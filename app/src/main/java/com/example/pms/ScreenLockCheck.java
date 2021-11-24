package com.example.pms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ScreenLockCheck extends AppCompatActivity {
    private EditText first, second, third, fourth;
    private int count = 1;
    private String pre_pin, cur_pin;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_lock);

        Toast.makeText(ScreenLockCheck.this, "한 번 더 입력해주세요", Toast.LENGTH_LONG).show();

        intent = getIntent();
        pre_pin = intent.getStringExtra("PIN");

        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        third = findViewById(R.id.third);
        fourth = findViewById(R.id.fourth);
    }

    public void Check_InputNum(View view) {
        switch (view.getId()) {
            case R.id.one:
                SetPinNum(1);
                break;

            case R.id.two:
                SetPinNum(2);
                break;


            case R.id.three:
                SetPinNum(3);
                break;


            case R.id.four:
                SetPinNum(4);
                break;


            case R.id.five:
                SetPinNum(5);
                break;


            case R.id.six:
                SetPinNum(6);
                break;


            case R.id.seven:
                SetPinNum(7);
                break;

            case R.id.eight:
                SetPinNum(8);
                break;

            case R.id.nine:
                SetPinNum(9);
                break;

            case R.id.zero:
                SetPinNum(0);
                break;

            case R.id.deleteBtn:
                DelPinNum();
                if(count > 1)
                    count--;
                break;
        }
    }

    public void SetPinNum(int num) {
        switch (count) {
            case 1:
                first.setText(String.valueOf(num));
                count++;
                break;

            case 2:
                second.setText(String.valueOf(num));
                count++;
                break;

            case 3:
                third.setText(String.valueOf(num));
                count++;
                break;

            case 4:
                fourth.setText(String.valueOf(num));
                cur_pin = first.getText().toString() + second.getText().toString() + third.getText().toString() + fourth.getText().toString();
                Log.e("핀", pre_pin);
                Log.e("핀", cur_pin);
                if(pre_pin.equals(cur_pin)){
                    Toast.makeText(ScreenLockCheck.this, "앱 잠금 완료", Toast.LENGTH_LONG).show();
                    finish();
                    PrefsHelper.write("PIN", cur_pin);
                }else{
                    Toast.makeText(ScreenLockCheck.this, "다시 입력하세요", Toast.LENGTH_LONG).show();
                    first.setText(null);
                    second.setText(null);
                    third.setText(null);
                    fourth.setText(null);
                    count = 1;
                    cur_pin = null;
                }
                break;

            default:
                break;
        }
    }

    public void DelPinNum(){
        switch(count){
            case 2:
                first.setText(null);
                break;

            case 3:
                second.setText(null);
                break;

            case 4:
                third.setText(null);
                break;
        }
    }
}
