package com.palmdev.learn_english;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.palmdev.learn_english.Games.Game_01;
import com.palmdev.learn_english.Games.Game_02;
import com.palmdev.learn_english.Games.Game_03;

import java.util.Locale;

public class SelectGame extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context base) {
        String locale = Locale.getDefault().getLanguage();
        super.attachBaseContext(com.palmdev.learn_english.LocaleHelper.onAttach(base, locale));
    }

    //Ads
    private String AD_UNIT_INTERSTITIAL_ID;
    public InterstitialAd interstitialAd01;
    public InterstitialAd interstitialAd02;
    public InterstitialAd interstitialAd03;
    SharedPreferences save;
    SharedPreferences.Editor editor;
    int completedLevels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_game);

        getWindow().setStatusBarColor(getColor(R.color.orange_02));
        getWindow().setNavigationBarColor(getColor(R.color.orange_01));

        //ADS
        // SharedPreferences
        save = getSharedPreferences("Save", MODE_PRIVATE);
        editor = save.edit();
        completedLevels = save.getInt(getString(R.string.completedLevels), 0);
        boolean showAds = save.getBoolean(getString(R.string.show_ads),true);
        if (showAds & completedLevels >= 1) {
            AD_UNIT_INTERSTITIAL_ID = getString(R.string.AD_UNIT_INTERSTITIAL_ID);
            loadInterstitialAd01();
            loadInterstitialAd02();
            loadInterstitialAd03();
        }
    }

    public void loadInterstitialAd01() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                this,
                AD_UNIT_INTERSTITIAL_ID,
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        SelectGame.this.interstitialAd01 = interstitialAd;
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        SelectGame.this.interstitialAd01 = null;
                                        Intent intent = new Intent(SelectGame.this, Game_01.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        SelectGame.this.interstitialAd01 = null;
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        interstitialAd01 = null;
                    }
                });
    }
    public void loadInterstitialAd02() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                this,
                AD_UNIT_INTERSTITIAL_ID,
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        SelectGame.this.interstitialAd02 = interstitialAd;
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        SelectGame.this.interstitialAd02 = null;
                                        Intent intent = new Intent(SelectGame.this, Game_02.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        SelectGame.this.interstitialAd02 = null;
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        interstitialAd02 = null;
                    }
                });
    }
    public void loadInterstitialAd03() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                this,
                AD_UNIT_INTERSTITIAL_ID,
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        SelectGame.this.interstitialAd03 = interstitialAd;
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        SelectGame.this.interstitialAd03 = null;
                                        Intent intent = new Intent(SelectGame.this, Game_03.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        SelectGame.this.interstitialAd03 = null;
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        interstitialAd03 = null;
                    }
                });
    }

    public void onClickGame01(View view){
        try {
            if (interstitialAd01 != null & completedLevels >= 1) {
                interstitialAd01.show(SelectGame.this);
            } else {
                Intent intent = new Intent(SelectGame.this, Game_01.class);
                startActivity(intent);
                finish();
            }
        }catch (Exception e){
            //
        }
    }
    public void onClickGame02(View view){
        try {
            if (interstitialAd02 != null & completedLevels >= 1) {
                interstitialAd02.show(SelectGame.this);
            } else {
                Intent intent = new Intent(SelectGame.this, Game_02.class);
                startActivity(intent);
                finish();
            }
        }catch (Exception e){
            //
        }
    }
    public void onClickGame03(View view){
        try {
            if (interstitialAd03 != null & completedLevels >= 1) {
                interstitialAd03.show(SelectGame.this);
            } else {
                Intent intent = new Intent(SelectGame.this, Game_03.class);
                startActivity(intent);
                finish();
            }
        }catch (Exception e){
            //
        }
    }

    public void onClickBtnBack(View view) {
        try {
            Intent intent = new Intent(SelectGame.this, com.palmdev.learn_english.MainActivity.class);
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
            Intent intent = new Intent(SelectGame.this, com.palmdev.learn_english.MainActivity.class);
            startActivity(intent);
            finish();
        }catch (Exception e){
            // -
        }
    }

    public void onClickMore(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.palmdev.expressenglish")));
    }
}
