package com.palmdev.learn_english;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class PromoCode extends AppCompatActivity {

    EditText editText;
    SharedPreferences save;
    SharedPreferences.Editor editor;

    @Override
    protected void attachBaseContext(Context base) {
        String locale = Locale.getDefault().getLanguage();
        super.attachBaseContext(LocaleHelper.onAttach(base, locale));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promocode);

        getWindow().setStatusBarColor(getColor(R.color.orange_02));
        getWindow().setNavigationBarColor(getColor(R.color.orange_01));
    }



    public void putPromoCode(View view){
        editText = findViewById(R.id.edit_text);
        String text = editText.getText().toString();
        String controlPut = getString(R.string.promo_put);
        String controlDis = getString(R.string.promo_disable);
        save = getSharedPreferences(getString(R.string.save), MODE_PRIVATE);
        editor = save.edit();

        if (text.equals(controlPut)){
            editor.putBoolean(getString(R.string.show_ads),false);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(controlDis)){
            editor.putBoolean(getString(R.string.show_ads),true);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.resetlvl))) {
            editor.putInt(getString(R.string.completedLevels), 0);
            editor.putInt(getString(R.string.wordsLearned), 0);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.reset_lastWord))) {
            editor.putInt(getString(R.string.game_01_lastWord), 0);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set01lvl))) {
            editor.putInt(getString(R.string.completedLevels), 1);
            editor.putInt(getString(R.string.wordsLearned), 20);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set02lvl))) {
                editor.putInt(getString(R.string.completedLevels), 2);
                editor.putInt(getString(R.string.wordsLearned), 40);
                editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set03lvl))) {
            editor.putInt(getString(R.string.completedLevels), 3);
            editor.putInt(getString(R.string.wordsLearned), 60);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set04lvl))) {
            editor.putInt(getString(R.string.completedLevels), 4);
            editor.putInt(getString(R.string.wordsLearned), 80);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set05lvl))) {
            editor.putInt(getString(R.string.completedLevels), 5);
            editor.putInt(getString(R.string.wordsLearned), 100);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set06lvl))) {
            editor.putInt(getString(R.string.completedLevels), 6);
            editor.putInt(getString(R.string.wordsLearned), 120);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set07lvl))) {
            editor.putInt(getString(R.string.completedLevels), 7);
            editor.putInt(getString(R.string.wordsLearned), 140);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set08lvl))) {
            editor.putInt(getString(R.string.completedLevels), 8);
            editor.putInt(getString(R.string.wordsLearned), 160);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set09lvl))) {
            editor.putInt(getString(R.string.completedLevels), 9);
            editor.putInt(getString(R.string.wordsLearned), 180);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set10lvl))) {
            editor.putInt(getString(R.string.completedLevels), 10);
            editor.putInt(getString(R.string.wordsLearned), 200);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set11lvl))) {
            editor.putInt(getString(R.string.completedLevels), 11);
            editor.putInt(getString(R.string.wordsLearned), 220);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set12lvl))) {
            editor.putInt(getString(R.string.completedLevels), 12);
            editor.putInt(getString(R.string.wordsLearned), 240);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set13lvl))) {
            editor.putInt(getString(R.string.completedLevels), 13);
            editor.putInt(getString(R.string.wordsLearned), 260);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set14lvl))) {
            editor.putInt(getString(R.string.completedLevels), 14);
            editor.putInt(getString(R.string.wordsLearned), 280);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set15lvl))) {
            editor.putInt(getString(R.string.completedLevels), 15);
            editor.putInt(getString(R.string.wordsLearned), 300);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set16lvl))) {
            editor.putInt(getString(R.string.completedLevels), 16);
            editor.putInt(getString(R.string.wordsLearned), 320);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set17lvl))) {
            editor.putInt(getString(R.string.completedLevels), 17);
            editor.putInt(getString(R.string.wordsLearned), 340);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set18lvl))) {
            editor.putInt(getString(R.string.completedLevels), 18);
            editor.putInt(getString(R.string.wordsLearned), 360);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set19lvl))) {
            editor.putInt(getString(R.string.completedLevels), 19);
            editor.putInt(getString(R.string.wordsLearned), 380);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set20lvl))) {
            editor.putInt(getString(R.string.completedLevels), 20);
            editor.putInt(getString(R.string.wordsLearned), 400);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set21lvl))) {
            editor.putInt(getString(R.string.completedLevels), 21);
            editor.putInt(getString(R.string.wordsLearned), 420);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set22lvl))) {
            editor.putInt(getString(R.string.completedLevels), 22);
            editor.putInt(getString(R.string.wordsLearned), 440);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set23lvl))) {
            editor.putInt(getString(R.string.completedLevels), 23);
            editor.putInt(getString(R.string.wordsLearned), 460);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set24lvl))) {
            editor.putInt(getString(R.string.completedLevels), 24);
            editor.putInt(getString(R.string.wordsLearned), 480);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set25lvl))) {
            editor.putInt(getString(R.string.completedLevels), 25);
            editor.putInt(getString(R.string.wordsLearned), 500);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set26lvl))) {
            editor.putInt(getString(R.string.completedLevels), 26);
            editor.putInt(getString(R.string.wordsLearned), 520);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set27lvl))) {
            editor.putInt(getString(R.string.completedLevels), 27);
            editor.putInt(getString(R.string.wordsLearned), 540);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set28lvl))) {
            editor.putInt(getString(R.string.completedLevels), 28);
            editor.putInt(getString(R.string.wordsLearned), 560);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set29lvl))) {
            editor.putInt(getString(R.string.completedLevels), 29);
            editor.putInt(getString(R.string.wordsLearned), 580);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else if (text.equals(getString(R.string.set30lvl))) {
            editor.putInt(getString(R.string.completedLevels), 30);
            editor.putInt(getString(R.string.wordsLearned), 600);
            editText.setBackgroundResource(R.drawable.style_edittext_true);
        }else {
            editText.setBackgroundResource(R.drawable.style_edittext_false);
        }

        editor.apply();

        closeKeyBoard(view);
        editText.setText("");
    }

    public void closeKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        getWindow().getDecorView().clearFocus();
    }

    public void onClickBtnBack(View view) {
        try {
            Intent intent = new Intent(PromoCode.this,Settings.class);
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
            Intent intent = new Intent(PromoCode.this,Settings.class);
            startActivity(intent);
            finish();
        }catch (Exception e){
            // -
        }
    }
}
