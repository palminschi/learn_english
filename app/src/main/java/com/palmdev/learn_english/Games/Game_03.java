package com.palmdev.learn_english.Games;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.palmdev.learn_english.LocaleHelper;
import com.palmdev.learn_english.R;
import com.palmdev.learn_english.SelectGame;

import java.util.Locale;


public class Game_03 extends AppCompatActivity {

    Button btnNext, btnSound;
    TextView textWin, textLose, tv_lastWord;
    TextView bestScore, wordTranslate, phrase, phraseTranslate;
    private String wordMain;
    private int actualWord, bestScore_pref, counterFullWord, counterErrors;
    private final int[] images = {
            R.id.stage_1,
            R.id.stage_2,
            R.id.stage_3,
            R.id.stage_4,
            R.id.stage_5,
            R.id.stage_6,
    };
    private final int[] containers = {
            R.id.con_let1,R.id.con_let2,R.id.con_let3,R.id.con_let4,R.id.con_let5,R.id.con_let6,
            R.id.con_let7,R.id.con_let8,R.id.con_let9,R.id.con_let10,R.id.con_let11,R.id.con_let12,
            R.id.con_let13,R.id.con_let14,R.id.con_let15,R.id.con_let16,R.id.con_let17,R.id.con_let18,
            R.id.con_let19,R.id.con_let20,R.id.con_let21,R.id.con_let22,R.id.con_let23,
    };
    private final int[] letters = {
            R.id.let1,R.id.let2,R.id.let3,R.id.let4,R.id.let5,R.id.let6,R.id.let7,R.id.let8,R.id.let9,
            R.id.let10,R.id.let11,R.id.let12,R.id.let13,R.id.let14,R.id.let15,R.id.let16,R.id.let17,
            R.id.let18,R.id.let19,R.id.let20,R.id.let21,R.id.let22,R.id.let23,
    };
    private final int[] alphabet = {
            R.id.let_A,R.id.let_AE,R.id.let_B,R.id.let_SS,R.id.let_C,R.id.let_D,R.id.let_E,R.id.let_F,
            R.id.let_G,R.id.let_H,R.id.let_I,R.id.let_J,R.id.let_K,R.id.let_L,R.id.let_M,R.id.let_N,
            R.id.let_O,R.id.let_NN,R.id.let_P,R.id.let_Q,R.id.let_R,R.id.let_S,R.id.let_T,R.id.let_U,
            R.id.let_UE,R.id.let_V,R.id.let_W,R.id.let_X,R.id.let_Y,R.id.let_Z,
    };

    SharedPreferences save;
    SharedPreferences.Editor editor;
    Games_Arrays arrays = new Games_Arrays();
    Animation animDisappear, animClick, animAppear;
    char[] charArray;
    MediaPlayer mPlayer;
    //Ads
    private String AD_UNIT_INTERSTITIAL_ID;
    public InterstitialAd interstitialAd;
    boolean showAds;
    private int ads_game_counter;


    @Override
    protected void attachBaseContext(Context base) {
        String locale = Locale.getDefault().getLanguage();
        super.attachBaseContext(LocaleHelper.onAttach(base, locale));
    }

