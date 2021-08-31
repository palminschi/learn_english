package com.palmdev.learn_english.Games;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

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
import java.util.Random;

public class Game_01 extends AppCompatActivity {

    Button wordLeft, wordRight;
    TextView wordMain, tv_bestScore;
    LinearLayout lineWords;
    private float bottomOfScreen, topOfScreen;
    private int count, maxScore;
    private int count2, bestScore, life, prefBestScore;
    Games_Arrays arrays = new Games_Arrays();
    Random random = new Random();
    final Handler handler = new Handler();
    Runnable run;
    ImageView life01, life02, life03, fullScreen_false;
    final int[] colors = {
            R.drawable.style_game01_color01,
            R.drawable.style_game01_color02,
            R.drawable.style_game01_color03,
            R.drawable.style_game01_color04,
            R.drawable.style_game01_color05,
            R.drawable.style_game01_color06,
            R.drawable.style_game01_color07,
            R.drawable.style_game01_color08,
            R.drawable.style_game01_color09,
            R.drawable.style_game01_color10,
    };
    SharedPreferences save;
    SharedPreferences.Editor editor;
    MediaPlayer mPlayer;
    ToggleButton sound_toggle;

    @Override
    protected void attachBaseContext(Context base) {
        String locale = Locale.getDefault().getLanguage();
        super.attachBaseContext(LocaleHelper.onAttach(base, locale));
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_01);

        // SharedPref
        save = getSharedPreferences("Save", MODE_PRIVATE);
        editor = save.edit();
        boolean soundValue = save.getBoolean("soundValue",true);

        // Color Status and Nav Bar
        getWindow().setStatusBarColor(getColor(R.color.white_violet_02));
        getWindow().setNavigationBarColor(getColor(R.color.brown_01));

        // Find Views
        fullScreen_false = findViewById(R.id.full_screen_false);
        sound_toggle = findViewById(R.id.sound_toggle);
        wordLeft = findViewById(R.id.word_left);
        wordRight = findViewById(R.id.word_right);
        wordMain = findViewById(R.id.word_title);
        lineWords = findViewById(R.id.line_words);
        tv_bestScore = findViewById(R.id.best_score);
        life01 = findViewById(R.id.life_01);
        life02 = findViewById(R.id.life_02);
        life03 = findViewById(R.id.life_03);

        bottomOfScreen = getResources().getDisplayMetrics().heightPixels;
        topOfScreen = 0;

        //ADS
        boolean showAds = save.getBoolean(getString(R.string.show_ads),true);
        if (showAds) {
            LinearLayout container_ads = findViewById(R.id.container_ads);
            container_ads.setVisibility(View.VISIBLE);

            AdRequest adRequest = new AdRequest.Builder().build();
            AdView bannerAd = findViewById(R.id.bannerAd);
            bannerAd.loadAd(adRequest);
        }

        // Set score and counter
        prefBestScore = save.getInt(getString(R.string.game_01_bestScore), 0);
        count = save.getInt(getString(R.string.game_01_lastWord), 0);
        if (count >= 579) {
            count = 0;
        }
        maxScore = count + 19;
        bestScore = 0;
        life = 3;

        // If the user has not clicked
        run = () -> {
            if (count2 == count) {
                pressedFalse();

                fall();
                count++;
                handler.removeCallbacks(run);
                if (life == 0 | count >= maxScore) {
                    gameOver();
                    return;
                }
                getContent();
                if (count < maxScore - 1) {
                    fallHandler();
                }

            }
        };
        soundToggle();

        // Start Game
        getContent();
        sound_toggle.setChecked(soundValue);
        fall();
        if (count < maxScore) {
            fallHandler();
        }

