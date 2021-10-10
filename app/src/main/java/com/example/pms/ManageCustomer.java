package com.example.pms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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

    private String mJsonString;
    private CustomerAdapter cAdapter;
    private RecyclerView rc;
    private int customerCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_customer);

        et = (EditText)findViewById(R.id.search_box);
        et.setText("");

        rc = (RecyclerView)findViewById(R.id.listView_main_list);
        rc.setLayoutManager(new LinearLayoutManager(this));

        cust = new ArrayList<>();
        cAdapter = new CustomerAdapter(this, cust);
        rc.setAdapter(cAdapter);

        cust.clear();
        cAdapter.notifyDataSetChanged();

        GetData task = new GetData();
      
        task.execute( "http://" + IP_ADDRESS + "/list.php", "");

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
                finish();
            }
        });

        ImageButton settingbtn = (ImageButton) findViewById(R.id.SettingBtn);
        ImageButton backbtn = (ImageButton) findViewById(R.id.BackBtn);
        ImageButton homebtn = (ImageButton) findViewById(R.id.HomeBtn);

        settingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Setting.class));
                overridePendingTransition(R.anim.horizon_enter, R.anim.none);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.horizon_enter, R.anim.none);
                finish();
            }
        });
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

                String id = item.getString(TAG_IDX);
                String name = item.getString(TAG_NAME);
                String pnum = item.getString(TAG_PNUM);
                String cnum = item.getString(TAG_CNUM);
                String rdate = item.getString(TAB_RDATE);

                Customer cus = new Customer();

                cus.setName(name);
                cus.setPnum(pnum);
                cus.setCnum(cnum);
                cus.setRdate(rdate);

                cust.add(cus);
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
}
