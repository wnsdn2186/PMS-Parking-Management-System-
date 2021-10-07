package com.example.pms;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RegisterCar extends AppCompatActivity {
    private String cname, pnum;
    private FloatingActionButton next_btn;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_car);

        cname = getIntent().getStringExtra("name");
        pnum = getIntent().getStringExtra("phone");

        String st = cname + "님의";
        tv = (TextView)findViewById(R.id.tv1);
        tv.setText(st);

        next_btn = (FloatingActionButton)findViewById(R.id.carNext);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText car = (EditText) findViewById(R.id.cnumField);
                String cnum = car.getText().toString();

                if (cnum.length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "차량번호를 입력하세요!", Toast.LENGTH_LONG).show();
                    car.requestFocus();
                } else {
                    Intent it = new Intent(RegisterCar.this, RegisterSubmit.class);
                    it.putExtra("name", cname);
                    it.putExtra("phone", pnum);
                    it.putExtra("car", cnum);
                    startActivity(it);
                    overridePendingTransition(R.anim.horizon_enter, R.anim.none);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent back = new Intent(RegisterCar.this, RegisterPhone.class);
        back.putExtra("name", cname);
        startActivity(back);
        overridePendingTransition(R.anim.horizon_enter, R.anim.none);
        finish();
    }
}
