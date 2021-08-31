package com.palmdev.learn_english.Levels_Start;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.palmdev.learn_english.Arrays.Array_lvl_17;
import com.palmdev.learn_english.Levels.Level17;
import com.palmdev.learn_english.LocaleHelper;
import com.palmdev.learn_english.R;
import com.palmdev.learn_english.SelectDayActivity;

import java.util.Locale;

public class Level17_Start extends AppCompatActivity {

    Array_lvl_17 array = new Array_lvl_17();
    final int[] words = {
            R.id.word_1,R.id.word_2,R.id.word_3,R.id.word_4,R.id.word_5,R.id.word_6,R.id.word_7,
            R.id.word_8,R.id.word_9,R.id.word_10,R.id.word_11,R.id.word_12,R.id.word_13,R.id.word_14
            ,R.id.word_15,R.id.word_16,R.id.word_17,R.id.word_18,R.id.word_19,R.id.word_20,
    };
    final int[] wordsTranslate = {
            R.id.word_translate_1,R.id.word_translate_2,R.id.word_translate_3,R.id.word_translate_4,
            R.id.word_translate_5,R.id.word_translate_6,R.id.word_translate_7,R.id.word_translate_8,
            R.id.word_translate_9,R.id.word_translate_10,R.id.word_translate_11,
            R.id.word_translate_12,R.id.word_translate_13,R.id.word_translate_14,
            R.id.word_translate_15,R.id.word_translate_16,R.id.word_translate_17,
            R.id.word_translate_18,R.id.word_translate_19,R.id.word_translate_20,
    };

    @Override
    protected void attachBaseContext(Context base) {
        String locale = Locale.getDefault().getLanguage();
        super.attachBaseContext(LocaleHelper.onAttach(base, locale));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_level);

        for (int i = 0; i<20; i++){
            TextView tv = findViewById(words[i]);
            tv.setText(array.word_title[i]);
        }
        for (int i = 0; i<20; i++){
            TextView tv = findViewById(wordsTranslate[i]);
            tv.setText(array.word_translate[i]);
        }
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.top_to_bottom);
        LinearLayout allWords = findViewById(R.id.all_words);
        allWords.startAnimation(anim);
    }

    public void onClickBtnStart(View view) {
        try {
            Intent intent = new Intent(Level17_Start.this, Level17.class);
            startActivity(intent);
            finish();
        }catch (Exception e){
            //
        }
    }

    public void onClickBtnBack(View view) {
        try {
            Intent intent = new Intent(Level17_Start.this, SelectDayActivity.class);
            startActivity(intent);
            finish();
        }catch (Exception e){
            //
        }
    }

    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(Level17_Start.this,SelectDayActivity.class);
            startActivity(intent);
            finish();
        }catch (Exception e){
            //
        }
    }
}
