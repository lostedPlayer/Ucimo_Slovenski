package com.gamadevlopment.ucimoslovenski.RecyclerViewAdapters;

import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gamadevlopment.ucimoslovenski.R;
import com.gamadevlopment.ucimoslovenski.Word;
import com.google.android.material.bottomsheet.BottomSheetDialog;


import java.util.ArrayList;


public class SwipeCardRecyclerViewAdapter extends RecyclerView.Adapter<SwipeCardRecyclerViewAdapter.myViewHolder> {


    Context context;
    private ArrayList<Word> mDataList;

    public SwipeCardRecyclerViewAdapter(Application context, ArrayList<Word> mDataList) {
        this.context = context.getApplicationContext();
        this.mDataList = mDataList;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context).inflate(R.layout.swipe_card_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        Word curentWord = mDataList.get(position);
        String foregin_word = curentWord.getForgein_word();
        String translated_word = curentWord.getTranslated_word();
        String audio_path = curentWord.getAudio_path();

        holder.swipe_card_foregin_word.setText(foregin_word);
        holder.swipe_card_translated_word.setText(translated_word);
        //play sound when card clicked
        holder.swipe_card_main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Jos uvijek radimo na ovoj opciji :)", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    public class myViewHolder extends RecyclerView.ViewHolder {

        private TextView swipe_card_foregin_word, swipe_card_translated_word;
        private CardView swipe_card_main_layout;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            swipe_card_foregin_word = itemView.findViewById(R.id.swipe_card_textView_foregin_Word);
            swipe_card_translated_word = itemView.findViewById(R.id.swipe_card_textView_translated_Word);
            swipe_card_main_layout = itemView.findViewById(R.id.swipe_card_layout);
        }

    }


    //play word audio
    public void playSound_Foregein_word(String soundPath) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(soundPath);
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
            mediaPlayer.start();
        } catch (Exception e) {

            Log.d("TEST", "Playing audio from Firebase server error :  " + e.getMessage());
        }
    }

}