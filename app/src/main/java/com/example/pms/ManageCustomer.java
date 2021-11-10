package com.example.pms;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ManageCustomer extends AppCompatActivity {
    private ImageView search_btn;
    private FloatingActionButton add_btn;
    private EditText et;
    private TextView tv, rs;
    private static String IP_ADDRESS = "13.59.85.177";
    private ArrayList<Customer> cust;
    private ArrayList<Customer> copiedList;

    private String mJsonString;
    private CustomerAdapter cAdapter;
    private RecyclerView rc;
    private int customerCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_customer);

        ImageButton backbtn = findViewById(R.id.BackBtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.none, R.anim.horizon_exit);
            }
        });

        et = (EditText) findViewById(R.id.search_box);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = et.getText().toString();
                search(text);
            }
        });

        rc = (RecyclerView) findViewById(R.id.listView_main_list);
        rc.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        rc.setLayoutManager(new LinearLayoutManager(this));

        cust = new ArrayList<>();
        copiedList = new ArrayList<>();
        cAdapter = new CustomerAdapter(this, cust);
        cAdapter.notifyDataSetChanged();
        rc.setAdapter(cAdapter);

        cust.clear();

        GetData task = new GetData();
        task.execute("http://" + IP_ADDRESS + "/list.php", "");

        search_btn = (ImageView) findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "검색", Toast.LENGTH_SHORT).show();
            }
        });

        add_btn = (FloatingActionButton) findViewById(R.id.addCustomer);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
                overridePendingTransition(R.anim.horizon_enter, R.anim.none);
            }
        });

        cAdapter.setOnDeleteClickListener(new CustomerAdapter.OnDeleteClickListener() {
            @Override
            public void onDelete(int idx) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ManageCustomer.this);
                builder.setTitle("삭제");
                builder.setMessage("해당 항목을 삭제하시겠습니까?");
                builder.setPositiveButton("예",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ManageCustomer.JsonParse jsonParse = new ManageCustomer.JsonParse();
                            jsonParse.execute("http://" + IP_ADDRESS + "/delete.php", Integer.toString(idx));
                            Log.i("Complete MSG: ", Integer.toString(idx));
                            cAdapter.notifyDataSetChanged();
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    });
                builder.setNegativeButton("아니오",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                builder.show();
            }
        });
    }

    private void search(String text) {
        cust.clear();

        if (text.length() == 0) {
            cust.addAll(copiedList);
        } else {
            for(int i = 0; i < copiedList.size(); i++) {
                if(copiedList.get(i).getCnum().contains(text)) {
                    cust.add(copiedList.get(i));
                }
            }
        }
        cAdapter.notifyDataSetChanged();
    }

    private class GetData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ManageCustomer.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            Log.d("response", "response - " + result);

            if (result == null){
                rs.setText(errorString);
            }
            else {
                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            String postParameters = params[1];

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("response", "response code - " + responseStatusCode);

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
                return sb.toString().trim();
            } catch (Exception e) {
                Log.d("error", "GetData : Error ", e);
                errorString = e.toString();
                return null;
            }

        }
    }

    private void showResult(){

        String TAG_JSON="cusList";
        String TAG_IDX = "idx";
        String TAG_NAME = "stcustname1";
        String TAG_PNUM = "sttelno1";
        String TAG_CNUM = "stplate1";
        String TAB_RDATE = "regdatetime";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                int id = item.getInt(TAG_IDX);
                String name = item.getString(TAG_NAME);
                String pnum = item.getString(TAG_PNUM);
                String cnum = item.getString(TAG_CNUM);
                String rdate = item.getString(TAB_RDATE);

                Customer cus = new Customer();

                cus.setId(id);
                cus.setName(name);
                cus.setPnum(pnum);
                cus.setCnum(cnum);
                cus.setRdate(rdate);

                cust.add(cus);
                copiedList.add(cus);
                cAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            Log.d("result", "showResult : ", e);
        }

        String title = "고객 관리 (";
        customerCount = cAdapter.getItemCount();
        Log.d("count", String.valueOf(customerCount));
        title += String.valueOf(customerCount) + ")";

        tv = (TextView)findViewById(R.id.customerManage_title);
        tv.setText(title);

    }

    public class JsonParse extends AsyncTask<String,Void,String> {
        ProgressDialog progressDialog;
        String TAG = "JsonParseTest";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(progressDialog != null) {
                progressDialog.dismiss();
            }
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {
            String id = (String)params[1];

            String serverURL = (String)params[0];
            String postParameters = "id=" + id;
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
                Log.d(TAG, "DELETE/ POST response code - " + responseStatusCode);

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
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();
                return sb.toString();
            } catch (Exception e) {
                Log.d(TAG, "DeleteData: Error ", e);
                return new String("Error: " + e.getMessage());
            }

        }
    }
}
