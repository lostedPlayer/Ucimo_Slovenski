package com.gamadevlopment.ucimoslovenski.RecyclerViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gamadevlopment.ucimoslovenski.Exercise;
import com.gamadevlopment.ucimoslovenski.Quiz_activities.listening_Quiz_activity;
import com.gamadevlopment.ucimoslovenski.R;
import com.gamadevlopment.ucimoslovenski.cardSwipe_activity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class excersizePick_RecyclerAdapter extends RecyclerView.Adapter<excersizePick_RecyclerAdapter.myViewHolder> {


    private Context context;
    private ArrayList<Exercise> mExersizes_List;


    public final static String PICKED_EXERCISE_INTENT_KEY = "EXERCISE_FILE_NAME";
    public final static String PICKED_EXERCISE_TITLE_INTENT_KEY = "EXERCISE_TITLE";


    public excersizePick_RecyclerAdapter(Context context, ArrayList<Exercise> mExersizes_List) {
        this.context = context;
        this.mExersizes_List = mExersizes_List;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context).inflate(R.layout.pick_excersize_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        Exercise exercize = mExersizes_List.get(position);
        String title = exercize.getTitle();
        String csvFileName = exercize.getCsvFileName();
        String wordsInExcercise = exercize.getNumberOfWordsInExcerCise();
        String image_fileName = exercize.getExcercise_image();

        int resId = context.getResources().getIdentifier(image_fileName, "drawable", context.getPackageName());

        if (resId != 0) {
            Glide.with(context).load(resId).into(holder.mExercise_image);
        }


        holder.mExersize_title.setText(title);
        holder.mWordsInExcercise.setText(wordsInExcercise + context.getString(R.string.words_in_excercise_text_holder) );


        holder.mExerciseCard_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View layoutView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_layout, null, false);
                CardView cardButton, gameButton;
                cardButton = layoutView.findViewById(R.id.bottomSheet_cardButton);
                gameButton = layoutView.findViewById(R.id.bottomSheet_gameButton);

                cardButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, cardSwipe_activity.class);
                        intent.putExtra(PICKED_EXERCISE_INTENT_KEY, csvFileName);
                        intent.putExtra(PICKED_EXERCISE_TITLE_INTENT_KEY, title);
                        context.startActivity(intent);
                    }
                });

                gameButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, listening_Quiz_activity.class);
                        intent.putExtra(PICKED_EXERCISE_INTENT_KEY, csvFileName);
                        context.startActivity(intent);
                    }
                });

                BottomSheetDialog dialog = new BottomSheetDialog(context);
                dialog.setContentView(layoutView);
                dialog.show();

            }
        });

    }


    @Override
    public int getItemCount() {
        return mExersizes_List.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        private TextView mExersize_title, mWordsInExcercise;
        private CardView mExerciseCard_layout;

        private ImageView mExercise_image;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            mExersize_title = itemView.findViewById(R.id.exersize_text_on_button);
            mExerciseCard_layout = itemView.findViewById(R.id.pick_excersize_card);
            mWordsInExcercise = itemView.findViewById(R.id.pick_excercise_card_wordsInExcercise);
            mExercise_image = itemView.findViewById(R.id.pick_excercise_card_image);
        }
    }

}
