package com.example.pms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ScreenLockCheck extends AppCompatActivity {
    private EditText check_first, check_second, check_third, check_fourth;
    private int count = 1;
    private String pre_pin, cur_pin;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_lock_check);

        Toast.makeText(ScreenLockCheck.this, "한 번 더 입력해주세요", Toast.LENGTH_LONG).show();

        intent = getIntent();
        pre_pin = intent.getStringExtra("PIN");

        check_first = findViewById(R.id.check_first);
        check_second = findViewById(R.id.check_second);
        check_third = findViewById(R.id.check_third);
        check_fourth = findViewById(R.id.check_fourth);
    }

    public void Check_InputNum(View view) {
        switch (view.getId()) {
            case R.id.check_one:
                SetPinNum(1);
                break;

            case R.id.check_two:
                SetPinNum(2);
                break;


            case R.id.check_three:
                SetPinNum(3);
                break;


            case R.id.check_four:
                SetPinNum(4);
                break;


            case R.id.check_five:
                SetPinNum(5);
                break;


            case R.id.check_six:
                SetPinNum(6);
                break;


            case R.id.check_seven:
                SetPinNum(7);
                break;

            case R.id.check_eight:
                SetPinNum(8);
                break;

            case R.id.check_nine:
                SetPinNum(9);
                break;

            case R.id.check_zero:
                SetPinNum(0);
                break;

            case R.id.check_deleteBtn:
                DelPinNum();
                if(count > 1)
                    count--;
                break;
        }
    }

    public void SetPinNum(int num) {
        switch (count) {
            case 1:
                check_first.setText(String.valueOf(num));
                count++;
                break;

            case 2:
                check_second.setText(String.valueOf(num));
                count++;
                break;

            case 3:
                check_third.setText(String.valueOf(num));
                count++;
                break;

            case 4:
                check_fourth.setText(String.valueOf(num));
                cur_pin = check_first.getText().toString() + check_second.getText().toString() + check_third.getText().toString() + check_fourth.getText().toString();
                Log.e("핀", pre_pin);
                Log.e("핀", cur_pin);
                if(pre_pin.equals(cur_pin)){
                    Toast.makeText(ScreenLockCheck.this, "앱 잠금 완료", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(ScreenLockCheck.this, "다시 입력하세요", Toast.LENGTH_LONG).show();
                    check_first.setText(null);
                    check_second.setText(null);
                    check_third.setText(null);
                    check_fourth.setText(null);
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
                check_first.setText(null);
                break;

            case 3:
                check_second.setText(null);
                break;

            case 4:
                check_third.setText(null);
                break;
        }
    }
}
