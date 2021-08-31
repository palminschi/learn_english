package com.palmdev.learn_english;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.FormError;
import com.google.android.ump.UserMessagingPlatform;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast toast;
    SharedPreferences save;
    SharedPreferences.Editor editor;
    public static final String PREF_FILE= "MyPref";
    public static final String PURCHASE_KEY= "purchase";
    // Funding Choice
    private ConsentInformation consentInformation;
    private ConsentForm consentForm;
    //Ads
    private String AD_UNIT_INTERSTITIAL_ID;
    public InterstitialAd interstitialAd;

    @Override
    protected void attachBaseContext(Context base) {
        String locale = Locale.getDefault().getLanguage();
        super.attachBaseContext(com.palmdev.learn_english.LocaleHelper.onAttach(base, locale));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setNavigationBarColor(getColor(R.color.orange_01));
        getWindow().setStatusBarColor(getColor(R.color.violet_02));


        save = getSharedPreferences("Save",MODE_PRIVATE);
        editor = save.edit();

        // ADS
        MobileAds.initialize( this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        FirebaseApp.initializeApp(this);

        // Purchase
        //item Purchased
        if(getPurchaseValueFromPref()){
            editor.putBoolean(getString(R.string.show_ads),false);
        }
        //item not Purchased
        else{
            editor.putBoolean(getString(R.string.show_ads),true);
        }
        editor.apply();

        getNumWords();

        // Funding Choice
        ConsentRequestParameters params = new ConsentRequestParameters
                .Builder()
                .setTagForUnderAgeOfConsent(false)
                .build();

        consentInformation = UserMessagingPlatform.getConsentInformation(this);
        consentInformation.requestConsentInfoUpdate(
                this,
                params,
                new ConsentInformation.OnConsentInfoUpdateSuccessListener() {
                    @Override
                    public void onConsentInfoUpdateSuccess() {
                        // The consent information state was updated.
                        // You are now ready to check if a form is available.
                        if (consentInformation.isConsentFormAvailable()) {
                            loadForm();
                        }
                    }
                },
                new ConsentInformation.OnConsentInfoUpdateFailureListener() {
                    @Override
                    public void onConsentInfoUpdateFailure(FormError formError) {
                        // Handle the error.

                    }
                });
        // Push
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        return;
                    }

                    String token = task.getResult();
                    Log.d("TAG", "Token ->" + token);
                });

        // Interstitial Ad
        int completedLevels = save.getInt(getString(R.string.completedLevels), 0);
        if (completedLevels >= 2) {
            boolean showAds = save.getBoolean(getString(R.string.show_ads),true);
            if (showAds) {
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
                        MainActivity.this.interstitialAd = interstitialAd;
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        MainActivity.this.interstitialAd = null;
                                        Intent intent = new Intent(MainActivity.this, com.palmdev.learn_english.Repeat.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        MainActivity.this.interstitialAd = null;
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        interstitialAd = null;
                    }
                });
    }

    // Funding Choice
    public void loadForm() {
        UserMessagingPlatform.loadConsentForm(
                this,
                new UserMessagingPlatform.OnConsentFormLoadSuccessListener() {
                    @Override
                    public void onConsentFormLoadSuccess(ConsentForm consentForm) {
                        MainActivity.this.consentForm = consentForm;
                        if(consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.REQUIRED) {
                            consentForm.show(
                                    MainActivity.this,
                                    new ConsentForm.OnConsentFormDismissedListener() {
                                        @Override
                                        public void onConsentFormDismissed(@Nullable FormError formError) {
                                            // Handle dismissal by reloading form.
                                            loadForm();
                                        }
                                    });

                        }
                    }
                },
                new UserMessagingPlatform.OnConsentFormLoadFailureListener() {
                    @Override
                    public void onConsentFormLoadFailure(FormError formError) {
                        // Handle the error
                    }
                }
        );
    }

    // Purchase
    private boolean getPurchaseValueFromPref(){
        return getPreferenceObject().getBoolean( PURCHASE_KEY,false);
    }
    private SharedPreferences getPreferenceObject() {
        return getApplicationContext().getSharedPreferences(PREF_FILE, 0);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            toast.cancel();
            super.onBackPressed();
        }else {
            backPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(getBaseContext(),R.string.toastExitApp,Toast.LENGTH_LONG);
            toast.show();
        }
    }
    private void getNumWords(){
        TextView wordsNum = findViewById(R.id.words_num);


        final int wordsLearned = save.getInt("wordsLearned",0);
        final String wordsToString = Integer.toString(wordsLearned);

        wordsNum.setText(wordsToString);

    }

    public void onClickButtonOne(View view) {
        try {
            Intent intent = new Intent(MainActivity.this, com.palmdev.learn_english.SelectDayActivity.class);
            startActivity(intent);finish();
        }catch (Exception e){
            //-
        }
    }
    public void onClickButtonTwo(View view) {
        int completedLevels = save.getInt("completedLevels",0);
        if (completedLevels == 0){
            toast = Toast.makeText(getBaseContext(),getString(R.string.toastRepeat),Toast.LENGTH_LONG);
            toast.show();
        }else {
            try {
                if (interstitialAd != null) {
                    interstitialAd.show(MainActivity.this);
                } else {
                    Intent intent = new Intent(MainActivity.this, com.palmdev.learn_english.Repeat.class);
                    startActivity(intent);
                    finish();
                }
            }catch (Exception e){
                //
            }
        }
    }
    public void onClickButtonThree(View view) {
        try {
            Intent intent = new Intent(MainActivity.this, SelectGame.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            //-
        }
    }

    public void onClickBtnShop(View view) {
        try {
            Intent intent = new Intent(MainActivity.this, com.palmdev.learn_english.Shop.class);
            startActivity(intent);
            finish();
        }catch (Exception e){
            // -
        }
    }

    public void onClickBtnSettings(View view) {
        try {
            Intent intent = new Intent(MainActivity.this,Settings.class);
            startActivity(intent);
            finish();
        }catch (Exception e){
            // -
        }
    }


}