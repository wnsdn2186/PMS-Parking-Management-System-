package com.example.pms;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RegisterPhone extends AppCompatActivity {
    private FloatingActionButton next_btn;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);

        EditText phone = (EditText) findViewById(R.id.pnumField);
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        String cname = getIntent().getStringExtra("name");
        String st = cname + "님의";
        tv = (TextView) findViewById(R.id.tv1);
        tv.setText(st);

        next_btn = (FloatingActionButton) findViewById(R.id.phoneNext);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pnum = phone.getText().toString();

                if (pnum.length() == 0) {
                    Toast.makeText(getApplicationContext(), "전화번호를 입력하세요!", Toast.LENGTH_LONG).show();
                    phone.requestFocus();
                } else {
                    Intent it = new Intent(RegisterPhone.this, RegisterCar.class);
                    it.putExtra("name", cname);
                    it.putExtra("phone", pnum);
                    startActivity(it);
                    overridePendingTransition(R.anim.horizon_enter, R.anim.none);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), RegisterName.class));
        overridePendingTransition(R.anim.horizon_enter, R.anim.none);
    }
}
