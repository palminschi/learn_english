package com.palmdev.learn_english.Games;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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
import com.google.android.play.core.review.model.ReviewErrorCode;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;
import com.palmdev.learn_english.LocaleHelper;
import com.palmdev.learn_english.R;
import com.palmdev.learn_english.SelectGame;

import java.util.Locale;

public class Game_01_End extends AppCompatActivity {

    TextView tv_bestScore, tv_winOrLose;
    ImageView iv_winOrLose, life01, life02, life03;
    SharedPreferences save;
    SharedPreferences.Editor editor;
    // ADS
    private String AD_UNIT_INTERSTITIAL_ID;
    public InterstitialAd interstitialAd;
    boolean showAds;

    @Override
    protected void attachBaseContext(Context base) {
        String locale = Locale.getDefault().getLanguage();
        super.attachBaseContext(LocaleHelper.onAttach(base, locale));
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_01_end);

        getWindow().setStatusBarColor(getColor(R.color.white_violet_02));
        getWindow().setNavigationBarColor(getColor(R.color.brown_01));

        // Shared Pref
        save = getSharedPreferences("Save", MODE_PRIVATE);
        editor = save.edit();
        boolean winOrLose = save.getBoolean(getString(R.string.game_01_winOrLose), false);
        int livesLeft = save.getInt(getString(R.string.game_01_livesLeft), 0);
        int bestScore = save.getInt(getString(R.string.game_01_bestScore), 0);
        showAds = save.getBoolean(getString(R.string.show_ads), true);

        // Find Views
        tv_bestScore = findViewById(R.id.best_score);
        tv_winOrLose = findViewById(R.id.tv_winOrLose);
        iv_winOrLose = findViewById(R.id.iv_winOrLose);
        life01 = findViewById(R.id.life_01);
        life02 = findViewById(R.id.life_02);
        life03 = findViewById(R.id.life_03);

        // Set Content
        if (winOrLose) {
            tv_winOrLose.setText(getString(R.string.win));
            iv_winOrLose.setImageResource(R.drawable.img_game_win);
        } else {
            tv_winOrLose.setText(getString(R.string.lose));
            iv_winOrLose.setImageResource(R.drawable.img_game_lose);
        }
        tv_bestScore.setText(String.valueOf(bestScore));

        switch (livesLeft) {
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


        // ADS
        if (showAds) {
            AD_UNIT_INTERSTITIAL_ID = getString(R.string.AD_UNIT_INTERSTITIAL_ID);
            loadInterstitialAd();

        }

        // Like App
        if (winOrLose) {
            ReviewManager manager = ReviewManagerFactory.create(this);
            Task<ReviewInfo> request = manager.requestReviewFlow();
            request.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // We can get the ReviewInfo object
                    ReviewInfo reviewInfo = task.getResult();
                    Task<Void> flow = manager.launchReviewFlow(Game_01_End.this, reviewInfo);
                    flow.addOnCompleteListener(task1 -> {

                    });
                }
            });
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
                        Game_01_End.this.interstitialAd = interstitialAd;
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        Game_01_End.this.interstitialAd = null;

                                        editor.putInt(getString(R.string.ads_game_counter), 0);
                                        editor.apply();

                                        Intent intent = new Intent(Game_01_End.this, SelectGame.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        Game_01_End.this.interstitialAd = null;
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        interstitialAd = null;
                    }
                });
    }

    public void onClickBtnBack(View view) {
        if (showAds && interstitialAd != null) {
            interstitialAd.show(Game_01_End.this);
        } else {
            try {
                Intent intent = new Intent(Game_01_End.this, SelectGame.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (showAds && interstitialAd != null) {
            interstitialAd.show(Game_01_End.this);
        } else {
            try {
                Intent intent = new Intent(Game_01_End.this, SelectGame.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }

    public void onClickExit(View view) {
        if (showAds && interstitialAd != null) {
            interstitialAd.show(Game_01_End.this);
        } else {
            try {
                Intent intent = new Intent(Game_01_End.this, SelectGame.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }

    public void onClickRepeat(View view) {
        try {
            Intent intent = new Intent(Game_01_End.this, Game_01.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            // -
        }
    }
}
