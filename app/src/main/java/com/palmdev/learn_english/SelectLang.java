package com.palmdev.learn_english;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;


public class SelectLang extends AppCompatActivity {

    final int[] flags = {
            R.id.img_flag_EN,
            R.id.img_flag_RO,
            R.id.img_flag_RU,
            R.id.img_flag_FR,
            R.id.img_flag_PL,
    };
    final int[] buttons = {
            R.id.btn_lang_EN,
            R.id.btn_lang_RO,
            R.id.btn_lang_RU,
            R.id.btn_lang_FR,
            R.id.btn_lang_PL,
    };

    @Override
    protected void attachBaseContext(Context base) {
        String locale = Locale.getDefault().getLanguage();
        super.attachBaseContext(com.palmdev.learn_english.LocaleHelper.onAttach(base, locale));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_lang);

        getWindow().setStatusBarColor(getColor(R.color.orange_02));
        getWindow().setNavigationBarColor(getColor(R.color.orange_01));

        for (int j : flags) {
            ImageView flag = findViewById(j);
            flag.setClipToOutline(true);
        }

        for (int j : buttons) {
            Button btn = findViewById(j);
            btn.setBackgroundResource(R.drawable.style_sel_lang);
        }
        Button button = findViewById(R.id.btn_lang_EN);
        button.setBackgroundResource(R.drawable.style_sel_lang_actual);
        String locale = Locale.getDefault().getLanguage();
        switch (locale) {
            case "ru":
                for (int j : buttons) {
                    Button btn = findViewById(j);
                    btn.setBackgroundResource(R.drawable.style_sel_lang);
                }
                Button button_ru = findViewById(R.id.btn_lang_RU);
                button_ru.setBackgroundResource(R.drawable.style_sel_lang_actual);
                break;
            case "ro":
                for (int j : buttons) {
                    Button btn = findViewById(j);
                    btn.setBackgroundResource(R.drawable.style_sel_lang);
                }
                Button button_ro = findViewById(R.id.btn_lang_RO);
                button_ro.setBackgroundResource(R.drawable.style_sel_lang_actual);
                break;
            case "fr":
                for (int j : buttons) {
                    Button btn = findViewById(j);
                    btn.setBackgroundResource(R.drawable.style_sel_lang);
                }
                Button button_fr = findViewById(R.id.btn_lang_FR);
                button_fr.setBackgroundResource(R.drawable.style_sel_lang_actual);
                break;
            case "pl":
                for (int j : buttons) {
                    Button btn = findViewById(j);
                    btn.setBackgroundResource(R.drawable.style_sel_lang);
                }
                Button button_pl = findViewById(R.id.btn_lang_PL);
                button_pl.setBackgroundResource(R.drawable.style_sel_lang_actual);
                break;
        }

    }

    public void onClickEN(View view){
        updateViews("en");
        for (int j: buttons){
            Button btn = findViewById(j);
            btn.setBackgroundResource(R.drawable.style_sel_lang);
        }
        Button btn = findViewById(R.id.btn_lang_EN);
        btn.setBackgroundResource(R.drawable.style_sel_lang_actual);
    }
    public void onClickFR(View view){
        updateViews("fr");
        for (int j: buttons){
            Button btn = findViewById(j);
            btn.setBackgroundResource(R.drawable.style_sel_lang);
        }
        Button btn = findViewById(R.id.btn_lang_FR);
        btn.setBackgroundResource(R.drawable.style_sel_lang_actual);
    }
    public void onClickRO(View view){
        updateViews("ro");
        for (int j : buttons) {
            Button btn = findViewById(j);
            btn.setBackgroundResource(R.drawable.style_sel_lang);
        }
        Button button = findViewById(R.id.btn_lang_RO);
        button.setBackgroundResource(R.drawable.style_sel_lang_actual);
    }
    public void onClickRU(View view){
        updateViews("ru");
        for (int j: buttons){
            Button btn = findViewById(j);
            btn.setBackgroundResource(R.drawable.style_sel_lang);
        }
        Button btn = findViewById(R.id.btn_lang_RU);
        btn.setBackgroundResource(R.drawable.style_sel_lang_actual);
    }
    public void onClickPL(View view){
        updateViews("pl");
        for (int j: buttons){
            Button btn = findViewById(j);
            btn.setBackgroundResource(R.drawable.style_sel_lang);
        }
        Button btn = findViewById(R.id.btn_lang_PL);
        btn.setBackgroundResource(R.drawable.style_sel_lang_actual);
    }

    private void updateViews(String languageCode) {
        Context context = com.palmdev.learn_english.LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();
        this.recreate();
    }

    //
    public void onClickBtnBack(View view) {
        try {
            Intent intent = new Intent(SelectLang.this, Settings.class);
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
            Intent intent = new Intent(SelectLang.this, Settings.class);
            startActivity(intent);
            finish();
        }catch (Exception e){
            // -
        }
    }
}
