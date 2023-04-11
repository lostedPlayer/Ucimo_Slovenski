package com.gamadevlopment.ucimoslovenski;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gamadevlopment.ucimoslovenski.RecyclerViewAdapters.SwipeCardRecyclerViewAdapter;
import com.gamadevlopment.ucimoslovenski.Quiz_activities.listening_Quiz_activity;
import com.gamadevlopment.ucimoslovenski.RecyclerViewAdapters.excersizePick_RecyclerAdapter;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class cardSwipe_activity extends AppCompatActivity implements CardStackListener {

    private CardStackView cardStackView;
    private TextView mPickedWordCategory_tv;
    private SwipeCardRecyclerViewAdapter adapter;

    private ArrayList<Word> mWordList;

    String csvFileName;
    String exercise_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_swipe);
        getSupportActionBar().hide();

        mWordList = new ArrayList<>();
        csvFileName = getIntent().getExtras().getString(excersizePick_RecyclerAdapter.PICKED_EXERCISE_INTENT_KEY);
        exercise_title = getIntent().getExtras().getString(excersizePick_RecyclerAdapter.PICKED_EXERCISE_TITLE_INTENT_KEY);

        //set picked word category
        mPickedWordCategory_tv = findViewById(R.id.swipe_card_word_category_textView);
        mPickedWordCategory_tv.setText(exercise_title);

        cardStackView = findViewById(R.id.cardStackView);
        adapter = new SwipeCardRecyclerViewAdapter(this.getApplication(), mWordList);
        CardStackLayoutManager cardStackLayoutManager = new CardStackLayoutManager(this.getApplicationContext(), this);
        cardStackLayoutManager.setStackFrom(StackFrom.Top);
        cardStackLayoutManager.setVisibleCount(1);
        cardStackView.setLayoutManager(cardStackLayoutManager);
        cardStackLayoutManager.setCanScrollVertical(false);
        cardStackView.setAdapter(adapter);


        readFromCsv();

    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {

    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {

    }

    @Override
    public void onCardDisappeared(View view, int position) {
        if (position == mWordList.size() - 1) {
            Toast.makeText(this, "Nema vise Kartica ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, listening_Quiz_activity.class);
            intent.putExtra(excersizePick_RecyclerAdapter.PICKED_EXERCISE_INTENT_KEY, csvFileName);
            startActivity(intent);
            finish();
        }
    }

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
                    mWordList.add(word);
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

        adapter.notifyDataSetChanged();
    }

}
