package com.gamadevlopment.ucimoslovenski.Quiz_activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gamadevlopment.ucimoslovenski.R;
import com.gamadevlopment.ucimoslovenski.RecyclerViewAdapters.excersizePick_RecyclerAdapter;
import com.gamadevlopment.ucimoslovenski.Word;
import com.gamadevlopment.ucimoslovenski.pick_excersize;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class listening_Quiz_activity extends AppCompatActivity {

    private TextView mForeignWordTextView;
    private Button mOptionButton1, mOptionButton2, mOptionButton3, mButton_backHome;

    private ArrayList<Word> mDataList = new ArrayList<>();
    private HashSet<Integer> mCorrectlyAnsweredIndices = new HashSet<Integer>();
    private Random mRandom = new Random();
    private int mCurrentIndex;

    private int mNumCorrectAnswers = 0;
    private int mNumIncorrectAnswers = 0;

    String csvFileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening_quiz);
        getSupportActionBar().hide();
        //Destroy old activity

        // Get the current window of the activity
        Window window = getWindow();

        // Hide the navigation bar and enable immersive mode
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        mForeignWordTextView = findViewById(R.id.listening_quiz_textView_learning_word);
        mOptionButton1 = findViewById(R.id.listening_quiz_Button_1);
        mOptionButton2 = findViewById(R.id.listening_quiz_Button_2);
        mOptionButton3 = findViewById(R.id.listening_quiz_Button_3);
        mButton_backHome = findViewById(R.id.listening_quiz_Home_Button);

        csvFileName = getIntent().getExtras().getString(excersizePick_RecyclerAdapter.PICKED_EXERCISE_INTENT_KEY);


        //when clicked user is shown dialog with how many answers he correctly and wrong answered and button to go home
        mButton_backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create the custom view layout
                View customView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.quiz_results_alert_dialog_layout, null, false);

                TextView numRightTextView = customView.findViewById(R.id.quiz_alertDialog_textView_corectAnswers);
                TextView numWrongTextView = customView.findViewById(R.id.quiz_alertDialog_textView_wrongAnswers);
                Button backOnHomeActivityButton = customView.findViewById(R.id.button_quizResults_dialog);
                numRightTextView.setText(String.valueOf(mNumCorrectAnswers));
                numWrongTextView.setText(String.valueOf(mNumIncorrectAnswers));

                backOnHomeActivityButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), pick_excersize.class);
                        startActivity(intent);
                        finish();
                    }
                });


                BottomSheetDialog dialog = new BottomSheetDialog(listening_Quiz_activity.this);
                dialog.setContentView(customView);
                dialog.show();

            }
        });

        //get Data from file
        readFromCsv();
    }

    //read words from cvs file with given file name
    private void readFromCsv() {

        int resId = getResources().getIdentifier(csvFileName, "raw", getPackageName());
        InputStream inputStream = getResources().openRawResource(resId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(";"); // replace ',' with your CSV separator

                if (tokens.length >= 2) {
                    // assuming the first column contains the foreign word and the second column contains the translated word
                    String foreignWord = tokens[1];
                    String translatedWord = tokens[0];

                    Word word = new Word(foreignWord, translatedWord, "NN");
                    mDataList.add(word);
                }
            }
        } catch (IOException e) {
            Log.e("TEST", "Error reading from CSV file", e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                Log.e("TEST", "Error closing input stream", e);
            }
        }

        startQuiz();
    }


    private void startQuiz() {

        mOptionButton1.setBackgroundResource(R.drawable.button_bc_with_white_stroke);
        mOptionButton2.setBackgroundResource(R.drawable.button_bc_with_white_stroke);
        mOptionButton3.setBackgroundResource(R.drawable.button_bc_with_white_stroke);

        // Pick a random foreign word from the list
        mCurrentIndex = mRandom.nextInt(mDataList.size());
        String foreignWord = mDataList.get(mCurrentIndex).getForgein_word();

        // Set the foreign word text view
        mForeignWordTextView.setText(foreignWord);

        // Pick a random button to be the correct answer
        int correctButton = mRandom.nextInt(3);

        // Keep track of the incorrect translations that have already been selected
        HashSet<String> incorrectTranslations = new HashSet<String>();


        // Set the text for each button
        if (correctButton == 0) {
            mOptionButton1.setText(mDataList.get(mCurrentIndex).getTranslated_word());
            incorrectTranslations.add(mOptionButton1.getText().toString());
            mOptionButton2.setText(getRandomIncorrectTranslation(incorrectTranslations));
            incorrectTranslations.add(mOptionButton2.getText().toString());
            mOptionButton3.setText(getRandomIncorrectTranslation(incorrectTranslations));
        } else if (correctButton == 1) {
            mOptionButton1.setText(getRandomIncorrectTranslation(incorrectTranslations));
            incorrectTranslations.add(mOptionButton1.getText().toString());
            mOptionButton2.setText(mDataList.get(mCurrentIndex).getTranslated_word());
            incorrectTranslations.add(mOptionButton2.getText().toString());
            mOptionButton3.setText(getRandomIncorrectTranslation(incorrectTranslations));
        } else {
            mOptionButton1.setText(getRandomIncorrectTranslation(incorrectTranslations));
            incorrectTranslations.add(mOptionButton1.getText().toString());
            mOptionButton2.setText(getRandomIncorrectTranslation(incorrectTranslations));
            incorrectTranslations.add(mOptionButton2.getText().toString());
            mOptionButton3.setText(mDataList.get(mCurrentIndex).getTranslated_word());
        }
        // Set click listeners for each button
        mOptionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(0);
            }
        });

        mOptionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(1);
            }
        });

        mOptionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(2);
            }
        });
    }

    private String getRandomIncorrectTranslation(HashSet<String> incorrectTranslations) {
        // Create a copy of the list of words and remove the correct translation and the already selected incorrect translations
        ArrayList<Word> availableWords = new ArrayList<>(mDataList);
        availableWords.remove(mCurrentIndex);
        for (String translation : incorrectTranslations) {
            availableWords.removeIf(word -> word.getTranslated_word().equals(translation));
        }

        // Select a random index from the new list and return the corresponding translated word
        int randomIndex = mRandom.nextInt(availableWords.size());
        return availableWords.get(randomIndex).getTranslated_word();
    }


    private void checkAnswer(int buttonNumber) {
        String selectedTranslation = "";

        // Determine which button was clicked
        switch (buttonNumber) {
            case 0:
                selectedTranslation = mOptionButton1.getText().toString();
                break;
            case 1:
                selectedTranslation = mOptionButton2.getText().toString();
                break;
            case 2:
                selectedTranslation = mOptionButton3.getText().toString();
                break;
        }

        // Check if the selected translation matches the correct translation
        if (selectedTranslation.equals(mDataList.get(mCurrentIndex).getTranslated_word())) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();

            //add word to hash list so word wont show again
            mCorrectlyAnsweredIndices.add(mCurrentIndex);
            switch (buttonNumber) {
                case 0:
                    mOptionButton1.setBackgroundResource(R.drawable.button_bc_correct_new);
                    playSound_Foregein_word(this, R.raw.right_answer);
                    break;
                case 1:
                    mOptionButton2.setBackgroundResource(R.drawable.button_bc_correct_new);
                    playSound_Foregein_word(this, R.raw.right_answer);
                    break;
                case 2:
                    mOptionButton3.setBackgroundResource(R.drawable.button_bc_correct_new);
                    playSound_Foregein_word(this, R.raw.right_answer);
                    break;

            }
            mNumCorrectAnswers++; // increment the number of correct answers
        } else {
            Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
            switch (buttonNumber) {
                case 0:
                    mOptionButton1.setBackgroundResource(R.drawable.button_bc_wrong_new);
                    playSound_Foregein_word(this, R.raw.wrong_answer);
                    break;
                case 1:
                    mOptionButton2.setBackgroundResource(R.drawable.button_bc_wrong_new);
                    playSound_Foregein_word(this, R.raw.wrong_answer);
                    break;
                case 2:
                    mOptionButton3.setBackgroundResource(R.drawable.button_bc_wrong_new);
                    playSound_Foregein_word(this, R.raw.wrong_answer);
                    break;

            }

            // Show the correct answer by changing its background color
            if (mOptionButton1.getText().toString().equals(mDataList.get(mCurrentIndex).getTranslated_word())) {
                mOptionButton1.setBackgroundResource(R.drawable.button_bc_correct_new);
            } else if (mOptionButton2.getText().toString().equals(mDataList.get(mCurrentIndex).getTranslated_word())) {
                mOptionButton2.setBackgroundResource(R.drawable.button_bc_correct_new);
            } else if (mOptionButton3.getText().toString().equals(mDataList.get(mCurrentIndex).getTranslated_word())) {
                mOptionButton3.setBackgroundResource(R.drawable.button_bc_correct_new);
            }

            mNumIncorrectAnswers++; // increment the number of incorrect answers
        }

        // Restart the quiz with a new question
        // Disable all buttons temporarily to prevent multiple clicks
        mOptionButton1.setEnabled(false);
        mOptionButton2.setEnabled(false);
        mOptionButton3.setEnabled(false);

        // Delay for 1 second before starting the next quiz question
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Re-enable all buttons
                mOptionButton1.setEnabled(true);
                mOptionButton2.setEnabled(true);
                mOptionButton3.setEnabled(true);

                // Start the next quiz question
                startQuiz();
            }
        }, 1500); // Delay for 1 second (1000 milliseconds)


    }

    public void playSound_Foregein_word(Context context, int soundPath) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, soundPath);
        mediaPlayer.start();
    }

}