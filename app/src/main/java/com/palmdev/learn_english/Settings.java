package com.palmdev.learn_english;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Settings extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context base) {
        String locale = Locale.getDefault().getLanguage();
        super.attachBaseContext(com.palmdev.learn_english.LocaleHelper.onAttach(base, locale));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        getWindow().setStatusBarColor(getColor(R.color.orange_02));
        getWindow().setNavigationBarColor(getColor(R.color.orange_01));
        ImageView flag = findViewById(R.id.img_flag);
        flag.setClipToOutline(true);

        flag.setImageResource(R.drawable.flag_eng);
        String locale = Locale.getDefault().getLanguage();
        switch (locale) {
            case "ru":
                flag.setImageResource(R.drawable.flag_russia);
                break;
            case "ro":
                flag.setImageResource(R.drawable.flag_romania);
                break;
            case "fr":
                flag.setImageResource(R.drawable.flag_france);
                break;
            case "pl":
                flag.setImageResource(R.drawable.flag_poland);
                break;
        }
    }


    public void onClickSelLang(View view) {
        try {
            Intent intent = new Intent(Settings.this, com.palmdev.learn_english.SelectLang.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            // -
        }
    }

    public void onClickLike(View view) {
        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public void onClickOther(View view) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://developer?id=DevPalm")));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=DevPalm")));
        }
    }

    public void onClickPromoCode(View view) {
        try {
            Intent intent = new Intent(Settings.this, com.palmdev.learn_english.PromoCode.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            // -
        }
    }

    public void onClickHelp(View view) {
        Intent email = new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:" + getString(R.string.support_email)));
        email.putExtra(Intent.EXTRA_SUBJECT, "Support Learn German");
        startActivity(email);
    }

    public void onClickPolicy(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacy_policy)));
        startActivity(browserIntent);
    }

    public void onClickBtnBack(View view) {
        try {
            Intent intent = new Intent(Settings.this, com.palmdev.learn_english.MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            // -
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            Intent intent = new Intent(Settings.this, com.palmdev.learn_english.MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            // -
        }
    }


}
