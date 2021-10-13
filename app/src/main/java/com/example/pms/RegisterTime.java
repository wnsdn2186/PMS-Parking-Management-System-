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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterTime extends AppCompatActivity {
    private TextView sdT, stT, edT, etT;
    private EditText sdate, stime1, stime2, edate, etime1, etime2;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_time);

        ImageButton backbtn = (ImageButton) findViewById(R.id.BackBtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn = (Button) findViewById(R.id.nextBtn2);

        sdate = (EditText) findViewById(R.id.sdateField);
        sdT = (TextView) findViewById(R.id.sdateTv);

        sdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    sdT.setText("날짜를 입력해 주세요(YYYYMMDD 형식)");
                    sdT.setTextColor(Color.parseColor("#64AFE1"));
                } else {
                    sdT.setText("날짜");
                    sdT.setTextColor(Color.parseColor("#C0C0C0"));
                }
            }
        });

        stime1 = (EditText) findViewById(R.id.stimeField1);
        stT = (TextView) findViewById(R.id.stimeTv);

        stime1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    stT.setText("시간을 입력해 주세요(24H 형식)");
                    stT.setTextColor(Color.parseColor("#64AFE1"));
                } else {
                    stT.setText("시간");
                    stT.setTextColor(Color.parseColor("#C0C0C0"));
                }
            }
        });

        stime2 = (EditText) findViewById(R.id.stimeField2);
        stT = (TextView) findViewById(R.id.stimeTv);

        stime2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    stT.setText("시간을 입력해 주세요(24H 형식)");
                    stT.setTextColor(Color.parseColor("#64AFE1"));
                } else {
                    stT.setText("시간");
                    stT.setTextColor(Color.parseColor("#C0C0C0"));
                }
            }
        });

        edate = (EditText) findViewById(R.id.edateField);
        edT = (TextView) findViewById(R.id.edateTv);

        edate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    edT.setText("날짜를 입력해 주세요(YYYYMMDD 형식)");
                    edT.setTextColor(Color.parseColor("#64AFE1"));
                } else {
                    edT.setText("날짜");
                    edT.setTextColor(Color.parseColor("#C0C0C0"));
                }
            }
        });

        etime1 = (EditText) findViewById(R.id.etimeField1);
        etT = (TextView) findViewById(R.id.etimeTv);

        etime1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    etT.setText("시간을 입력해 주세요(24H 형식)");
                    etT.setTextColor(Color.parseColor("#64AFE1"));
                } else {
                    etT.setText("시간");
                    etT.setTextColor(Color.parseColor("#C0C0C0"));
                }
            }
        });

        etime2 = (EditText) findViewById(R.id.etimeField2);
        etT = (TextView) findViewById(R.id.etimeTv);

        etime2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    etT.setText("시간을 입력해 주세요(24H 형식)");
                    etT.setTextColor(Color.parseColor("#64AFE1"));
                } else {
                    etT.setText("시간");
                    etT.setTextColor(Color.parseColor("#C0C0C0"));
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sdate.hasFocus()) {
                    if (sdate.length() == 0 ) {
                        Toast.makeText(getApplicationContext(), "날짜를 입력하세요!", Toast.LENGTH_LONG).show();
                        sdate.requestFocus();
                        return;
                    } else {
                        stime1.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }
                } else if (stime1.hasFocus()) {
                    if (stime1.length() == 0 ) {
                        Toast.makeText(getApplicationContext(), "시간을 입력하세요!", Toast.LENGTH_LONG).show();
                        stime1.requestFocus();
                        return;
                    } else {
                        stime2.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }
                } else if (stime2.hasFocus()) {
                    if (stime2.length() == 0 ) {
                        Toast.makeText(getApplicationContext(), "시간을 입력하세요!", Toast.LENGTH_LONG).show();
                        stime2.requestFocus();
                        return;
                    } else {
                        edate.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }
                } else if (edate.hasFocus()) {
                    if (edate.length() == 0 ) {
                        Toast.makeText(getApplicationContext(), "날짜를 입력하세요!", Toast.LENGTH_LONG).show();
                        edate.requestFocus();
                        return;
                    } else {
                        etime1.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }
                } else if (etime1.hasFocus()) {
                    if (etime1.length() == 0 ) {
                        Toast.makeText(getApplicationContext(), "시간을 입력하세요!", Toast.LENGTH_LONG).show();
                        etime1.requestFocus();
                        return;
                    } else {
                        etime2.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }
                } else if (etime2.hasFocus()) {
                    if (etime2.length() == 0 ) {
                        Toast.makeText(getApplicationContext(), "시간을 입력하세요!", Toast.LENGTH_LONG).show();
                        etime2.requestFocus();
                        return;
                    } else {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(etime2.getWindowToken(), 0);
                        etime2.clearFocus();
                        btn.setText("다음 단계");
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (sdate.length() == 0 ) {
                                    Toast.makeText(getApplicationContext(), "날짜를 입력하세요!", Toast.LENGTH_LONG).show();
                                    sdate.requestFocus();
                                } else if (stime1.length() == 0 ) {
                                    Toast.makeText(getApplicationContext(), "시간을 입력하세요!", Toast.LENGTH_LONG).show();
                                    stime1.requestFocus();
                                } else if (stime2.length() == 0 ) {
                                    Toast.makeText(getApplicationContext(), "시간을 입력하세요!", Toast.LENGTH_LONG).show();
                                    stime2.requestFocus();
                                } else if (edate.length() == 0 ) {
                                    Toast.makeText(getApplicationContext(), "날짜를 입력하세요!", Toast.LENGTH_LONG).show();
                                    edate.requestFocus();
                                } else if (etime1.length() == 0 ) {
                                    Toast.makeText(getApplicationContext(), "시간을 입력하세요!", Toast.LENGTH_LONG).show();
                                    etime1.requestFocus();
                                } else if (etime2.length() == 0 ) {
                                    Toast.makeText(getApplicationContext(), "시간을 입력하세요!", Toast.LENGTH_LONG).show();
                                    etime2.requestFocus();
                                } else {
                                    String cname = getIntent().getStringExtra("name");
                                    String pnum = getIntent().getStringExtra("phone");
                                    String cnum = getIntent().getStringExtra("car");
                                    String start_date = sdate.getText().toString();
                                    String start_time1 = stime1.getText().toString();
                                    String start_time2 = stime2.getText().toString();
                                    String end_date = edate.getText().toString();
                                    String end_time1 = etime1.getText().toString();
                                    String end_time2 = etime2.getText().toString();

                                    Intent it = new Intent(RegisterTime.this, RegisterSubmit.class);
                                    it.putExtra("name", cname);
                                    it.putExtra("phone", pnum);
                                    it.putExtra("car", cnum);
                                    it.putExtra("sdate", start_date);
                                    it.putExtra("stime1", start_time1);
                                    it.putExtra("stime2", start_time2);
                                    it.putExtra("edate", end_date);
                                    it.putExtra("etime1", end_time1);
                                    it.putExtra("etime2", end_time2);

                                    startActivity(it);
                                    overridePendingTransition(R.anim.horizon_enter, R.anim.none);
                                }
                            }
                        });

                    }
                }
            }
        });




    }
}
