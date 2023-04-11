package com.gamadevlopment.ucimoslovenski;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.gamadevlopment.ucimoslovenski.RecyclerViewAdapters.excersizePick_RecyclerAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

public class pick_excersize extends AppCompatActivity {

    private RecyclerView mExersizeRecyclerView;
    private ImageView mTopImageView_animation;
    private excersizePick_RecyclerAdapter mExersizePickAdapter;

    private ArrayList<Exercise> mExercises_list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_excersize);
        getSupportActionBar().hide();





        //  mTopImageView_animation = findViewById(R.id.pick_excersize_animation_image);
        mExersizeRecyclerView = findViewById(R.id.recyclerView_excersize_Pick);
        RelativeLayout parentLayout = findViewById(R.id.pick_excercise_parentLayout);


        // Glide.with(this).load(R.drawable.pick_exercise_ilustration).into(mTopImageView_animation);
        mExercises_list = new ArrayList<>();


        mExersizePickAdapter = new excersizePick_RecyclerAdapter(this, mExercises_list);
        mExersizeRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mExersizeRecyclerView.setAdapter(mExersizePickAdapter);

        getExercizeFromCSV();

    }

    private void getExercizeFromCSV() {
        mExercises_list.clear();
        InputStream inputStream = getResources().openRawResource(R.raw.vjezbe); // replace words with your CSV file name
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(";");// replace ',' with your CSV separator

                if (tokens.length >= 4) {
                    // assuming the first column contains the foreign word and the second column contains the translated word
                    String title = tokens[0];
                    String csvFileName = tokens[1];
                    String numberOfWordsInExcercize = tokens[2];
                    String image_fileName = tokens[3];

                    mExercises_list.add(new Exercise(title, csvFileName, numberOfWordsInExcercize, image_fileName));

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
        mExersizePickAdapter.notifyDataSetChanged();
    }


}