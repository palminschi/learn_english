package com.palmdev.learn_english.Levels_Start;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.palmdev.learn_english.Arrays.Array_lvl_9;
import com.palmdev.learn_english.Levels.Level8;
import com.palmdev.learn_english.Levels.Level9;
import com.palmdev.learn_english.LocaleHelper;
import com.palmdev.learn_english.R;
import com.palmdev.learn_english.SelectDayActivity;

import java.util.Locale;

public class Level9_Start extends AppCompatActivity {

    Array_lvl_9 array = new Array_lvl_9();
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

    //Ads
    private String AD_UNIT_INTERSTITIAL_ID;
    public InterstitialAd interstitialAd;
    SharedPreferences save;
    SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
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
        //ADS
        // SharedPreferences
        save = getSharedPreferences("Save", MODE_PRIVATE);
        editor = save.edit();
        boolean showAds = save.getBoolean(getString(R.string.show_ads),true);
        if (showAds) {
            AD_UNIT_INTERSTITIAL_ID = getString(R.string.AD_UNIT_INTERSTITIAL_ID);
            loadInterstitialAd();
        }
    }

    public void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                this,
                AD_UNIT_INTERSTITIAL_ID,
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        Level9_Start.this.interstitialAd = interstitialAd;
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        Level9_Start.this.interstitialAd = null;
                                        Intent intent = new Intent(Level9_Start.this, Level9.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        Level9_Start.this.interstitialAd = null;
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        interstitialAd = null;
                    }
                });
    }

    public void onClickBtnStart(View view) {
        try {
            if (interstitialAd != null) {
                interstitialAd.show(Level9_Start.this);
            } else {
                Intent intent = new Intent(Level9_Start.this, Level9.class);
                startActivity(intent);
                finish();
            }
        }catch (Exception e){
            //
        }
    }

    public void onClickBtnBack(View view) {
        try {
            Intent intent = new Intent(Level9_Start.this, SelectDayActivity.class);
            startActivity(intent);
            finish();
        }catch (Exception e){
            //
        }
    }

    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(Level9_Start.this,SelectDayActivity.class);
            startActivity(intent);
            finish();
        }catch (Exception e){
            //
        }
    }
}
