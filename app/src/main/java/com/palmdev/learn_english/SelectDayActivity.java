package com.palmdev.learn_english;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.palmdev.learn_english.Levels_Start.Level10_Start;
import com.palmdev.learn_english.Levels_Start.Level11_Start;
import com.palmdev.learn_english.Levels_Start.Level12_Start;
import com.palmdev.learn_english.Levels_Start.Level13_Start;
import com.palmdev.learn_english.Levels_Start.Level14_Start;
import com.palmdev.learn_english.Levels_Start.Level15_Start;
import com.palmdev.learn_english.Levels_Start.Level16_Start;
import com.palmdev.learn_english.Levels_Start.Level17_Start;
import com.palmdev.learn_english.Levels_Start.Level18_Start;
import com.palmdev.learn_english.Levels_Start.Level19_Start;
import com.palmdev.learn_english.Levels_Start.Level1_Start;
import com.palmdev.learn_english.Levels_Start.Level20_Start;
import com.palmdev.learn_english.Levels_Start.Level21_Start;
import com.palmdev.learn_english.Levels_Start.Level22_Start;
import com.palmdev.learn_english.Levels_Start.Level23_Start;
import com.palmdev.learn_english.Levels_Start.Level24_Start;
import com.palmdev.learn_english.Levels_Start.Level25_Start;
import com.palmdev.learn_english.Levels_Start.Level26_Start;
import com.palmdev.learn_english.Levels_Start.Level27_Start;
import com.palmdev.learn_english.Levels_Start.Level28_Start;
import com.palmdev.learn_english.Levels_Start.Level29_Start;
import com.palmdev.learn_english.Levels_Start.Level2_Start;
import com.palmdev.learn_english.Levels_Start.Level30_Start;
import com.palmdev.learn_english.Levels_Start.Level3_Start;
import com.palmdev.learn_english.Levels_Start.Level4_Start;
import com.palmdev.learn_english.Levels_Start.Level5_Start;
import com.palmdev.learn_english.Levels_Start.Level6_Start;
import com.palmdev.learn_english.Levels_Start.Level7_Start;
import com.palmdev.learn_english.Levels_Start.Level8_Start;
import com.palmdev.learn_english.Levels_Start.Level9_Start;

import java.util.Locale;

public class SelectDayActivity extends AppCompatActivity {

    final int[] days = {R.id.day1,R.id.day2,R.id.day3,R.id.day4,R.id.day5,R.id.day6,R.id.day7,
            R.id.day8,R.id.day9,R.id.day10,R.id.day11,R.id.day12,R.id.day13,R.id.day14,R.id.day15,
            R.id.day16,R.id.day17,R.id.day18,R.id.day19,R.id.day20,R.id.day21,R.id.day22,R.id.day23,
            R.id.day24,R.id.day25,R.id.day26,R.id.day27,R.id.day28,R.id.day29,R.id.day30,R.id.end,
    };
    private Toast error;
    private int completedLevels;

    @Override
    protected void attachBaseContext(Context base) {
        String locale = Locale.getDefault().getLanguage();
        super.attachBaseContext(LocaleHelper.onAttach(base, locale));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_day);

        getActualDay();

        // my button back
        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(SelectDayActivity.this,MainActivity.class);
                startActivity(intent);finish();
            }catch (Exception e){
                // -
            }
        });
        
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            Intent intent = new Intent(SelectDayActivity.this,MainActivity.class);
            startActivity(intent);finish();
        }catch (Exception e){
            // -
        }
    }
    public void getActualDay(){
        SharedPreferences save = getSharedPreferences("Save",MODE_PRIVATE);
        completedLevels = save.getInt("completedLevels",0);

        for (int i = 0; i < 30; i++){
            TextView tv = findViewById(days[i]);
            tv.setBackgroundResource(R.drawable.style_btn_select_not_passed);
        }
        for (int i = 0; i < completedLevels; i++){
            TextView tv = findViewById(days[i]);
            tv.setBackgroundResource(R.drawable.style_btn_select_passed);
        }
        TextView actualTV = findViewById(days[completedLevels]);
        actualTV.setBackgroundResource(R.drawable.style_btn_select_actual);
    }

    public void selectDay1(View view) {
        try {
            Intent intent = new Intent(SelectDayActivity.this, Level1_Start.class);
            startActivity(intent);
            finish();
        }catch (Exception e){
            // -
        }
    }
    public void selectDay2(View view) {
        if (completedLevels < 1){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level2_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }
    public void selectDay3(View view) {
        if (completedLevels < 2){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level3_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }
    public void selectDay4(View view) {
        if (completedLevels < 3){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level4_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }
    public void selectDay5(View view) {
        if (completedLevels < 4){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level5_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }
    public void selectDay6(View view) {
        if (completedLevels < 5){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level6_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }
    public void selectDay7(View view) {
        if (completedLevels < 6){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level7_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }
    public void selectDay8(View view) {
        if (completedLevels < 7){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level8_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }
    public void selectDay9(View view) {
        if (completedLevels < 8){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level9_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }
    public void selectDay10(View view) {
        if (completedLevels < 9){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level10_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }
    public void selectDay11(View view) {
        if (completedLevels < 10){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level11_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }
    public void selectDay12(View view) {
        if (completedLevels < 11){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level12_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }
    public void selectDay13(View view) {
        if (completedLevels < 12){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level13_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }
    public void selectDay14(View view) {
        if (completedLevels < 13){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level14_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }
    public void selectDay15(View view) {
        if (completedLevels < 14){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level15_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }
    public void selectDay16(View view) {
        if (completedLevels < 15){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level16_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }
    public void selectDay17(View view) {
        if (completedLevels < 16){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level17_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }
    public void selectDay18(View view) {
        if (completedLevels < 17){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level18_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }
    public void selectDay19(View view) {
        if (completedLevels < 18){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level19_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }
    public void selectDay20(View view) {
        if (completedLevels < 19){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level20_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }public void selectDay21(View view) {
        if (completedLevels < 20){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level21_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }public void selectDay22(View view) {
        if (completedLevels < 21){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level22_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }public void selectDay23(View view) {
        if (completedLevels < 22){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level23_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }public void selectDay24(View view) {
        if (completedLevels < 23){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level24_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }public void selectDay25(View view) {
        if (completedLevels < 24){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level25_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }public void selectDay26(View view) {
        if (completedLevels < 25){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level26_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }public void selectDay27(View view) {
        if (completedLevels < 26){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level27_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }public void selectDay28(View view) {
        if (completedLevels < 27){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level28_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }public void selectDay29(View view) {
        if (completedLevels < 28){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level29_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }public void selectDay30(View view) {
        if (completedLevels < 29){
            error = Toast.makeText(getBaseContext(),R.string.toastSelectDay,Toast.LENGTH_LONG);
            error.show();
        }else {
            try {
                Intent intent = new Intent(SelectDayActivity.this, Level30_Start.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // -
            }
        }
    }

    public void selectDayEnd(View view) {
        Toast selectEnd = Toast.makeText(getBaseContext(), R.string.selectDayEnd, Toast.LENGTH_LONG);
        selectEnd.show();
    }
}
