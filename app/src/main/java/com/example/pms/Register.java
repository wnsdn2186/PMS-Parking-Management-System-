package com.example.pms;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    private TextView nT, pT, cT;
    private EditText name, phone, car;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn = (Button)findViewById(R.id.nextBtn);

        name = (EditText) findViewById(R.id.nameField);
        nT = (TextView) findViewById(R.id.nameTv);

        phone = (EditText) findViewById(R.id.pnumField);
        pT = (TextView) findViewById(R.id.phone);

        car = (EditText) findViewById(R.id.cnumField);
        cT = (TextView) findViewById(R.id.car);

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    nT.setText("이름을 입력해 주세요");
                    nT.setTextColor(Color.parseColor("#64AFE1"));
                } else {
                    nT.setText("이름");
                    nT.setTextColor(Color.parseColor("#C0C0C0"));
                }
            }
        });

        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    pT.setText("휴대폰 번호 (-없이)를 입력해 주세요");
                    pT.setTextColor(Color.parseColor("#64AFE1"));
                } else {
                    pT.setText("휴대폰 번호 (-없이)");
                    pT.setTextColor(Color.parseColor("#C0C0C0"));
                }
            }
        });

        car.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    cT.setText("차량 번호를 입력해 주세요");
                    cT.setTextColor(Color.parseColor("#64AFE1"));
                } else {
                    cT.setText("차량 번호");
                    cT.setTextColor(Color.parseColor("#C0C0C0"));
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.hasFocus()) {
                    if (name.length() == 0 ) {
                        Toast.makeText(getApplicationContext(), "이름을 입력하세요!", Toast.LENGTH_LONG).show();
                        name.requestFocus();
                        return;
                    } else {
                        pT.setVisibility(View.VISIBLE);
                        phone.setVisibility(View.VISIBLE);
                        phone.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }
                } else if (phone.hasFocus()) {
                    if (phone.length() == 0 ) {
                        Toast.makeText(getApplicationContext(), "휴대폰 번호를 입력하세요!", Toast.LENGTH_LONG).show();
                        phone.requestFocus();
                        return;
                    } else {
                        cT.setVisibility(View.VISIBLE);
                        car.setVisibility(View.VISIBLE);
                        car.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }
                } else if (car.hasFocus()) {
                    if (car.length() == 0 ) {
                        Toast.makeText(getApplicationContext(), "차량 번호를 입력하세요!", Toast.LENGTH_LONG).show();
                        car.requestFocus();
                        return;
                    } else {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(car.getWindowToken(), 0);
                        car.clearFocus();
                        btn.setText("다음 단계");
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String cnum = car.getText().toString();
                                cnum = cnum.replaceAll(" ","");

                                Intent it = new Intent(Register.this, RegisterSubmit.class);
                                it.putExtra("name", name.getText().toString());
                                it.putExtra("phone", phone.getText().toString());
                                it.putExtra("car", cnum);
                                startActivity(it);
                                overridePendingTransition(R.anim.horizon_enter, R.anim.none);
                                finish();
                            }
                        });
                    }
                }
            }
        });

        name.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        name.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == keyEvent.KEYCODE_ENTER)
                    return true;
                return false;
            }
        });

        phone.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == keyEvent.KEYCODE_ENTER)
                    return true;
                return false;
            }
        });
    }
}
