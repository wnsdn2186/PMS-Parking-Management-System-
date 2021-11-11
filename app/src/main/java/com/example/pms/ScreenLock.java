package com.example.pms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScreenLock extends AppCompatActivity {
    private EditText first, second, third, fourth;
    private int count = 1;
    private String pin;
    private int REQ_EXIT = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_lock);

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
                pin = first.getText().toString() + second.getText().toString() + third.getText().toString() + fourth.getText().toString();
                Intent intent = new Intent(ScreenLock.this, ScreenLockCheck.class);
                intent.putExtra("PIN", pin);
                startActivityForResult(intent, REQ_EXIT);
                overridePendingTransition(R.anim.horizon_enter, R.anim.none);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_EXIT){
            if(resultCode == RESULT_OK){
                this.finish();
            }
        }
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        finish();
    }
}
