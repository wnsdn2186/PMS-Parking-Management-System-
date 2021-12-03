package com.example.pms;

import android.app.ProgressDialog;
import android.media.Image;
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
import java.time.LocalDate;
import java.util.ArrayList;

public class CarSearch extends AppCompatActivity {
    private TextView search_box;
    private RecyclerView recyclerView = null;
    private CarSearchAdapter adapter = null;
    private ArrayList<CarSearchItem> mList, FilteredList;
    private int cnt = 1;
    private static final String IP_ADDRESS = "58.151.43.91";
    private String mJsonImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_search);

        LocalDate now = LocalDate.now();

        int y = now.getYear();
        String year = String.valueOf(y);
        year = year.substring(2);

        int m = now.getMonthValue();
        String month = String.valueOf(m);

        String table = year + month;

        CarSearch.GetImage jsonParse = new CarSearch.GetImage();
        //jsonParse.execute("http://" + IP_ADDRESS + "/getImage.php", plateNum, table);
        jsonParse.execute("http://" + IP_ADDRESS + "/getImage.php", table);

        ImageButton backbtn = (ImageButton) findViewById(R.id.BackBtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.none, R.anim.horizon_exit);
            }
        });

        FilteredList = new ArrayList<>();
        search_box = findViewById(R.id.search_box);
        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String CarNum = search_box.getText().toString();
                //searchFilter(CarNum);
            }
        });

        ImageView searchbtn = (ImageView) findViewById(R.id.car_search_btn);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String plateNum = search_box.getText().toString();
            }
        });
    }

    private void addItem(Integer Cnt, String Date, String ImgURL, String CarNum, String InTime, String OutTime) {
        CarSearchItem items = new CarSearchItem();

        items.setCnt(Cnt);
        items.setDate(Date);
        items.setImgURL(ImgURL);
        items.setCarNum(CarNum);
        items.setInTime(InTime);
        items.setOutTime(OutTime);

        mList.add(items);
    }

    private void searchFilter(String SearchText) {
        FilteredList.clear();

        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getCarNum().toLowerCase().contains(SearchText.toLowerCase())) {
                FilteredList.add(mList.get(i));
            }
        }

        adapter.FilterList(FilteredList);
    }

    private void showResult() {
        String TAG_JSON = "imgList";
        //String TAG_IDX = "idx";
        String TAG_PLATE = "plate";
        String TAG_PASSTIME = "passtime";
        String TAG_IMGPATH = "imgpath";
        String TAG_PIMAGPATH = "pimgpath";

        mList = new ArrayList<>();
        adapter = new CarSearchAdapter(mList, this);
        recyclerView = findViewById(R.id.car_search_list);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            JSONObject jsonObject = new JSONObject(mJsonImg);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                //int id = item.getInt(TAG_IDX);
                String plateNum = item.getString(TAG_PLATE);
                String passTime = item.getString(TAG_PASSTIME);
                String imgPath = item.getString(TAG_IMGPATH);
                imgPath = imgPath.substring(19);
                imgPath = imgPath.replaceAll("\\\\", "/");
                Log.i("imgPath", imgPath);
                String pimgPath = item.getString(TAG_PIMAGPATH);
                pimgPath = pimgPath.substring(19);
                pimgPath = pimgPath.replaceAll("\\\\", "/");
                String path = "http://58.151.43.91/" + pimgPath;
                Log.i("Path: ", path);
                addItem(i, passTime, path, plateNum, "17:23", "19:20");
            }
        } catch (JSONException e) {
            Log.d("result", "showResult : ", e);
        }

        adapter.notifyDataSetChanged();
    }

    public class GetImage extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String TAG = "JsonParseTest";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (progressDialog != null) {
                progressDialog.dismiss();
            }

            Log.d("response", "response - " + result);

            if (result == null) {
//                rs.setText(errorString);
            } else {
                mJsonImg = result;
                Log.i("Img Result: ", mJsonImg);
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = (String) params[0];
            //String plate = (String) params[1];
            String table = (String) params[1];

            //String postParameters = "plate=" + plate + "&table=" + table;
            //Log.d("plate: ", plate);
            String postParameters = "table=" + table;
            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes(StandardCharsets.UTF_8));
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

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();
                return sb.toString();
            } catch (Exception e) {
                Log.d(TAG, "Getting Image Error: ", e);
                return "Error: " + e.getMessage();
            }

        }
    }
}
