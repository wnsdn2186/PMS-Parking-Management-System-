package com.example.pms;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserRegister extends AppCompatActivity {
    private TextView idT, passwordT, nameT, birthT, phoneT;
    private EditText id, password, name, birth, phone;
    private Button register;
    private String uid, upw, uname, ubirth, uphone;
    private static String IP_ADDRESS = "13.59.85.177";
    private static String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        ImageButton backbtn = (ImageButton) findViewById(R.id.BackBtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });

        idT = (TextView)findViewById(R.id.idTv);
        passwordT = (TextView)findViewById(R.id.pwTv);
        nameT = (TextView)findViewById(R.id.nameTv);
        birthT = (TextView)findViewById(R.id.birthTv);
        phoneT = (TextView)findViewById(R.id.phoneTv);

        id = (EditText)findViewById(R.id.idField);
        password = (EditText)findViewById(R.id.pwField);
        name = (EditText)findViewById(R.id.nameField);
        birth = (EditText)findViewById(R.id.birthField);
        phone = (EditText)findViewById(R.id.phoneField);

        id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    idT.setTextColor(Color.parseColor("#64AFE1"));
                } else {
                    idT.setTextColor(Color.parseColor("#C0C0C0"));
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    passwordT.setTextColor(Color.parseColor("#64AFE1"));
                } else {
                    passwordT.setTextColor(Color.parseColor("#C0C0C0"));
                }
            }
        });

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    nameT.setTextColor(Color.parseColor("#64AFE1"));
                } else {
                    nameT.setTextColor(Color.parseColor("#C0C0C0"));
                }
            }
        });

        birth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    birthT.setTextColor(Color.parseColor("#64AFE1"));
                } else {
                    birthT.setTextColor(Color.parseColor("#C0C0C0"));
                }
            }
        });

        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    phoneT.setTextColor(Color.parseColor("#64AFE1"));
                } else {
                    phoneT.setTextColor(Color.parseColor("#C0C0C0"));
                }
            }
        });

        register = (Button) findViewById(R.id.user_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.length() == 0) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력하세요!", Toast.LENGTH_LONG).show();
                    id.requestFocus();
                } else if (password.length() == 0) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요!", Toast.LENGTH_LONG).show();
                    password.requestFocus();
                } else if (name.length() == 0) {
                    Toast.makeText(getApplicationContext(), "이름을 입력하세요!", Toast.LENGTH_LONG).show();
                    name.requestFocus();
                } else if (birth.length() == 0) {
                    Toast.makeText(getApplicationContext(), "생일을 입력하세요!", Toast.LENGTH_LONG).show();
                    birth.requestFocus();
                } else if (phone.length() == 0) {
                    Toast.makeText(getApplicationContext(), "휴대폰 번호을 입력하세요!", Toast.LENGTH_LONG).show();
                    phone.requestFocus();
                } else {
                    uid = id.getText().toString();
                    upw = password.getText().toString();
                    uname = name.getText().toString();
                    ubirth = birth.getText().toString();
                    uphone = phone.getText().toString();


                    UserRegister.JsonParse jsonParse =new UserRegister.JsonParse();
                    jsonParse.execute("http://" + IP_ADDRESS + "/user_register.php", uid, upw, uname, ubirth, uphone);

                    finish();
                }
            }
        });
    }

    public class JsonParse extends AsyncTask<String, Void, String> {
        String TAG = "JsonParseTest";
        @Override
        protected String doInBackground(String... strings) {
            String url = (String)strings[0];
            String uid = (String)strings[1];
            String upw = (String)strings[2];
            String uname = (String)strings[3];
            String ubirth = (String)strings[4];
            String uphone = (String)strings[5];

            String selectData = "uid=" + uid + "&upw=" + upw + "&uname=" + uname + "&ubirth=" + ubirth + "&uphone=" + uphone;

            try {
                URL serverURL = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) serverURL.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(selectData.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();
                Log.d(TAG, sb.toString().trim());

                return sb.toString().trim();        // 받아온 JSON의 공백을 제거
            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                String errorString = e.toString();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String fromdoInBackgroundString) { // doInBackgroundString에서 return한 값을 받음
            super.onPostExecute(fromdoInBackgroundString);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}