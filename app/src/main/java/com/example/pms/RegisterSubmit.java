package com.example.pms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class RegisterSubmit extends AppCompatActivity {
    private Button cancel, submit;
    private TextView tv1, tv2, tv3;
    private String cname, pnum, cnum;
    private static String IP_ADDRESS = "221.139.167.8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_submit);

        cname = getIntent().getStringExtra("name");
        pnum = getIntent().getStringExtra("phone");
        cnum = getIntent().getStringExtra("car");

        Log.d("HERE: ", cname + " " + pnum + " " + cnum);

        tv1 = (TextView) findViewById(R.id.name);
        tv1.setText(cname);

        tv2 = (TextView) findViewById(R.id.phone);
        tv2.setText(pnum);

        tv3 = (TextView) findViewById(R.id.car);
        tv3.setText(cnum);

        cancel = (Button) findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "등록 취소", Toast.LENGTH_SHORT).show();
            }
        });

        submit = (Button) findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 타임존 한국 지정
                TimeZone tz;

                //날짜 및 시간 형식 지정
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
                tz = TimeZone.getTimeZone("Asia/Seoul");
                simpleDateFormat.setTimeZone(tz);

                //Date 객체 사용
                Date today = new Date();
                String date = simpleDateFormat.format(today);


                RegisterSubmit.JsonParse jsonParse = new RegisterSubmit.JsonParse();
                jsonParse.execute("http://" + IP_ADDRESS + "/register.php", cname, pnum, cnum, date);

                Toast.makeText(getApplicationContext(), "id : " + cname + " 님의 회원가입이 완료 되었습니다.", Toast.LENGTH_LONG).show();

                finish();
            }
        });
    }

    public class JsonParse extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String TAG = "JsonParseTest";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (progressDialog != null)
                progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {
            String name = (String) params[1];
            String pnum = (String) params[2];
            String carNum = (String) params[3];
            String regDate = (String) params[4];

            String serverURL = (String) params[0];
            String postParameters = "name=" + name + "&pnum=" + pnum + "&carNum=" + carNum + "&regDate=" + regDate;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();
                return sb.toString();
            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }

        }
    }

    @Override
    public void onBackPressed() {
        Intent back = new Intent(RegisterSubmit.this, RegisterCar.class);
        back.putExtra("name", cname);
        back.putExtra("phone", pnum);
        startActivity(back);
        overridePendingTransition(R.anim.horizon_enter, R.anim.none);
        finish();
    }
}