    @SuppressLint({"CommitPrefEdits", "SetTextI18n"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_03);

        //Shared Pref
        save = getSharedPreferences("Save", MODE_PRIVATE);
        editor = save.edit();
        actualWord = save.getInt(getString(R.string.game_03_actualWord),0);
        if (actualWord == 600) {
            actualWord = 0;
        }
        bestScore_pref = save.getInt(getString(R.string.game_03_bestScore),0);
        ads_game_counter = save.getInt(getString(R.string.ads_game_counter), 0);
        showAds = save.getBoolean(getString(R.string.show_ads),true);

        // Find Views
        tv_lastWord = findViewById(R.id.tv_lastWord);
        btnSound = findViewById(R.id.btn_sound);
        textLose = findViewById(R.id.text_lose);
        textWin = findViewById(R.id.text_win);
        btnNext = findViewById(R.id.btn_next);
        btnNext.setClickable(false);
        bestScore = findViewById(R.id.best_score);
        wordTranslate = findViewById(R.id.word_translate);
        phrase = findViewById(R.id.phrase);
        phraseTranslate = findViewById(R.id.phrase_translate);
        for (int i = 0; i < 23; i++){
            FrameLayout fl = findViewById(containers[i]);
            fl.setVisibility(View.GONE);
            TextView tv = findViewById(letters[i]);
            tv.setText("");
        }

        // Animation
        animDisappear = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.disappear);
        animClick = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.click);
        animAppear = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.appear);

        // Set Content
        tv_lastWord.setText(getString(R.string.games_wordNum)+ (actualWord + 1));
        mPlayer = MediaPlayer.create(this,arrays.sound[actualWord]);
        bestScore.setText(String.valueOf(bestScore_pref));
        wordMain = getString(arrays.word_main[actualWord]);
        wordTranslate.setText(getString(arrays.word_translate[actualWord]));
        phrase.setText(getString(arrays.phrase[actualWord]));
        phraseTranslate.setText(getString(arrays.phrase_translate[actualWord]));

        charArray = wordMain.toCharArray();
        for (int i = 0; i < wordMain.length(); i++) {
            TextView tv = findViewById(letters[i]);
            tv.setText(String.valueOf(charArray[i]));
            tv.setVisibility(View.INVISIBLE);
            tv.setAllCaps(true);
            FrameLayout fl = findViewById(containers[i]);
            fl.setVisibility(View.VISIBLE);
            if (charArray[i] == ' '){
                fl.setVisibility(View.INVISIBLE);
            }
            if (charArray[i] == '?'){
                fl.setVisibility(View.GONE);
            }
            if (charArray[i] == '¿'){
                fl.setVisibility(View.GONE);
            }

        }

        counterErrors = 0;
        counterFullWord = charArray.length;
        for (int i = 0; i < charArray.length; i++){
            if (charArray[i] == ' '){
                counterFullWord--;
            }
            if (charArray[i] == '?'){
                counterFullWord--;
            }
            if (charArray[i] == '¿'){
                counterFullWord--;
            }
        }

        // ADS
        if (showAds){
            if (ads_game_counter >= 3){
                AD_UNIT_INTERSTITIAL_ID = getString(R.string.AD_UNIT_INTERSTITIAL_ID);
                loadInterstitialAd();
            }
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
                        Game_03.this.interstitialAd = interstitialAd;
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        Game_03.this.interstitialAd = null;

                                        ads_game_counter = 0;
                                        editor.putInt(getString(R.string.ads_game_counter),0);
                                        editor.apply();

                                        Intent intent = new Intent(Game_03.this, Game_03.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        Game_03.this.interstitialAd = null;
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        interstitialAd = null;
                    }
                });
    }

    public void getSound(View view){
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);
        mPlayer.start();
    }

    public void getLikeApp(){
        ReviewManager manager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();
                Task<Void> flow = manager.launchReviewFlow(Game_03.this, reviewInfo);
                flow.addOnCompleteListener(task1 -> {

                });
            }
        });
    }

    public void win(){
        for (int j : alphabet) {
            TextView tv = findViewById(j);
            tv.setClickable(false);
        }
        bestScore_pref = bestScore_pref + 1;
        editor.putInt(getString(R.string.game_03_bestScore),bestScore_pref);
        textWin.startAnimation(animAppear);
        textWin.setVisibility(View.VISIBLE);

        actualWord++;
        editor.putInt(getString(R.string.game_03_actualWord),actualWord);
        btnNext.setClickable(true);
        btnNext.setBackgroundResource(R.drawable.style_btn_true);
        phrase.startAnimation(animAppear);
        phrase.setVisibility(View.VISIBLE);
        phraseTranslate.startAnimation(animAppear);
        phraseTranslate.setVisibility(View.VISIBLE);
        btnSound.setVisibility(View.VISIBLE);
        btnSound.setScaleX(0.0f);
        btnSound.animate().scaleX(1.0f).setDuration(500);

        if (actualWord == 2){
            getLikeApp();
            ads_game_counter = 0;
            editor.putInt(getString(R.string.ads_game_counter),0);
        }
        editor.apply();
    }
    public void lose(){
        for (int j : alphabet) {
            TextView tv = findViewById(j);
            tv.setClickable(false);
        }
        editor.putInt(getString(R.string.game_03_bestScore),0);
        textLose.startAnimation(animAppear);
        textLose.setVisibility(View.VISIBLE);

        editor.putInt(getString(R.string.game_03_actualWord),actualWord++);
        btnNext.setClickable(true);
        btnNext.setBackgroundResource(R.drawable.style_btn_true);
        phrase.startAnimation(animAppear);
        phrase.setVisibility(View.VISIBLE);
        phraseTranslate.startAnimation(animAppear);
        phraseTranslate.setVisibility(View.VISIBLE);
        btnSound.setVisibility(View.VISIBLE);
        btnSound.setScaleX(0.0f);
        btnSound.animate().scaleX(1.0f).setDuration(500);

        if (actualWord == 2){
            getLikeApp();
            ads_game_counter = 0;
            editor.putInt(getString(R.string.ads_game_counter),0);
        }

        editor.apply();
    }

    public void selectedFalse(TextView textView){
        boolean incorrect = true;
        for (int i = 0; i < wordMain.length(); i++) {
            TextView tv = findViewById(letters[i]);
            String text1 = textView.getText().toString();
            text1 = text1.toLowerCase();
            String text2 = tv.getText().toString();
            text2 = text2.toLowerCase();

            if (text2.equals("ó")){
                text2 = "o";
            }
            if (text2.equals("ú")){
                text2 = "u";
            }
            if (text2.equals("í")){
                text2 = "i";
            }
            if (text2.equals("á")){
                text2 = "a";
            }
            if (text2.equals("é")){
                text2 = "e";
            }
            if (text1.equals(text2)){
                incorrect = false;
            }

        }
        if (incorrect) {
            ImageView iv = findViewById(images[counterErrors]);
            iv.setVisibility(View.VISIBLE);
            counterErrors++;
        }
        if (counterErrors >= 6) {
            lose();
        }
    }
    public void selectedTrue(TextView textView){
        textView.startAnimation(animClick);
        textView.setClickable(false);
        textView.postDelayed(() -> {
            textView.startAnimation(animDisappear);
            textView.setVisibility(View.INVISIBLE);
        },300);

        for (int i = 0; i < wordMain.length(); i++) {
            TextView tv = findViewById(letters[i]);
            String text1 = textView.getText().toString();
            String text2 = tv.getText().toString();
            text1 = text1.toLowerCase();
            text2 = text2.toLowerCase();
            if (text2.equals("ó")){
                text2 = "o";
            }
            if (text2.equals("ú")){
                text2 = "u";
            }
            if (text2.equals("í")){
                text2 = "i";
            }
            if (text2.equals("á")){
                text2 = "a";
            }
            if (text2.equals("é")){
                text2 = "e";
            }
            if (text1.equals(text2)){
                tv.startAnimation(animAppear);
                tv.setVisibility(View.VISIBLE);
                counterFullWord--;
            }
        }
        if (counterFullWord == 0){
            win();
        }

    }

    //
    public void onClickBtnNext(View view) {
        if (showAds && interstitialAd != null && ads_game_counter >= 3){
            interstitialAd.show(Game_03.this);
        } else {
            this.recreate();
        }

        ads_game_counter++;
        editor.putInt(getString(R.string.ads_game_counter),ads_game_counter);
        editor.apply();
    }

    public void onClickBtnBack(View view) {
        try {
            Intent intent = new Intent(Game_03.this, SelectGame.class);
            startActivity(intent);
            finish();
        }catch (Exception e){
            // -
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            Intent intent = new Intent(Game_03.this,SelectGame.class);
            startActivity(intent);
            finish();
        }catch (Exception e){
            // -
        }
    }


    // Alphabet onClicks
    public void onClick_A(View view){
        TextView let_A = findViewById(R.id.let_A);
        selectedTrue(let_A);
        selectedFalse(let_A);
    }
    public void onClick_AE(View view){
        TextView let_AE = findViewById(R.id.let_AE);
        selectedTrue(let_AE);
        selectedFalse(let_AE);
    }
    public void onClick_B(View view){
        TextView let_B = findViewById(R.id.let_B);
        selectedTrue(let_B);
        selectedFalse(let_B);
    }
    public void onClick_SS(View view){
        TextView let_SS = findViewById(R.id.let_SS);
        selectedTrue(let_SS);
        selectedFalse(let_SS);
    }
    public void onClick_C(View view){
        TextView let_C = findViewById(R.id.let_C);
        selectedTrue(let_C);
        selectedFalse(let_C);
    }
    public void onClick_D(View view){
        TextView let_D = findViewById(R.id.let_D);
        selectedTrue(let_D);
        selectedFalse(let_D);
    }
    public void onClick_E(View view){
        TextView let_E = findViewById(R.id.let_E);
        selectedTrue(let_E);
        selectedFalse(let_E);
    }
    public void onClick_F(View view){
        TextView let_F = findViewById(R.id.let_F);
        selectedTrue(let_F);
        selectedFalse(let_F);
    }
    public void onClick_G(View view){
        TextView let_G = findViewById(R.id.let_G);
        selectedTrue(let_G);
        selectedFalse(let_G);
    }
    public void onClick_H(View view){
        TextView let_H = findViewById(R.id.let_H);
        selectedTrue(let_H);
        selectedFalse(let_H);
    }
    public void onClick_I(View view){
        TextView let_I = findViewById(R.id.let_I);
        selectedTrue(let_I);
        selectedFalse(let_I);
    }
    public void onClick_J(View view){
        TextView let_J = findViewById(R.id.let_J);
        selectedTrue(let_J);
        selectedFalse(let_J);
    }
    public void onClick_K(View view){
        TextView let_K = findViewById(R.id.let_K);
        selectedTrue(let_K);
        selectedFalse(let_K);
    }
    public void onClick_L(View view){
        TextView let_L = findViewById(R.id.let_L);
        selectedTrue(let_L);
        selectedFalse(let_L);
    }
    public void onClick_M(View view){
        TextView let_M = findViewById(R.id.let_M);
        selectedTrue(let_M);
        selectedFalse(let_M);
    }
    public void onClick_N(View view){
        TextView let_N = findViewById(R.id.let_N);
        selectedTrue(let_N);
        selectedFalse(let_N);
    }
    public void onClick_O(View view){
        TextView let_O = findViewById(R.id.let_O);
        selectedTrue(let_O);
        selectedFalse(let_O);
    }
    public void onClick_NN(View view){
        TextView let_NN = findViewById(R.id.let_NN);
        selectedTrue(let_NN);
        selectedFalse(let_NN);
    }
    public void onClick_P(View view){
        TextView let_P = findViewById(R.id.let_P);
        selectedTrue(let_P);
        selectedFalse(let_P);
    }
    public void onClick_Q(View view){
        TextView let_Q = findViewById(R.id.let_Q);
        selectedTrue(let_Q);
        selectedFalse(let_Q);
    }
    public void onClick_R(View view){
        TextView let_R = findViewById(R.id.let_R);
        selectedTrue(let_R);
        selectedFalse(let_R);
    }
    public void onClick_S(View view){
        TextView let_S = findViewById(R.id.let_S);
        selectedTrue(let_S);
        selectedFalse(let_S);
    }
    public void onClick_T(View view){
        TextView let_T = findViewById(R.id.let_T);
        selectedTrue(let_T);
        selectedFalse(let_T);
    }
    public void onClick_U(View view){
        TextView let_U = findViewById(R.id.let_U);
        selectedTrue(let_U);
        selectedFalse(let_U);
    }
    public void onClick_UE(View view){
        TextView let_UE = findViewById(R.id.let_UE);
        selectedTrue(let_UE);
        selectedFalse(let_UE);
    }
    public void onClick_V(View view){
        TextView let_V = findViewById(R.id.let_V);
        selectedTrue(let_V);
        selectedFalse(let_V);
    }
    public void onClick_W(View view){
        TextView let_W = findViewById(R.id.let_W);
        selectedTrue(let_W);
        selectedFalse(let_W);
    }
    public void onClick_X(View view){
        TextView let_X = findViewById(R.id.let_X);
        selectedTrue(let_X);
        selectedFalse(let_X);
    }
    public void onClick_Y(View view){
        TextView let_Y = findViewById(R.id.let_Y);
        selectedTrue(let_Y);
        selectedFalse(let_Y);
    }
    public void onClick_Z(View view){
        TextView let_Z = findViewById(R.id.let_Z);
        selectedTrue(let_Z);
        selectedFalse(let_Z);
    }
}
