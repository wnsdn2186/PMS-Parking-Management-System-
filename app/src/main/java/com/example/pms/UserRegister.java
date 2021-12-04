package com.example.pms;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegister extends AppCompatActivity {
    private TextView idT, passwordT, nameT, birthT, phoneT, dateT;
    private EditText id, password, name, birth, phone, date;
    private Button register, dupCheck;
    private String uid, upw, uname, ubirth, uphone, jsonString, checkID;
    private static final String IP_ADDRESS = "58.151.43.91";
    private static String temp;
    private boolean dup = false;
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        ImageButton backbtn = (ImageButton) findViewById(R.id.BackBtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.none, R.anim.horizon_exit);
            }
        });

        idT = (TextView) findViewById(R.id.idTv);
        passwordT = (TextView) findViewById(R.id.pwTv);
        nameT = (TextView) findViewById(R.id.nameTv);
        birthT = (TextView) findViewById(R.id.birthTv);
        phoneT = (TextView) findViewById(R.id.phoneTv);
        dateT = (TextView) findViewById(R.id.dateTv);

        id = (EditText) findViewById(R.id.idField);
        password = (EditText) findViewById(R.id.pwField);
        name = (EditText) findViewById(R.id.nameField);
        birth = (EditText) findViewById(R.id.birthField);
        phone = (EditText) findViewById(R.id.phoneField);
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        date = (EditText) findViewById(R.id.dateField);
        dupCheck = (Button)findViewById(R.id.dupCheck);

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

        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dup = false;
            }

            @Override
            public void afterTextChanged(Editable s) { }
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

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dupCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkID  = id.getText().toString();

                UserRegister.Check check = new UserRegister.Check();
                check.execute("http://" + IP_ADDRESS + "/user_dupcheck.php", checkID);
            }
        });

        //+가입일
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        date.setText(mFormat.format(mDate));

        register = (Button) findViewById(R.id.user_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pattern p = Pattern.compile("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$");
                Matcher m = p.matcher((id).getText().toString());
                if (id.length() == 0) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력하세요!", Toast.LENGTH_LONG).show();
                    id.requestFocus();
                } else if ( !m.matches()) {
                    Toast.makeText(UserRegister.this, "Email형식으로 입력하세요!", Toast.LENGTH_SHORT).show();
                    id.requestFocus();
                } else if (dup == false) {
                    Toast.makeText(UserRegister.this, "아이디 중복 확인을 해주세요!", Toast.LENGTH_SHORT).show();
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


                    UserRegister.JsonParse jsonParse = new UserRegister.JsonParse();
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
            String url = (String) strings[0];
            String uid = (String) strings[1];
            String upw = (String) strings[2];
            String uname = (String) strings[3];
            String ubirth = (String) strings[4];
            String uphone = (String) strings[5];

            String selectData = "uid=" + uid + "&upw=" + upw + "&uname=" + uname + "&ubirth=" + ubirth + "&uphone=" + uphone;

            try {
                URL serverURL = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) serverURL.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(selectData.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
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
        protected void onPostExecute(String fromdoInBackgroundString) {
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

        private String getTime() {
            mNow = System.currentTimeMillis();
            mDate = new Date(mNow);
            return mFormat.format(mDate);
        }
    }

    public class Check extends AsyncTask<String, Void, String> {
        String TAG = "JsonParseTest2";

        @Override
        protected String doInBackground(String... strings) {
            String url = (String) strings[0];
            String userID = (String) strings[1];

            String selectData = "userID=" + userID;

            try {
                URL serverURL = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) serverURL.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(selectData.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            jsonString = result;
            checkDup();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        private void checkDup() {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("Admin");

                if(jsonArray.length() != 0) {
                    Toast.makeText(getApplicationContext(), "이미 사용중인 아이디 입니다.", Toast.LENGTH_LONG).show();
                    id.setText(null);
                    id.requestFocus();
                } else {
                    Toast.makeText(getApplicationContext(), "사용 가능한 아이디 입니다.", Toast.LENGTH_LONG).show();
                    password.requestFocus();
                    dup = true;
                    return;
                }
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
    }
}