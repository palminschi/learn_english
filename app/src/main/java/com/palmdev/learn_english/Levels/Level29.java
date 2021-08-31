package com.palmdev.learn_english.Levels;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.palmdev.learn_english.Arrays.Array_lvl_29;
import com.palmdev.learn_english.AssetsUtil;
import com.palmdev.learn_english.LocaleHelper;
import com.palmdev.learn_english.MainActivity;
import com.palmdev.learn_english.R;
import com.palmdev.learn_english.Repeat;
import com.palmdev.learn_english.SelectDayActivity;
import com.palmdev.learn_english.Shop;

import java.util.Locale;


public class Level29 extends AppCompatActivity {

    Array_lvl_29 array = new Array_lvl_29();
    private int count = 0;
    private FrameLayout container_LT, container_RT, container_LB, container_RB;
    final int[] progress = {R.id.point_1,R.id.point_2,R.id.point_3,R.id.point_4,R.id.point_5,
            R.id.point_6,R.id.point_7,R.id.point_8,R.id.point_9,R.id.point_10,R.id.point_11,
            R.id.point_12,R.id.point_13,R.id.point_14,R.id.point_15,R.id.point_16,R.id.point_17,
            R.id.point_18,R.id.point_19,R.id.point_20,};
    private Button btn_next;
    private TextView phrase_translate,word_LT,word_LB,word_RT,word_RB,word_title,phrase;
    private ImageView img_LT, img_LB, img_RT, img_RB;
    Animation animDisappear, animClick, animAppear;
    private int clickEnd = 0;
    Dialog dialogExit, dialogEnd;
    MediaPlayer mPlayer;
    ToggleButton sound_toggle;
    SharedPreferences save;
    SharedPreferences.Editor editor;
    //Ads
    private String AD_UNIT_INTERSTITIAL_ID;
    public InterstitialAd interstitialAd;

    @Override
    protected void attachBaseContext(Context base) {
        String locale = Locale.getDefault().getLanguage();
        super.attachBaseContext(LocaleHelper.onAttach(base, locale));
    }

    @SuppressLint({"ClickableViewAccessibility", "CommitPrefEdits"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal);

        // SharedPreferences
        save = getSharedPreferences("Save", MODE_PRIVATE);
        editor = save.edit();
        boolean soundValue = save.getBoolean("soundValue",true);
        //ADS
        boolean showAds = save.getBoolean(getString(R.string.show_ads),true);
        if (showAds) {
            LinearLayout container_ads = findViewById(R.id.container_ads);
            container_ads.setVisibility(View.VISIBLE);

            AdRequest adRequest = new AdRequest.Builder().build();
            AdView bannerAd = findViewById(R.id.bannerAd);
            bannerAd.loadAd(adRequest);

            AD_UNIT_INTERSTITIAL_ID = getString(R.string.AD_UNIT_INTERSTITIAL_ID);
            loadInterstitialAd();
        }

        // Find Views
        sound_toggle = findViewById(R.id.sound_toggle);
        img_LT = findViewById(R.id.img_LT);
        img_LB = findViewById(R.id.img_LB);
        img_RT = findViewById(R.id.img_RT);
        img_RB = findViewById(R.id.img_RB);
        img_LT.setClipToOutline(true);
        img_LB.setClipToOutline(true);
        img_RT.setClipToOutline(true);
        img_RB.setClipToOutline(true);
        word_LT = findViewById(R.id.word_LT);
        word_LB = findViewById(R.id.word_LB);
        word_RT = findViewById(R.id.word_RT);
        word_RB = findViewById(R.id.word_RB);
        word_title = findViewById(R.id.word_title);
        phrase = findViewById(R.id.phrase);
        phrase_translate = findViewById(R.id.phrase_translate);
        container_LT = findViewById(R.id.container_LT);
        container_RT = findViewById(R.id.container_RT);
        container_LB = findViewById(R.id.container_LB);
        container_RB = findViewById(R.id.container_RB);
        btn_next = findViewById(R.id.btn_next);

        // Content
        btn_next.setEnabled(false);
        getContent();
        getSound();
        sound_toggle.setChecked(soundValue);
        soundToggle();
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (soundValue){
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
        }else {
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);
        }

