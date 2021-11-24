package com.example.pms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {
    private EditText id, password;
    private Button login, register;
    private CheckBox checkBox;
    private String jsonString, userID, userPassword;
    private static String IP_ADDRESS = "13.59.85.177";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        PrefsHelper.init(getApplicationContext());

        id = (EditText)findViewById(R.id.userID);
        password = (EditText)findViewById(R.id.userPassword);
        login = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_register);
        checkBox = findViewById(R.id.checkBox);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userID = id.getText().toString();
                userPassword = password.getText().toString();

                Login.JsonParse jsonParse = new Login.JsonParse();
                jsonParse.execute("http://" + IP_ADDRESS + "/user_login.php", userID, userPassword);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, UserRegister.class);
                startActivity(intent);
                overridePendingTransition(R.anim.horizon_enter, R.anim.none);
            }
        });

        if(checkBox.isChecked()){
            PrefsHelper.write("userID", userID);
            PrefsHelper.write("userPwd", userPassword);
            PrefsHelper.write("AutoLogin", "on");
        }else{
            PrefsHelper.write("userID", "null");
            PrefsHelper.write("userPwd", "null");
            PrefsHelper.write("AutoLogin", "off");
        }

        checkBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                PrefsHelper.write("userID", userID);
                PrefsHelper.write("userPwd", userPassword);
                PrefsHelper.write("AutoLogin", "on");
            }
        });

    }

    public class JsonParse extends AsyncTask<String, Void, String> {
        String TAG = "JsonParseTest";
        @Override
        protected String doInBackground(String... strings) {    // execute의 매개변수를 받아와서 사용
            String url = strings[0];
            String userID = strings[1];
            String userPassword = strings[2];

            String selectData = "userID=" + userID + "&userPassword=" + userPassword;

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

                return sb.toString().trim();
            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                String errorString = e.toString();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) { // doInBackgroundString에서 return한 값을 받음
            super.onPostExecute(result);

            jsonString = result;
            checkLogin();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


        private void checkLogin() {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                boolean success = jsonObject.getBoolean("success");
                Log.d("success", String.valueOf(success));

                if(success) {
                    /*Admin admin = new Admin();
                    String userID = jsonObject.getString("userID");
                    String userPassword = jsonObject.getString("userPassword");
                    String userName = jsonObject.getString("userName");
                    String userBirth = jsonObject.getString("userBirth");
                    String userPhone = jsonObject.getString("userPhone");

                    Log.d("data1", userID);
                    Log.d("data2", userPassword);
                    Log.d("data3", userName);
                    Log.d("data4", userBirth);
                    Log.d("data5", userPhone);

                    admin.setId(userID);
                    admin.setPassword(userPassword);
                    admin.setName(userName);
                    admin.setBirth(userBirth);
                    admin.setPassword(userPhone);*/

                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "등록되지 않은 회원입니다. 아이디 또는 비밀번호를 확인해주세요", Toast.LENGTH_LONG).show();
                    id.setText(null);
                    password.setText(null);
                    return;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}