        // Sound
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (soundValue){
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
        }else {
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);
        }
    }



    public void getSound(){
        mPlayer = MediaPlayer.create(this,arrays.sound[count]);
        mPlayer.start();
    }
    public void soundToggle(){
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        sound_toggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
                editor.putBoolean("soundValue",true);
            }else {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);
                editor.putBoolean("soundValue",false);
            }
            editor.apply();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(run);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(run);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(run);
    }

    public void gameOver() {

        editor.putBoolean(getString(R.string.game_01_winOrLose), count == maxScore);

        editor.putInt(getString(R.string.game_01_livesLeft), life);
        if (bestScore > prefBestScore) {
            editor.putInt(getString(R.string.game_01_bestScore), bestScore);
        }
        editor.putInt(getString(R.string.game_01_lastWord), count);
        editor.apply();
        handler.removeCallbacks(run);
        Intent intent = new Intent(Game_01.this, Game_01_End.class);
        startActivity(intent);
        finish();
        handler.removeCallbacks(run);
    }

    public void fallHandler() {
        count2 = count;
        handler.postDelayed(run, 6000);
    }

    public void fall() {
        Runnable endAction = () -> lineWords.animate()
                .translationY(bottomOfScreen)
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(6000);
        lineWords.animate().translationY(topOfScreen).setDuration(0).withEndAction(endAction);
    }

    public void onPressedWordLeft(View view) {
        handler.removeCallbacks(run);
        String str = getResources().getString(arrays.word_translate[count]);
        if (wordLeft.getText().toString().equals(str)) {
            pressedTrue();
        } else {
            pressedFalse();
        }
        if (life == 0 | count >= maxScore) {
            gameOver();
            return;
        }
        count++;
        getContent();
        fall();
        if (count < maxScore - 1) {
            fallHandler();
        }
    }

    public void onPressedWordRight(View view) {
        handler.removeCallbacks(run);
        String str = getResources().getString(arrays.word_translate[count]);
        if (wordRight.getText().toString().equals(str)) {
            pressedTrue();
        } else {
            pressedFalse();
        }
        if (life == 0 | count >= maxScore) {
            gameOver();
            return;
        }
        count++;
        getContent();
        fall();
        if (count < maxScore - 1) {
            fallHandler();
        }
    }

    public void pressedTrue() {
        bestScore++;
        String str = String.valueOf(bestScore);
        tv_bestScore.setText(str);
    }

    public void pressedFalse() {
        fullScreen_false.setAlpha(0.9f);
        fullScreen_false.setVisibility(View.VISIBLE);
        fullScreen_false.animate()
                .alpha(0.0f)
                .setDuration(500);

        if (bestScore > prefBestScore) {
            editor.putInt(getString(R.string.game_01_bestScore), bestScore);
            editor.apply();
        }
        bestScore = 0;
        String str = String.valueOf(bestScore);
        tv_bestScore.setText(str);
        life--;
        switch (life) {
            case 3:
                life01.setImageResource(R.drawable.game_heart);
                life02.setImageResource(R.drawable.game_heart);
                life03.setImageResource(R.drawable.game_heart);
                break;
            case 2:
                life01.setImageResource(R.drawable.game_heart_empty);
                life02.setImageResource(R.drawable.game_heart);
                life03.setImageResource(R.drawable.game_heart);
                break;
            case 1:
                life01.setImageResource(R.drawable.game_heart_empty);
                life02.setImageResource(R.drawable.game_heart_empty);
                life03.setImageResource(R.drawable.game_heart);
                break;
            case 0:
                life01.setImageResource(R.drawable.game_heart_empty);
                life02.setImageResource(R.drawable.game_heart_empty);
                life03.setImageResource(R.drawable.game_heart_empty);
                break;
        }
    }

    public void getContent() {
        getSound();
        int randomColor1 = random.nextInt(10);
        int randomColor2 = random.nextInt(10);
        while (randomColor1 == randomColor2) {
            randomColor2 = random.nextInt(10);
        }
        wordLeft.setBackgroundResource(colors[randomColor1]);
        wordRight.setBackgroundResource(colors[randomColor2]);

        wordMain.setText(arrays.word_main[count]);
        boolean leftOrRight = random.nextBoolean();
        if (leftOrRight) {
            wordRight.setText(arrays.word_translate[count]);
            wordLeft.setText(arrays.word_false_V1[count]);
            if (wordLeft.getText().toString().equals(wordRight.getText().toString())) {
                wordLeft.setText(arrays.word_false_V2[count]);
            }
        } else {
            wordLeft.setText(arrays.word_translate[count]);
            wordRight.setText(arrays.word_false_V1[count]);
            if (wordLeft.getText().toString().equals(wordRight.getText().toString())) {
                wordRight.setText(arrays.word_false_V2[count]);
            }
        }
    }


    public void onClickBtnBack(View view) {
        gameOver();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        gameOver();
    }
}
