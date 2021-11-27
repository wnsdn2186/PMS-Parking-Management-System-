package com.example.pms;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
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
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Statistics extends AppCompatActivity {
    private Toolbar toolbar;
    private static String IP_ADDRESS = "13.59.85.177";
    private String mJsonString;
    ArrayList<Integer> dayjsonList = new ArrayList<>(); // 출입차량 수
    ArrayList<String> daylabelList = new ArrayList<>(); // 시간
    ArrayList<Integer> timejsonList = new ArrayList<>(); // 출입차량 수
    ArrayList<String> timelabelList = new ArrayList<>(); // 시간
    BarChart daychart, timechart;
    XAxis xAxisday, xAxistime;
    int today = 0;
    int OneDayCnt = 0, TwoDayCnt = 0, ThreeDayCnt = 0, FourDayCnt = 0, FiveDayCnt = 0, SixDayCnt = 0, TodayCnt = 0, MaxCntDay = 20;

    int midnightCnt = 0, time1 = 0, time2 = 0, time3 = 0, time4 = 0, time5 = 0, time6 = 0, time7 = 0, time8 = 0, time9 = 0, time10 = 0, time11 = 0;
    int noonCnt = 0, time13 = 0, time14 = 0, time15 = 0, time16 = 0, time17 = 0, time18 = 0, time19 = 0, time20 = 0, time21 = 0, time22 = 0, time23 = 0, MaxCntTime = 5;

    private TextView TimeAvgTitle, TimeAvgTxt, DayAvgTitle, DayAvgTxt;
    private TextView ChartTimeTitle, ChartDayTitle;
    String TimeTitle, DayTitle;
    String cTimeTitle, cDayTitle;
    String TimeWord = "시간";
    String DayWord = "일";
    int start, end;
    private double TimeAvg = 0;
    private double DayAvg = 0;

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

        daychart = (BarChart) findViewById(R.id.daybarchart);
        timechart = (BarChart) findViewById(R.id.timebarchart);

        xAxisday = daychart.getXAxis();
        xAxistime = timechart.getXAxis();

        TimeAvgTitle = findViewById(R.id.TimeAvgTitle);
        TimeAvgTxt = findViewById(R.id.TimeAvg);
        DayAvgTitle = findViewById(R.id.DayAvgTitle);
        DayAvgTxt = findViewById(R.id.DayAvg);

        ChartTimeTitle = findViewById(R.id.timetitle);
        ChartDayTitle = findViewById(R.id.daytitle);

        TimeTitle = TimeAvgTitle.getText().toString();
        DayTitle = DayAvgTitle.getText().toString();
        cTimeTitle = ChartTimeTitle.getText().toString();
        cDayTitle = ChartDayTitle.getText().toString();

        SpannableString TimespannableString = new SpannableString(TimeTitle);
        SpannableString DayspannableString = new SpannableString(DayTitle);
        SpannableString cTimespannableString = new SpannableString(cTimeTitle);
        SpannableString cDayspannableString = new SpannableString(cDayTitle);

        TimespannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#64AFE1")), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        TimespannableString.setSpan(new RelativeSizeSpan(1.3f), 0, 3, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        DayspannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#64AFE1")), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        DayspannableString.setSpan(new RelativeSizeSpan(1.3f), 0, 2, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        cTimespannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#64AFE1")), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cTimespannableString.setSpan(new RelativeSizeSpan(1.3f), 0, 3, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        cDayspannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#64AFE1")), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cDayspannableString.setSpan(new RelativeSizeSpan(1.3f), 0, 3, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        TimeAvgTitle.setText(TimespannableString);
        DayAvgTitle.setText(DayspannableString);
        ChartTimeTitle.setText(cTimespannableString);
        ChartDayTitle.setText(cDayspannableString);
        // GraphInitSetting(); // 그래프 세팅
    }

    public void GraphInitSetting() {
        switch (today) {
            case 0:
                daylabelList.add("월");
                daylabelList.add("화");
                daylabelList.add("수");
                daylabelList.add("목");
                daylabelList.add("금");
                daylabelList.add("토");
                daylabelList.add("일");
                break;
            case 1:
                daylabelList.add("화");
                daylabelList.add("수");
                daylabelList.add("목");
                daylabelList.add("금");
                daylabelList.add("토");
                daylabelList.add("일");
                daylabelList.add("월");
                break;
            case 2:
                daylabelList.add("수");
                daylabelList.add("목");
                daylabelList.add("금");
                daylabelList.add("토");
                daylabelList.add("일");
                daylabelList.add("월");
                daylabelList.add("화");
                break;
            case 3:
                daylabelList.add("목");
                daylabelList.add("금");
                daylabelList.add("토");
                daylabelList.add("일");
                daylabelList.add("월");
                daylabelList.add("화");
                daylabelList.add("수");
                break;
            case 4:
                daylabelList.add("금");
                daylabelList.add("토");
                daylabelList.add("일");
                daylabelList.add("월");
                daylabelList.add("화");
                daylabelList.add("수");
                daylabelList.add("목");
                break;
            case 5:
                daylabelList.add("토");
                daylabelList.add("일");
                daylabelList.add("월");
                daylabelList.add("화");
                daylabelList.add("수");
                daylabelList.add("목");
                daylabelList.add("금");
                break;
            case 6:
                daylabelList.add("일");
                daylabelList.add("월");
                daylabelList.add("화");
                daylabelList.add("수");
                daylabelList.add("목");
                daylabelList.add("금");
                daylabelList.add("토");
                break;
        }

        timelabelList.add("00시");
        timelabelList.add("01시");
        timelabelList.add("02시");
        timelabelList.add("03시");
        timelabelList.add("04시");
        timelabelList.add("05시");
        timelabelList.add("06시");
        timelabelList.add("07시");
        timelabelList.add("08시");
        timelabelList.add("09시");
        timelabelList.add("10시");
        timelabelList.add("11시");
        timelabelList.add("12시");
        timelabelList.add("13시");
        timelabelList.add("14시");
        timelabelList.add("15시");
        timelabelList.add("16시");
        timelabelList.add("17시");
        timelabelList.add("18시");
        timelabelList.add("19시");
        timelabelList.add("20시");
        timelabelList.add("21시");
        timelabelList.add("22시");
        timelabelList.add("23시");


        dayjsonList.add(OneDayCnt);
        dayjsonList.add(TwoDayCnt);
        dayjsonList.add(ThreeDayCnt);
        dayjsonList.add(FourDayCnt);
        dayjsonList.add(FiveDayCnt);
        dayjsonList.add(SixDayCnt);
        dayjsonList.add(TodayCnt);

        findMaxCntDay(OneDayCnt, TwoDayCnt, ThreeDayCnt, FourDayCnt, FiveDayCnt, SixDayCnt, TodayCnt);

        timejsonList.add(midnightCnt);
        timejsonList.add(time1);
        timejsonList.add(time2);
        timejsonList.add(time3);
        timejsonList.add(time4);
        timejsonList.add(time5);
        timejsonList.add(time6);
        timejsonList.add(time7);
        timejsonList.add(time8);
        timejsonList.add(time9);
        timejsonList.add(time10);
        timejsonList.add(time11);
        timejsonList.add(noonCnt);
        timejsonList.add(time13);
        timejsonList.add(time14);
        timejsonList.add(time15);
        timejsonList.add(time16);
        timejsonList.add(time17);
        timejsonList.add(time18);
        timejsonList.add(time19);
        timejsonList.add(time20);
        timejsonList.add(time21);
        timejsonList.add(time22);
        timejsonList.add(time23);

        findMaxCntTime(midnightCnt, time1, time2, time3, time4, time5, time6, time7, time8, time9, time10, time11);
        findMaxCntTime(noonCnt, time13, time14, time15, time16, time17, time18, time19, time20, time21, time22, time23);

        DayBarChartGraph(daylabelList, dayjsonList);
        TimeBarChartGraph(timelabelList, timejsonList);

        daychart.getAxisRight().setAxisMaxValue(MaxCntDay);
        daychart.getAxisLeft().setAxisMaxValue(MaxCntDay);
        daychart.getAxisRight().setAxisMinValue(0);
        daychart.getAxisLeft().setAxisMinValue(0);

        timechart.getAxisRight().setAxisMaxValue(MaxCntTime);
        timechart.getAxisLeft().setAxisMaxValue(MaxCntTime);
        timechart.getAxisRight().setAxisMinValue(0);
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
        depenses.setColors(Collections.singletonList(Color.rgb(100, 175, 255))); // 컬러 팔레트
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

        BarDataSet depenses = new BarDataSet(entries, "시간 출입차량");
        depenses.setAxisDependency(YAxis.AxisDependency.LEFT);
        timechart.setDescription(" ");

        ArrayList<String> labels = new ArrayList<String>();
        for (int i = 0; i < labelList.size(); i++) {
            labels.add((String) labelList.get(i));
        }

        //그래프 디자인
        depenses.setColors(Collections.singletonList(Color.rgb(100, 175, 255)));
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

    public class GetData extends AsyncTask<String, Void, String> {
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

            if (result == null) {
                //rs.setText(errorString);
            } else {
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
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
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

    public void showResult() {
        String TAG_JSON = "statData";
        String TAG_PASSTIME = "passtime";
        Long pt = 0L, range = 0L, time = 0L;

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                pt = item.getLong(TAG_PASSTIME);
                Log.i("passtime: ", String.valueOf(pt));

                range = jsonArray.getJSONObject(jsonArray.length() - 1).getLong(TAG_PASSTIME) * 1000L;
                Date RangeDate = new Date(range);
                today = RangeDate.getDay();

                time = pt * 1000L;
                Date ItDate = new Date(time);

                if ((7 + today - ItDate.getDay()) % 7 == 6) {
                    OneDayCnt += 1;
                } else if ((7 + today - ItDate.getDay()) % 7 == 5) {
                    TwoDayCnt += 1;
                } else if ((7 + today - ItDate.getDay()) % 7 == 4) {
                    ThreeDayCnt += 1;
                } else if ((7 + today - ItDate.getDay()) % 7 == 3) {
                    FourDayCnt += 1;
                } else if ((7 + today - ItDate.getDay()) % 7 == 2) {
                    FiveDayCnt += 1;
                } else if ((7 + today - ItDate.getDay()) % 7 == 1) {
                    SixDayCnt += 1;
                } else if ((7 + today - ItDate.getDay()) % 7 == 0) {
                    TodayCnt += 1;
                    if (ItDate.getHours() == 0) {
                        midnightCnt += 1;
                    } else if (ItDate.getHours() == 1) {
                        time1 += 1;
                    } else if (ItDate.getHours() == 2) {
                        time2 += 1;
                    } else if (ItDate.getHours() == 3) {
                        time3 += 1;
                    } else if (ItDate.getHours() == 4) {
                        time4 += 1;
                    } else if (ItDate.getHours() == 5) {
                        time5 += 1;
                    } else if (ItDate.getHours() == 6) {
                        time6 += 1;
                    } else if (ItDate.getHours() == 7) {
                        time7 += 1;
                    } else if (ItDate.getHours() == 8) {
                        time8 += 1;
                    } else if (ItDate.getHours() == 9) {
                        time9 += 1;
                    } else if (ItDate.getHours() == 10) {
                        time10 += 1;
                    } else if (ItDate.getHours() == 11) {
                        time11 += 1;
                    } else if (ItDate.getHours() == 12) {
                        noonCnt += 1;
                    } else if (ItDate.getHours() == 13) {
                        time13 += 1;
                    } else if (ItDate.getHours() == 14) {
                        time14 += 1;
                    } else if (ItDate.getHours() == 15) {
                        time15 += 1;
                    } else if (ItDate.getHours() == 16) {
                        time16 += 1;
                    } else if (ItDate.getHours() == 17) {
                        time17 += 1;
                    } else if (ItDate.getHours() == 18) {
                        time18 += 1;
                    } else if (ItDate.getHours() == 19) {
                        time19 += 1;
                    } else if (ItDate.getHours() == 20) {
                        time20 += 1;
                    } else if (ItDate.getHours() == 21) {
                        time21 += 1;
                    } else if (ItDate.getHours() == 22) {
                        time22 += 1;
                    } else if (ItDate.getHours() == 23) {
                        time23 += 1;
                    }
                }
            }
            TimeAvg = (midnightCnt + time1 + time2 + time3 + time4 + time5 + time6 + time7 + time8 + time9 + time10 + time11 + noonCnt +
                    time13 + time14 + time15 + time16 + time17 + time18 + time19 + time20 + time21 + time22 + time23) / 24.0;
            DayAvg = (OneDayCnt + TwoDayCnt + ThreeDayCnt + FourDayCnt + FiveDayCnt + SixDayCnt + TodayCnt) / 7.0;

            TimeAvgTxt.setText(String.format("%.1f", TimeAvg) + "/시");
            DayAvgTxt.setText(String.format("%.1f", DayAvg) + "/일");

            GraphInitSetting(); // 그래프 세팅

        } catch (JSONException e) {
            Log.d("result", "showResult : ", e);
        }
    }

    public int findMaxCntDay(int oneDayCnt, int twoDayCnt, int threeDayCnt, int fourDayCnt, int fiveDayCnt, int sixDayCnt, int todayCnt) {
        int CntArr[] = {oneDayCnt, twoDayCnt, threeDayCnt, fourDayCnt, fiveDayCnt, sixDayCnt, todayCnt};
        for (int i = 0; i < CntArr.length; i++) {
            if (MaxCntDay < CntArr[i]) {
                MaxCntDay = CntArr[i];
            }
        }
        MaxCntDay = (int) (MaxCntDay * 1.1);
        return MaxCntDay;
    }

    public int findMaxCntTime(int time1, int time2, int time3, int time4, int time5, int time6, int time7, int time8, int time9, int time10, int time11, int time12) {
        int CntArr[] = {time1, time2, time3, time4, time5, time6, time7, time8, time9, time10, time11, time12};
        for (int i = 0; i < CntArr.length; i++) {
            if (MaxCntTime < CntArr[i]) {
                MaxCntTime = CntArr[i];
            }
        }
        MaxCntTime = (int) (MaxCntTime * 1.1);
        return MaxCntTime;
    }
}

