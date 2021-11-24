package com.example.pms;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class Statistics  extends AppCompatActivity {
    private Toolbar toolbar;
    private static String IP_ADDRESS = "13.59.85.177";
    private String mJsonString;
    ArrayList<Integer> dayjsonList = new ArrayList<>(); // 출입차량 수
    ArrayList<String> daylabelList = new ArrayList<>(); // 시간
    ArrayList<Integer> timejsonList = new ArrayList<>(); // 출입차량 수
    ArrayList<String> timelabelList = new ArrayList<>(); // 시간
    BarChart daychart, timechart;
    XAxis xAxisday, xAxistime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        ImageButton backbtn = (ImageButton) findViewById(R.id.BackBtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.none, R.anim.horizon_exit);
            }
        });

        GetData task = new GetData();
        task.execute("http://" + IP_ADDRESS + "/stat.php", "");

        daychart = (BarChart)findViewById(R.id.daybarchart);
        timechart = (BarChart)findViewById(R.id.timebarchart);

        xAxisday = daychart.getXAxis();
        xAxistime = timechart.getXAxis();

        GraphInitSetting(); // 그래프 세팅

        DayBarChartGraph(daylabelList, dayjsonList);
        TimeBarChartGraph(timelabelList, timejsonList);
    }

    public void GraphInitSetting(){

        daylabelList.add("일");daylabelList.add("월");daylabelList.add("화");daylabelList.add("수");daylabelList.add("목");daylabelList.add("금");daylabelList.add("토"); // 요일

        timelabelList.add("00시");timelabelList.add("01시");timelabelList.add("02시");timelabelList.add("03시");timelabelList.add("04시");timelabelList.add("05시");
        timelabelList.add("06시");timelabelList.add("07시");timelabelList.add("08시");timelabelList.add("09시");timelabelList.add("10시");timelabelList.add("11시");
        timelabelList.add("12시");timelabelList.add("13시");timelabelList.add("14시");timelabelList.add("15시");timelabelList.add("16시");timelabelList.add("17시");
        timelabelList.add("18시");timelabelList.add("19시");timelabelList.add("20시");timelabelList.add("21시");timelabelList.add("22시");timelabelList.add("23시"); // 시간

        // 임시값
        dayjsonList.add(110);
        dayjsonList.add(550);
        dayjsonList.add(260);
        dayjsonList.add(340);
        dayjsonList.add(420);
        dayjsonList.add(480);
        dayjsonList.add(80);

        // 임시값
        timejsonList.add(10);timejsonList.add(10);timejsonList.add(50);timejsonList.add(30);timejsonList.add(20);timejsonList.add(44);
        timejsonList.add(94);timejsonList.add(77);timejsonList.add(75);timejsonList.add(38);timejsonList.add(25);timejsonList.add(40);
        timejsonList.add(2);timejsonList.add(63);timejsonList.add(59);timejsonList.add(32);timejsonList.add(63);timejsonList.add(80);
        timejsonList.add(88);timejsonList.add(6);timejsonList.add(10);timejsonList.add(12);timejsonList.add(17);timejsonList.add(22);

        DayBarChartGraph(daylabelList, dayjsonList);
        TimeBarChartGraph(timelabelList, timejsonList);
        daychart.setTouchEnabled(false); // 터치불가 --> 추후 변경

        daychart.getAxisRight().setAxisMaxValue(700); // 최대값(add의 최대값 + 20정도로 하기)
        daychart.getAxisLeft().setAxisMaxValue(700);
        daychart.getAxisRight().setAxisMinValue(0); // 최대값(add의 최대값 + 20정도로 하기)
        daychart.getAxisLeft().setAxisMinValue(0);

        timechart.getAxisRight().setAxisMaxValue(100); // 최대값(add의 최대값 + 20정도로 하기)
        timechart.getAxisLeft().setAxisMaxValue(100);
        timechart.getAxisRight().setAxisMinValue(0); // 최대값(add의 최대값 + 20정도로 하기)
        timechart.getAxisLeft().setAxisMinValue(0);
    }

    private void DayBarChartGraph(ArrayList<String> labelList, ArrayList<Integer> valList) {

        ArrayList<BarEntry> entries = new ArrayList<>(); // 출입차량 넣기
        for (int i = 0; i < valList.size(); i++) {
            entries.add(new BarEntry((Integer) valList.get(i), i));
        }

        BarDataSet depenses = new BarDataSet(entries, "일간 출입차량"); // 이름
        depenses.setAxisDependency(YAxis.AxisDependency.LEFT);
        daychart.setDescription(" ");

        ArrayList<String> labels = new ArrayList<String>(); //요일 한글표시
        for (int i = 0; i < labelList.size(); i++) {
            labels.add((String) labelList.get(i));
        }

        //그래프 디자인
        depenses.setColors(Collections.singletonList(Color.rgb(0, 102, 255))); // 컬러 팔레트
        daychart.setVisibleXRangeMaximum(7f); // 갯수표시
        xAxisday.setPosition(XAxis.XAxisPosition.BOTTOM); // 요일 표시 하단
        xAxisday.setDrawGridLines(false); // 세로선 삭제
        //barChart.getAxisLeft().setEnabled(false); // 왼쪽 수치 표시x

        BarData data = new BarData(labels, depenses);
        data.setValueFormatter(new MyValueFormatter());

        daychart.setData(data);
        daychart.animateXY(1000, 1000);
        daychart.invalidate();
    }

    private void TimeBarChartGraph(ArrayList<String> labelList, ArrayList<Integer> valList) {

        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < valList.size(); i++) {
            entries.add(new BarEntry((Integer) valList.get(i), i));
        }

        BarDataSet depenses = new BarDataSet(entries, "시간별 출입차량");
        depenses.setAxisDependency(YAxis.AxisDependency.LEFT);
        timechart.setDescription(" ");

        ArrayList<String> labels = new ArrayList<String>();
        for (int i = 0; i < labelList.size(); i++) {
            labels.add((String) labelList.get(i));
        }

        //그래프 디자인
        depenses.setColors(Collections.singletonList(Color.rgb(77, 148, 255)));
        timechart.setVisibleXRangeMaximum(24f);
        xAxistime.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxistime.setDrawGridLines(false);
        //timechart.getAxisLeft().setEnabled(false);

        BarData data = new BarData(labels, depenses);
        data.setValueFormatter(new MyValueFormatter());

        timechart.setData(data);
        timechart.animateXY(1000, 1000);
        timechart.invalidate();
    }

    public class MyValueFormatter implements ValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("#"); // use one decimal
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(value);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Statistics.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            Log.d("response", "response - " + result);

            if (result == null){
                //rs.setText(errorString);
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
        String TAG_JSON="statData";
        String TAG_PASSTIME = "passtime";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject item = jsonArray.getJSONObject(i);
                int pt = item.getInt(TAG_PASSTIME);
                Log.i("passtime: ", String.valueOf(pt));
            }
        } catch (JSONException e) {
            Log.d("result", "showResult : ", e);
        }
    }

}

/*
1. 일간, 시간 데이터 가져오기
2. 컬러 변경 - 단색에서 다색으로
3. 일간, 시간 데이터 현재를 제일 우측 표시
4. 현재 기준으로 필요 수 만큼 가져오기
5. 소수점 제거
6. 터치 기능 수정
 + 디자인
*/

