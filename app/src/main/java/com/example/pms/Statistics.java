package com.example.pms;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class Statistics  extends AppCompatActivity {
    private Toolbar toolbar;
    ArrayList<Integer> timejsonList = new ArrayList<>(); //출입차량 수
    ArrayList<String> timelabelList = new ArrayList<>(); //가로
    ArrayList<Integer> dayjsonList = new ArrayList<>();
    ArrayList<String> daylabelList = new ArrayList<>();
    ArrayList<Integer> weekjsonList = new ArrayList<>(); //출입차량 수
    ArrayList<String> weeklabelList = new ArrayList<>(); //가로
    ArrayList<Integer> monthjsonList = new ArrayList<>();
    ArrayList<String> monthlabelList = new ArrayList<>();
    BarChart chart;
    XAxis xAxis;
    Button btnTime, btnDay, btnWeek, btnMonth;
    ImageButton backbtn;
    String value = "time";
    BarDataSet depenses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        chart = (BarChart)findViewById(R.id.barchart);
        xAxis = chart.getXAxis();
        btnTime = (Button)findViewById(R.id.btn_time);
        btnDay = (Button)findViewById(R.id.btn_day);
        btnWeek = (Button)findViewById(R.id.btn_week);
        btnMonth = (Button)findViewById(R.id.btn_month);
        backbtn = (ImageButton) findViewById(R.id.BackBtn);

        GraphInitSetting();

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.BackBtn:
                        finish();
                        break;
                    case R.id.btn_time:
                        value = "time";
                        GraphInitSetting();
                        break;
                    case R.id.btn_day:
                        value = "day";
                        GraphInitSetting();
                        break;
                    case R.id.btn_week:
                        value = "week";
                        GraphInitSetting();
                        break;
                    case R.id.btn_month:
                        value = "month";
                        GraphInitSetting();
                        break;
                }
            }
        };
        btnTime.setOnClickListener(clickListener);
        btnDay.setOnClickListener(clickListener);
        btnWeek.setOnClickListener(clickListener);
        btnMonth.setOnClickListener(clickListener);
    }

    public void GraphInitSetting(){

        switch (value) {
            case "time":
                timelabelList.add("00시");timelabelList.add("01시");timelabelList.add("02시");timelabelList.add("03시");timelabelList.add("04시");timelabelList.add("05시");
                timelabelList.add("06시");timelabelList.add("07시");timelabelList.add("08시");timelabelList.add("09시");timelabelList.add("10시");timelabelList.add("11시");
                timelabelList.add("12시");timelabelList.add("13시");timelabelList.add("14시");timelabelList.add("15시");timelabelList.add("16시");timelabelList.add("17시");
                timelabelList.add("18시");timelabelList.add("19시");timelabelList.add("20시");timelabelList.add("21시");timelabelList.add("22시");timelabelList.add("23시");

                timejsonList.add(10);timejsonList.add(10);timejsonList.add(50);timejsonList.add(30);timejsonList.add(20);timejsonList.add(44);
                timejsonList.add(94);timejsonList.add(77);timejsonList.add(75);timejsonList.add(38);timejsonList.add(25);timejsonList.add(40);
                timejsonList.add(2);timejsonList.add(63);timejsonList.add(59);timejsonList.add(32);timejsonList.add(63);timejsonList.add(80);
                timejsonList.add(88);timejsonList.add(6);timejsonList.add(10);timejsonList.add(12);timejsonList.add(17);timejsonList.add(22);

                chart.getAxisRight().setAxisMaxValue(100); // 최대값(add의 최대값 + 20정도로 하기)
                chart.getAxisLeft().setAxisMaxValue(100);
                chart.getAxisRight().setAxisMinValue(0); // 최대값(add의 최대값 + 20정도로 하기)
                chart.getAxisLeft().setAxisMinValue(0);

                chart.setTouchEnabled(false); // 터치불가
                BarChartGraph(timelabelList, timejsonList);
                break;
            case "day":
                daylabelList.add("일");daylabelList.add("월");daylabelList.add("화");daylabelList.add("수");daylabelList.add("목");daylabelList.add("금");daylabelList.add("토");

                dayjsonList.add(110);dayjsonList.add(550);dayjsonList.add(260);dayjsonList.add(340);dayjsonList.add(420);dayjsonList.add(480);dayjsonList.add(80);
                chart.getAxisRight().setAxisMaxValue(700); // 최대값(add의 최대값 + 20정도로 하기)
                chart.getAxisLeft().setAxisMaxValue(700);
                chart.getAxisRight().setAxisMinValue(0); // 최대값(add의 최대값 + 20정도로 하기)
                chart.getAxisLeft().setAxisMinValue(0);

                chart.setTouchEnabled(false); // 터치불가
                BarChartGraph(daylabelList, dayjsonList);
                break;
            case "week":

                break;
            case "month":

                break;
        }
    }

    private void BarChartGraph(ArrayList<String> labelList, ArrayList<Integer> valList) {

        ArrayList<BarEntry> entries = new ArrayList<>(); // 출입차량 넣기
        for (int i = 0; i < valList.size(); i++) {
            entries.add(new BarEntry((Integer) valList.get(i), i));
        }

        switch (value) {
            case "time":
                depenses = new BarDataSet(entries, "시간별 출입차량");
                depenses.setAxisDependency(YAxis.AxisDependency.LEFT);
                chart.setDescription(" ");

                depenses.setColors(Collections.singletonList(Color.rgb(77, 148, 255)));
                chart.setVisibleXRangeMaximum(24f);
                break;
            case "day":
                depenses = new BarDataSet(entries, "일간 출입차량"); // 이름
                depenses.setAxisDependency(YAxis.AxisDependency.LEFT);
                chart.setDescription(" ");

                depenses.setColors(Collections.singletonList(Color.rgb(0, 102, 255))); // 컬러 팔레트
                chart.setVisibleXRangeMaximum(7f); // 갯수표시
                break;
            case "week":
                depenses = new BarDataSet(entries, "주간 출입차량"); // 이름
                depenses.setAxisDependency(YAxis.AxisDependency.LEFT);
                chart.setDescription(" ");

                depenses.setColors(Collections.singletonList(Color.rgb(0, 102, 255))); // 컬러 팔레트
                chart.setVisibleXRangeMaximum(5f); // 갯수표시
                break;
            case "month":
                depenses = new BarDataSet(entries, "월간 출입차량"); // 이름
                depenses.setAxisDependency(YAxis.AxisDependency.LEFT);
                chart.setDescription(" ");

                depenses.setColors(Collections.singletonList(Color.rgb(0, 102, 255))); // 컬러 팔레트
                chart.setVisibleXRangeMaximum(12f); // 갯수표시
                break;
        }

        ArrayList<String> labels = new ArrayList<String>(); //한글표시
        for (int i = 0; i < labelList.size(); i++) {
            labels.add((String) labelList.get(i));
        }

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 표시 하단
        xAxis.setDrawGridLines(false); // 세로선 삭제

        BarData data = new BarData(labels, depenses);
        data.setValueFormatter(new MyValueFormatter());

        chart.setData(data);
        chart.animateXY(1000, 1000);
        chart.invalidate();
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
}

/*
1. 일간, 시간 데이터 가져오기
2. 컬러 변경 - 단색에서 다색으로
3. 일간, 시간 데이터 현재를 제일 우측 표시
4. 현재 기준으로 필요 수 만큼 가져오기
 + 디자인
*/

