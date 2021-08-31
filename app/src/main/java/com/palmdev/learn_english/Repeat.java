package com.palmdev.learn_english;

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
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.palmdev.learn_english.Arrays.Array_lvl_1;
import com.palmdev.learn_english.Arrays.Array_lvl_10;
import com.palmdev.learn_english.Arrays.Array_lvl_11;
import com.palmdev.learn_english.Arrays.Array_lvl_12;
import com.palmdev.learn_english.Arrays.Array_lvl_13;
import com.palmdev.learn_english.Arrays.Array_lvl_14;
import com.palmdev.learn_english.Arrays.Array_lvl_15;
import com.palmdev.learn_english.Arrays.Array_lvl_16;
import com.palmdev.learn_english.Arrays.Array_lvl_17;
import com.palmdev.learn_english.Arrays.Array_lvl_18;
import com.palmdev.learn_english.Arrays.Array_lvl_19;
import com.palmdev.learn_english.Arrays.Array_lvl_2;
import com.palmdev.learn_english.Arrays.Array_lvl_20;
import com.palmdev.learn_english.Arrays.Array_lvl_21;
import com.palmdev.learn_english.Arrays.Array_lvl_22;
import com.palmdev.learn_english.Arrays.Array_lvl_23;
import com.palmdev.learn_english.Arrays.Array_lvl_24;
import com.palmdev.learn_english.Arrays.Array_lvl_25;
import com.palmdev.learn_english.Arrays.Array_lvl_26;
import com.palmdev.learn_english.Arrays.Array_lvl_27;
import com.palmdev.learn_english.Arrays.Array_lvl_28;
import com.palmdev.learn_english.Arrays.Array_lvl_29;
import com.palmdev.learn_english.Arrays.Array_lvl_3;
import com.palmdev.learn_english.Arrays.Array_lvl_30;
import com.palmdev.learn_english.Arrays.Array_lvl_4;
import com.palmdev.learn_english.Arrays.Array_lvl_5;
import com.palmdev.learn_english.Arrays.Array_lvl_6;
import com.palmdev.learn_english.Arrays.Array_lvl_7;
import com.palmdev.learn_english.Arrays.Array_lvl_8;
import com.palmdev.learn_english.Arrays.Array_lvl_9;
import com.palmdev.learn_english.Levels.Level2;
import com.palmdev.learn_english.Levels_Start.Level2_Start;

import java.util.Locale;
import java.util.Random;

public class Repeat extends AppCompatActivity {

    final int[] letters = {
            R.id.letter1, R.id.letter2, R.id.letter3, R.id.letter4, R.id.letter5, R.id.letter6, R.id.letter7,
            R.id.letter8, R.id.letter9, R.id.letter10, R.id.letter11, R.id.letter12, R.id.letter13,
            R.id.letter14, R.id.letter15, R.id.letter16, R.id.letter17, R.id.letter18, R.id.letter19,
            R.id.letter20, R.id.letter21, R.id.letter22,};
    final int[] letters_background = {
            R.id.letter1_background, R.id.letter2_background, R.id.letter3_background,
            R.id.letter3_background, R.id.letter4_background, R.id.letter5_background,
            R.id.letter6_background, R.id.letter7_background, R.id.letter8_background,
            R.id.letter9_background, R.id.letter10_background, R.id.letter11_background,
            R.id.letter12_background, R.id.letter13_background, R.id.letter14_background,
            R.id.letter15_background, R.id.letter16_background, R.id.letter17_background,
            R.id.letter18_background, R.id.letter19_background, R.id.letter20_background,
            R.id.letter21_background, R.id.letter22_background,
    };
    final int[] progress = {R.id.point_1, R.id.point_2, R.id.point_3, R.id.point_4, R.id.point_5,
            R.id.point_6, R.id.point_7, R.id.point_8, R.id.point_9, R.id.point_10,};
    TextView wordTranslate, word, phrase, phraseTranslate;
    EditText editText;
    Random random = new Random();
    private int currentLevel;
    private int r1, r2, r3, r4, r5, r6, r7, r8, r9, r10;
    final int[] rNums = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private int count = 0;
    ImageView img;
    MediaPlayer mPlayer;
    //Ads
    private String AD_UNIT_INTERSTITIAL_ID;
    public InterstitialAd interstitialAd;
    SharedPreferences save;
    SharedPreferences.Editor editor;