        // Animation
        animDisappear = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.disappear);
        animClick = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.click);
        animAppear = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.appear);


        // Button Back Header
        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> {
            try {
                getDialogExit();
            }catch (Exception e){
                // -
            }
        });
    }

    public void onClickAdsOff(View view){
        try {
            Intent intent = new Intent(Level29.this, Shop.class);
            startActivity(intent);
            finish();
        }catch (Exception e){
            //
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
                        Level29.this.interstitialAd = interstitialAd;
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        Level29.this.interstitialAd = null;
                                        Intent intent = new Intent(Level29.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        Level29.this.interstitialAd = null;
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        interstitialAd = null;
                    }
                });
    }

    public void getSound(){
        mPlayer = MediaPlayer.create(this,array.sounds[count]);
        mPlayer.start();
    }
    public void soundOnClick(View view){
        sound_toggle.setChecked(false);
        getSound();
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
    public void onBackPressed() {
        getDialogExit();
    }

    @SuppressLint("ClickableViewAccessibility")
    private  void getDialogExit(){
        dialogExit = new Dialog(this);
        dialogExit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogExit.setContentView(R.layout.dialog_exit);
        dialogExit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogExit.getWindow().setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        dialogExit.setCancelable(false);

        ImageView btn_close = dialogExit.findViewById(R.id.dialog_btn_close);
        btn_close.setOnTouchListener((v, event) -> {
            if (event.getAction()==MotionEvent.ACTION_UP){
                dialogExit.cancel();
            }
            return true;
        });
        Button btn_no = dialogExit.findViewById(R.id.dialog_btn_no);
        btn_no.setOnClickListener(v -> dialogExit.cancel());
        Button btn_yes = dialogExit.findViewById(R.id.dialog_btn_yes);
        btn_yes.setOnClickListener(v -> {
            try {
                if (interstitialAd != null) {
                    interstitialAd.show(Level29.this);
                } else {
                    Intent intent = new Intent(Level29.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }catch (Exception e){
                //
            }
        });
        dialogExit.show();
    }

    @SuppressLint("ClickableViewAccessibility")
    private  void getDialogEnd(){
        dialogEnd = new Dialog(this);
        dialogEnd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEnd.setContentView(R.layout.dialog_end);
        dialogEnd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogEnd.getWindow().setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        dialogEnd.setCancelable(false);

        ImageView btn_close = dialogEnd.findViewById(R.id.dialog_btn_close);
        btn_close.setOnTouchListener((v, event) -> {
            if (event.getAction()==MotionEvent.ACTION_UP){
                try {
                    if (interstitialAd != null) {
                        interstitialAd.show(Level29.this);
                    } else {
                        Intent intent = new Intent(Level29.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }catch (Exception e){
                    //
                }
            }
            return true;
        });
        Button btn_exit = dialogEnd.findViewById(R.id.dialog_btn_exit);
        btn_exit.setOnClickListener(v -> {
            try {
                if (interstitialAd != null) {
                    interstitialAd.show(Level29.this);
                } else {
                    Intent intent = new Intent(Level29.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }catch (Exception e){
                //
            }
        });
        Button btn_repeat = dialogEnd.findViewById(R.id.dialog_btn_repeat);
        btn_repeat.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(Level29.this, Repeat.class);
                startActivity(intent);
                finish();
            }catch (Exception e){
                //
            }
        });
        dialogEnd.show();
    }

    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
    // GAME CONTENT
    private void getContent(){
        btn_next.setEnabled(false);

        container_RT.setVisibility(View.VISIBLE);
        container_LT.setVisibility(View.VISIBLE);
        container_RB.setVisibility(View.VISIBLE);
        container_LB.setVisibility(View.VISIBLE);
        Drawable img_assets_LB = AssetsUtil.loadDrawable(getAssets(),array.img_LB[count]);
        img_LB.setImageDrawable(img_assets_LB);
        Drawable img_assets_LT = AssetsUtil.loadDrawable(getAssets(),array.img_LT[count]);
        img_LT.setImageDrawable(img_assets_LT);
        Drawable img_assets_RB = AssetsUtil.loadDrawable(getAssets(),array.img_RB[count]);
        img_RB.setImageDrawable(img_assets_RB);
        Drawable img_assets_RT = AssetsUtil.loadDrawable(getAssets(),array.img_RT[count]);
        img_RT.setImageDrawable(img_assets_RT);
        word_LB.setText(array.word_LB[count]);
        word_LT.setText(array.word_LT[count]);
        word_RB.setText(array.word_RB[count]);
        word_RT.setText(array.word_RT[count]);
        word_title.setText(array.word_title[count]);
        phrase.setText(array.phrase[count]);
        phrase_translate.setText(array.phrase_translate[count]);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        switch (count) {
            case 3: case 4: case 7: case 8: case 9: case 10: case 13: case 14: case 15: case 16: case 17: case 18: case 19:
                params.gravity = Gravity.CENTER;

                img_LT.setVisibility(View.GONE);
                container_LT.getLayoutParams().height = FrameLayout.LayoutParams.WRAP_CONTENT;
                word_LT.setLayoutParams(params);
                word_LT.setTextSize(18);
                word_LT.setPadding(0,dpToPx(10,this),0,dpToPx(10,this));

                img_RT.setVisibility(View.GONE);
                container_RT.getLayoutParams().height = FrameLayout.LayoutParams.WRAP_CONTENT;
                word_RT.setLayoutParams(params);
                word_RT.setTextSize(18);
                word_RT.setPadding(0,dpToPx(10,this),0,dpToPx(10,this));

                img_LB.setVisibility(View.GONE);
                container_LB.getLayoutParams().height = FrameLayout.LayoutParams.WRAP_CONTENT;
                word_LB.setLayoutParams(params);
                word_LB.setTextSize(18);
                word_LB.setPadding(0,dpToPx(10,this),0,dpToPx(10,this));

                img_RB.setVisibility(View.GONE);
                container_RB.getLayoutParams().height = FrameLayout.LayoutParams.WRAP_CONTENT;
                word_RB.setLayoutParams(params);
                word_RB.setTextSize(18);
                word_RB.setPadding(0,dpToPx(10,this),0,dpToPx(10,this));

                break;

            default:

                params.gravity = Gravity.CENTER | Gravity.BOTTOM;

                img_LT.setVisibility(View.VISIBLE);
                container_LT.getLayoutParams().height = dpToPx(150,this);
                word_LT.setLayoutParams(params);
                word_LT.setTextSize(16);
                word_LT.setPadding(0,0,0,0);

                img_RT.setVisibility(View.VISIBLE);
                container_RT.getLayoutParams().height = dpToPx(150,this);
                word_RT.setLayoutParams(params);
                word_RT.setTextSize(16);
                word_RT.setPadding(0,0,0,0);

                img_LB.setVisibility(View.VISIBLE);
                container_LB.getLayoutParams().height = dpToPx(150,this);
                word_LB.setLayoutParams(params);
                word_LB.setTextSize(16);
                word_LB.setPadding(0,0,0,0);

                img_RB.setVisibility(View.VISIBLE);
                container_RB.getLayoutParams().height = dpToPx(150, this);
                word_RB.setLayoutParams(params);
                word_RB.setTextSize(16);
                word_RB.setPadding(0,0,0,0);
        }
    }


    public void selectedFalse(View v){
        v.setBackgroundResource(R.drawable.style_img_background_false);
        container_RT.setEnabled(false);
        container_LB.setEnabled(false);
        container_RB.setEnabled(false);
        container_LT.setEnabled(false);

        v.startAnimation(animClick);
        v.postDelayed(() -> {
            v.startAnimation(animDisappear);
            v.setVisibility(View.INVISIBLE);
        },300);
        v.postDelayed(() -> {
            container_RT.setEnabled(true);
            container_LB.setEnabled(true);
            container_RB.setEnabled(true);
            container_LT.setEnabled(true);
        },600);


    }

    public void selectedTrue(View v){
        phrase_translate.startAnimation(animAppear);
        phrase_translate.setVisibility(View.VISIBLE);
        btn_next.setEnabled(true);
        container_RT.setEnabled(false);
        container_LB.setEnabled(false);
        container_RB.setEnabled(false);
        container_LT.setEnabled(false);

        v.setBackgroundResource(R.drawable.style_img_background_true);

        btn_next.setBackgroundResource(R.drawable.style_btn_true);
    }




    public void selectedLT(View view) {
        String wordTranslate = getResources().getString(array.word_translate[count]);
        String wordLT = getResources().getString(array.word_LT[count]);
        TextView tv = findViewById(progress[count]);

        if (wordTranslate.equals(wordLT)){
            selectedTrue(container_LT);
            tv.setBackgroundResource(R.drawable.style_points_true);
        }else{ selectedFalse(container_LT); }
    }

    public void selectedRT(View view) {
        String wordTranslate = getResources().getString(array.word_translate[count]);
        String wordRT = getResources().getString(array.word_RT[count]);
        TextView tv = findViewById(progress[count]);


        if (wordTranslate.equals(wordRT)){
            selectedTrue(container_RT);
            tv.setBackgroundResource(R.drawable.style_points_true);
        }else{ selectedFalse(container_RT); }
    }

    public void selectedLB(View view) {
        String wordTranslate = getResources().getString(array.word_translate[count]);
        String wordLB = getResources().getString(array.word_LB[count]);
        TextView tv = findViewById(progress[count]);

        if (wordTranslate.equals(wordLB)){
            selectedTrue(container_LB);
            tv.setBackgroundResource(R.drawable.style_points_true);
        }else{ selectedFalse(container_LB); }
    }

    public void selectedRB(View view) {
        String wordTranslate = getResources().getString(array.word_translate[count]);
        String wordRB = getResources().getString(array.word_RB[count]);
        TextView tv = findViewById(progress[count]);

        if (wordTranslate.equals(wordRB)){
            selectedTrue(container_RB);
            tv.setBackgroundResource(R.drawable.style_points_true);
        }else { selectedFalse(container_RB); }
    }

    public void onClickBtnNext(View view) {
        count++;
        phrase_translate.setVisibility(View.INVISIBLE);
        container_RT.setBackgroundResource(R.drawable.style_img_background);
        container_RB.setBackgroundResource(R.drawable.style_img_background);
        container_LT.setBackgroundResource(R.drawable.style_img_background);
        container_LB.setBackgroundResource(R.drawable.style_img_background);
        if (count<20) {
            getContent();
            getSound();
            btn_next.setBackgroundResource(R.drawable.style_btn);
            container_RT.setEnabled(true);
            container_LB.setEnabled(true);
            container_RB.setEnabled(true);
            container_LT.setEnabled(true);
        }else {
            // END LEVEL
            if (clickEnd != 0) {
                getDialogEnd();
            }else {
                setContentView(R.layout.end_level);
                saveData();
                clickEnd++;
            }
        }
    }

    private void saveData(){
        int completedLevels = save.getInt("completedLevels", 0);
        int words = save.getInt("wordsLearned",0);
        if (completedLevels == 28) {
            editor.putInt("completedLevels", 29);
            editor.apply();
            words += 20;
            editor.putInt("wordsLearned", words);
            editor.apply();
        }
    }


    public void closeKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
