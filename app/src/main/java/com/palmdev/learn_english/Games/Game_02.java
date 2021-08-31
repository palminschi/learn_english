package com.palmdev.learn_english.Games;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.palmdev.learn_english.LocaleHelper;
import com.palmdev.learn_english.R;
import com.palmdev.learn_english.SelectGame;

import java.util.Locale;

public class Game_02 extends AppCompatActivity {

    TextView wordMain, wordTranslate, phrase, phraseTranslate;
    TextView tv_bestScore, tv_actualWord, tv_winOrLose;
    Button btnNext, btnControl;
    private int bestScore, actualWord;
    LinearLayout texts,textHelp;
    EditText editText;
    SharedPreferences save;
    SharedPreferences.Editor editor;
    Games_Arrays arrays = new Games_Arrays();
    Animation animAppear;
    MediaPlayer mPlayer;
    //Ads
    private String AD_UNIT_INTERSTITIAL_ID;
    public InterstitialAd interstitialAd;
    private boolean showAds;
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
        setContentView(R.layout.game_02);

        //Find Views
        wordMain = findViewById(R.id.word_main);
        wordTranslate = findViewById(R.id.word_translate);
        phrase = findViewById(R.id.phrase);
        phraseTranslate = findViewById(R.id.phrase_translate);
        tv_bestScore = findViewById(R.id.best_score);
        tv_actualWord = findViewById(R.id.tv_lastWord);
        tv_winOrLose = findViewById(R.id.tv_winOrLose);
        editText = findViewById(R.id.edit_text);
        texts = findViewById(R.id.texts);
        textHelp = findViewById(R.id.text_help);
        btnNext = findViewById(R.id.btn_next);
        btnControl = findViewById(R.id.btn_control);

        // Shared Pref
        save = getSharedPreferences("Save",MODE_PRIVATE);
        editor = save.edit();

        // Set Value
        btnControl.setClickable(true);
        btnNext.setClickable(false);
        showAds = save.getBoolean(getString(R.string.show_ads),true);
        ads_game_counter = save.getInt(getString(R.string.ads_game_counter),0);
        AD_UNIT_INTERSTITIAL_ID = getString(R.string.AD_UNIT_INTERSTITIAL_ID);
        bestScore = save.getInt(getString(R.string.game_02_bestScore),0);
        actualWord = save.getInt(getString(R.string.game_02_actualWord),0);
        mPlayer = MediaPlayer.create(this,arrays.sound[actualWord]);

        // Animation
        animAppear = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.appear);

        // Set Content
        texts.setVisibility(View.GONE);
        textHelp.setVisibility(View.VISIBLE);
        tv_actualWord.setText(getString(R.string.games_wordNum)+ (actualWord + 1));
        tv_bestScore.setText(String.valueOf(bestScore));
        wordMain.setText(getString(arrays.word_main[actualWord]));
        wordTranslate.setText(getString(arrays.word_translate[actualWord]));
        phrase.setText(getString(arrays.phrase[actualWord]));
        phraseTranslate.setText(getString(arrays.phrase_translate[actualWord]));

        // ADS
        if (showAds){

            LinearLayout container_ads = findViewById(R.id.container_ads);
            container_ads.setVisibility(View.VISIBLE);

            AdRequest adRequest = new AdRequest.Builder().build();
            AdView bannerAd = findViewById(R.id.bannerAd);
            bannerAd.loadAd(adRequest);

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
                        Game_02.this.interstitialAd = interstitialAd;
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        Game_02.this.interstitialAd = null;

                                        ads_game_counter = 0;
                                        editor.putInt(getString(R.string.ads_game_counter),0);
                                        editor.apply();

                                        Intent intent = new Intent(Game_02.this, Game_02.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        Game_02.this.interstitialAd = null;
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

    public void onClickControl(View view){
        closeKeyBoard(view);
        String userAnswer = editText.getText().toString().toLowerCase();
        String rightAnswer = wordMain.getText().toString().toLowerCase();
        if (userAnswer.equals("")){
            Toast.makeText(this, getString(R.string.editText), Toast.LENGTH_SHORT).show();
            return;
        }
        if (userAnswer.equals(rightAnswer)){
            editText.setBackgroundResource(R.drawable.style_edittext_true);
            tv_winOrLose.setText(getString(R.string.right));
            tv_winOrLose.setTextColor(getColor(R.color.green_03));
            bestScore++;
        }else {
            editText.setBackgroundResource(R.drawable.style_edittext_false);
            tv_winOrLose.setText(getString(R.string.incorrect));
            tv_winOrLose.setTextColor(getColor(R.color.red_02));
            bestScore = 0;
        }
        actualWord++;
        editor.putInt(getString(R.string.game_02_actualWord),actualWord);
        editor.putInt(getString(R.string.game_02_bestScore),bestScore);
        editor.apply();
        textHelp.setVisibility(View.GONE);
        texts.setVisibility(View.VISIBLE);
        texts.startAnimation(animAppear);
        btnControl.setClickable(false);
        btnNext.setClickable(true);
        btnNext.setBackgroundResource(R.drawable.style_btn_true);

    }

    public void onClickBtnNext(View view){
        editText.setText("");
        if (showAds && interstitialAd != null && ads_game_counter >= 3){
            interstitialAd.show(Game_02.this);
        } else {
            this.recreate();
        }

        ads_game_counter++;
        editor.putInt(getString(R.string.ads_game_counter),ads_game_counter);
        editor.apply();
    }

    public void closeKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        getWindow().getDecorView().clearFocus();
    }

    public void onClickBtnBack(View view) {
        try {
            Intent intent = new Intent(Game_02.this, SelectGame.class);
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
            Intent intent = new Intent(Game_02.this,SelectGame.class);
            startActivity(intent);
            finish();
        }catch (Exception e){
            // -
        }
    }
}