    @Override
    protected void attachBaseContext(Context base) {
        String locale = Locale.getDefault().getLanguage();
        super.attachBaseContext(LocaleHelper.onAttach(base, locale));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repeat);

        // SharedPreferences
        save = getSharedPreferences("Save", MODE_PRIVATE);
        editor = save.edit();

        // Sound ON
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);

        // Find Views
        img = findViewById(R.id.img);
        wordTranslate = findViewById(R.id.word_translate);
        word = findViewById(R.id.word);
        phraseTranslate = findViewById(R.id.phrase_translate);
        phrase = findViewById(R.id.phrase);
        editText = findViewById(R.id.edit_text);

        //
        img.setClipToOutline(true);
        getCurrentLevel();
        getRandomNumbers();
        rNums[0] = r1;
        rNums[1] = r2;
        rNums[2] = r3;
        rNums[3] = r4;
        rNums[4] = r5;
        rNums[5] = r6;
        rNums[6] = r7;
        rNums[7] = r8;
        rNums[8] = r9;
        rNums[9] = r10;
        for (int i = 0; i < 23; i++) {
            FrameLayout frameLayout = findViewById(letters_background[i]);
            frameLayout.setVisibility(View.GONE);
        }
        FrameLayout frameLayout0 = findViewById(letters_background[0]);
        frameLayout0.setVisibility(View.VISIBLE);


        // --
        init();

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
                        Repeat.this.interstitialAd = interstitialAd;
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        Repeat.this.interstitialAd = null;
                                        Intent intent = new Intent(Repeat.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        Repeat.this.interstitialAd = null;
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        interstitialAd = null;
                    }
                });
    }

    // Buttons
    public void onClickBtnNext(View view) {

        phrase.setVisibility(View.INVISIBLE);
        phraseTranslate.setVisibility(View.INVISIBLE);

        if (count < 9) {
            for (int i = 0; i < 23; i++) {
                FrameLayout frameLayout = findViewById(letters_background[i]);
                frameLayout.setVisibility(View.GONE);
                frameLayout.setBackgroundResource(R.drawable.style_letters_background);
            }
            TextView point = findViewById(progress[count]);
            point.setBackgroundResource(R.drawable.style_points_true);
            FrameLayout frameLayout = findViewById(letters_background[0]);
            frameLayout.setVisibility(View.VISIBLE);
            count++;
            init();
            editText.setText("");
            getWindow().getDecorView().clearFocus();
        } else {
            TextView point = findViewById(progress[9]);
            point.setBackgroundResource(R.drawable.style_points_true);
            getDialogEnd();
        }
    }

    public void onClickControl(View view) {
        closeKeyBoard(view);
        // True or false
        String s1 = editText.getText().toString().toLowerCase();
        String s2 = word.getText().toString().toLowerCase();

        if (s2.equals(s1)) {
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        } else {
            editText.setBackgroundResource(R.drawable.style_edittext_false);
        }

        // Make word visible
        phrase.setVisibility(View.VISIBLE);
        phraseTranslate.setVisibility(View.VISIBLE);
        TextView tv;
        for (int i = 0; i < 23; i++) {
            FrameLayout frameLayout2 = findViewById(letters_background[i]);
            frameLayout2.setBackgroundResource(R.drawable.style_letters_background_true);
        }
        char[] lettersArray = word.getText().toString().toCharArray();
        for (int i = 0; i < word.length(); i++) {
            tv = findViewById(letters[i]);
            tv.setText(String.valueOf(lettersArray[i]));
        }
    }

    public void onClickSound(View view) {
        mPlayer.start();
    }

    public void onClickBtnBack(View view) {
        try {
            if (interstitialAd != null) {
                interstitialAd.show(Repeat.this);
            } else {
                Intent intent = new Intent(Repeat.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }catch (Exception e){
            //
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if (interstitialAd != null) {
                interstitialAd.show(Repeat.this);
            } else {
                Intent intent = new Intent(Repeat.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }catch (Exception e){
            //
        }
    }


    private void getCurrentLevel() {
        currentLevel = save.getInt(getResources().getString(R.string.completedLevels), 1);
    }

    private void getRandomNumbers() {
        r1 = random.nextInt(20);

        r2 = random.nextInt(20);
        while (r2 == r1) {
            r2 = random.nextInt(20);
        }

        r3 = random.nextInt(20);
        while (r3 == r2 | r3 == r1) {
            r3 = random.nextInt(20);
        }

        r4 = random.nextInt(20);
        while (r4 == r3 | r4 == r2 | r4 == r1) {
            r4 = random.nextInt(20);
        }

        r5 = random.nextInt(20);
        while (r5 == r4 | r5 == r3 | r5 == r2 | r5 == r1) {
            r5 = random.nextInt(20);
        }

        r6 = random.nextInt(20);
        while (r6 == r5 | r6 == r4 | r6 == r3 | r6 == r2 | r6 == r1) {
            r6 = random.nextInt(20);
        }

        r7 = random.nextInt(20);
        while (r7 == r6 | r7 == r5 | r7 == r4 | r7 == r3 | r7 == r2 | r7 == r1) {
            r7 = random.nextInt(20);
        }

        r8 = random.nextInt(20);
        while (r8 == r7 | r8 == r6 | r8 == r5 | r8 == r4 | r8 == r3 | r8 == r2 | r8 == r1) {
            r8 = random.nextInt(20);
        }

        r9 = random.nextInt(20);
        while (r9 == r8 | r9 == r7 | r9 == r6 | r9 == r5 | r9 == r4 | r9 == r3 | r9 == r2 | r9 == r1) {
            r9 = random.nextInt(20);
        }

        r10 = random.nextInt(20);
        while (r10 == r9 | r10 == r8 | r10 == r7 | r10 == r6 | r10 == r5 | r10 == r4 | r10 == r3 | r10 == r2 | r10 == r1) {
            r10 = random.nextInt(20);
        }
    }

    public void hideLetters(TextView s, char[] c) {
        switch (s.length()) {
            case 3:
                c[1] = '-';
                break;
            case 4:
                c[1] = '-';
                c[2] = '-';
                break;
            case 5:
                c[2] = '-';
                c[3] = '-';
                break;
            case 6:
                c[3] = '-';
                c[4] = '-';
                break;
            case 7:
            case 8:
                c[4] = '-';
                c[5] = '-';
                c[6] = '-';
                break;
            case 9:
                c[5] = '-';
                c[6] = '-';
                c[7] = '-';
                break;
            case 10:
            case 11:
                c[5] = '-';
                c[6] = '-';
                c[7] = '-';
                c[8] = '-';
                break;
            case 12:
                c[5] = '-';
                c[6] = '-';
                c[7] = '-';
                c[8] = '-';
                c[9] = '-';
                break;
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 21:
            case 22:
            case 23:
                c[6] = '-';
                c[7] = '-';
                c[8] = '-';
                c[9] = '-';
                c[10] = '-';
                break;
            default:
                c[0] = '-';
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void getDialogEnd() {
        Dialog dialogEnd;
        dialogEnd = new Dialog(this);
        dialogEnd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEnd.setContentView(R.layout.dialog_end);
        dialogEnd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogEnd.getWindow().setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        dialogEnd.setCancelable(false);

        ImageView btn_close = dialogEnd.findViewById(R.id.dialog_btn_close);
        btn_close.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                try {
                    if (interstitialAd != null) {
                        interstitialAd.show(Repeat.this);
                    } else {
                        Intent intent = new Intent(Repeat.this, MainActivity.class);
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
                    interstitialAd.show(Repeat.this);
                } else {
                    Intent intent = new Intent(Repeat.this, MainActivity.class);
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
                Intent intent = new Intent(Repeat.this, Repeat.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                //
            }
        });
        //New texts
        btn_repeat.setText(R.string.dialog_exit_btn_yes);
        TextView dialogEnd_text1 = dialogEnd.findViewById(R.id.dialog_text1);
        dialogEnd_text1.setText(R.string.repeat_dialog_end_title);
        TextView dialogEnd_text2 = dialogEnd.findViewById(R.id.dialog_text2);
        dialogEnd_text2.setText(R.string.repeat_dialog_end_sub_title);
        //
        dialogEnd.show();
    }



    public void closeKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        getWindow().getDecorView().clearFocus();
    }

    // GAME CONTENT
    private void init () {
        editText.setBackgroundResource(R.drawable.style_edittext_black);

        switch (currentLevel) {
            case 1:
                getContentLevel(1);
                break;

            case 2:
                if (count > 5)
                    { getContentLevel(1); }
                else
                    { getContentLevel(2); }
                break;

            case 3:

                if (count < 3)
                    { getContentLevel(3); }
                else if (count < 6 & count > 3)
                    { getContentLevel(1); }
                else
                    { getContentLevel(2); }
                break;

            case 4:

                if (count < 4) {
                    getContentLevel(4);
                } else if (count < 6 & count > 4) {
                    getContentLevel(random.nextInt(2));
                } else {
                    getContentLevel(3);
                }
                break;

            case 5:

                if (count < 4) {
                    getContentLevel(5);
                } else if (count < 6 & count > 4) {
                    getContentLevel(random.nextInt(3));
                } else {
                    getContentLevel(4);
                }
                break;
            case 6:

                if (count < 4) {
                    getContentLevel(6);
                } else if (count < 6 & count > 4) {
                    getContentLevel(random.nextInt(4));
                } else {
                    getContentLevel(5);
                }
                break;
            case 7:

                if (count < 4) {
                    getContentLevel(7);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(5));
                } else {
                    getContentLevel(6);
                }
                break;
            case 8:

                if (count < 4) {
                    getContentLevel(8);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(6));
                } else {
                    getContentLevel(7);
                }
                break;
            case 9:

                if (count < 4) {
                    getContentLevel(9);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(7));
                } else {
                    getContentLevel(8);
                }
                break;
            case 10:

                if (count < 4) {
                    getContentLevel(10);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(8));
                } else {
                    getContentLevel(9);
                }
                break;
            case 11:

                if (count < 4) {
                    getContentLevel(11);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(9));
                } else {
                    getContentLevel(10);
                }
                break;
            case 12:

                if (count < 4) {
                    getContentLevel(12);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(11));
                } else {
                    getContentLevel(6);
                }
                break;
            case 13:

                if (count < 4) {
                    getContentLevel(13);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(12));
                } else {
                    getContentLevel(11);
                }
                break;
            case 14:

                if (count < 4) {
                    getContentLevel(14);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(13));
                } else {
                    getContentLevel(5);
                }
                break;
            case 15:

                if (count < 4) {
                    getContentLevel(15);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(13));
                } else {
                    getContentLevel(8);
                }
                break;
            case 16:

                if (count < 4) {
                    getContentLevel(16);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(14));
                } else {
                    getContentLevel(15);
                }
                break;
            case 17:

                if (count < 4) {
                    getContentLevel(17);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(14));
                } else {
                    getContentLevel(15);
                }
                break;
            case 18:

                if (count < 4) {
                    getContentLevel(18);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(14));
                } else {
                    getContentLevel(16);
                }
                break;
            case 19:

                if (count < 4) {
                    getContentLevel(19);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(17));
                } else {
                    getContentLevel(18);
                }
                break;
            case 20:

                if (count < 4) {
                    getContentLevel(20);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(18));
                } else {
                    getContentLevel(19);
                }
                break;
            case 21:

                if (count < 4) {
                    getContentLevel(21);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(19));
                } else {
                    getContentLevel(20);
                }
                break;
            case 22:

                if (count < 4) {
                    getContentLevel(22);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(20));
                } else {
                    getContentLevel(21);
                }
                break;
            case 23:

                if (count < 4) {
                    getContentLevel(23);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(21));
                } else {
                    getContentLevel(22);
                }
                break;
            case 24:

                if (count < 4) {
                    getContentLevel(24);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(22));
                } else {
                    getContentLevel(23);
                }
                break;
            case 25:

                if (count < 4) {
                    getContentLevel(25);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(23));
                } else {
                    getContentLevel(24);
                }
                break;
            case 26:

                if (count < 4) {
                    getContentLevel(26);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(24));
                } else {
                    getContentLevel(25);
                }
                break;
            case 27:

                if (count < 4) {
                    getContentLevel(27);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(25));
                } else {
                    getContentLevel(26);
                }
                break;
            case 28:

                if (count < 4) {
                    getContentLevel(28);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(26));
                } else {
                    getContentLevel(27);
                }
                break;
            case 29:

                if (count < 4) {
                    getContentLevel(29);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(27));
                } else {
                    getContentLevel(28);
                }
                break;
            case 30:

                if (count < 4) {
                    getContentLevel(30);
                } else if (count < 7 & count > 4) {
                    getContentLevel(random.nextInt(28));
                } else {
                    getContentLevel(29);
                }
                break;

        }
    }

    public void getContentLevel(int arrayNum) {

        if (arrayNum == 1) {
            Array_lvl_1 array = new Array_lvl_1();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        } else if (arrayNum == 2) {
            Array_lvl_2 array = new Array_lvl_2();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 3) {
            Array_lvl_3 array = new Array_lvl_3();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 4) {
            Array_lvl_4 array = new Array_lvl_4();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 5) {
            Array_lvl_5 array = new Array_lvl_5();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 6) {
            Array_lvl_6 array = new Array_lvl_6();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 7) {
            Array_lvl_7 array = new Array_lvl_7();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 8) {
            Array_lvl_8 array = new Array_lvl_8();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 9) {
            Array_lvl_9 array = new Array_lvl_9();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 10) {
            Array_lvl_10 array = new Array_lvl_10();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 11) {
            Array_lvl_11 array = new Array_lvl_11();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 12) {
            Array_lvl_12 array = new Array_lvl_12();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 13) {
            Array_lvl_13 array = new Array_lvl_13();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 14) {
            Array_lvl_14 array = new Array_lvl_14();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }
        else if (arrayNum == 15) {
            Array_lvl_15 array = new Array_lvl_15();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 16) {
            Array_lvl_16 array = new Array_lvl_16();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 17) {
            Array_lvl_17 array = new Array_lvl_17();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 18) {
            Array_lvl_18 array = new Array_lvl_18();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 19) {
            Array_lvl_19 array = new Array_lvl_19();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 20) {
            Array_lvl_20 array = new Array_lvl_20();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }
        else if (arrayNum == 21) {
            Array_lvl_21 array = new Array_lvl_21();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 22) {
            Array_lvl_22 array = new Array_lvl_22();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 23) {
            Array_lvl_23 array = new Array_lvl_23();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 24) {
            Array_lvl_24 array = new Array_lvl_24();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 25) {
            Array_lvl_25 array = new Array_lvl_25();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 26) {
            Array_lvl_26 array = new Array_lvl_26();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 27) {
            Array_lvl_27 array = new Array_lvl_27();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 28) {
            Array_lvl_28 array = new Array_lvl_28();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 29) {
            Array_lvl_29 array = new Array_lvl_29();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }else if (arrayNum == 30) {
            Array_lvl_30 array = new Array_lvl_30();
            Drawable img_asset = AssetsUtil.loadDrawable(getAssets(), array.word_img[rNums[count]]);
            img.setImageDrawable(img_asset);
            wordTranslate.setText(array.word_translate[rNums[count]]);
            word.setText(array.word_title[rNums[count]]);
            phrase.setText(array.phrase[rNums[count]]);
            phraseTranslate.setText(array.phrase_translate[rNums[count]]);
            mPlayer = MediaPlayer.create(this,array.sounds[rNums[count]]);

            char[] lettersArray0 = word.getText().toString().toCharArray();
            hideLetters(word, lettersArray0);

            for (int i = 0; i < word.length(); i++) {
                FrameLayout frameLayout = findViewById(letters_background[i + 1]);
                frameLayout.setVisibility(View.VISIBLE);
                TextView tv = findViewById(letters[i]);
                tv.setText(String.valueOf(lettersArray0[i]));
            }
        }
    }



}


