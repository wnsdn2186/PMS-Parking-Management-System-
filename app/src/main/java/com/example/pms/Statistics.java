package com.example.pms;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Statistics  extends AppCompatActivity {
    private Toolbar toolbar;
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
            }
        });


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
        dayjsonList.add(10);
        dayjsonList.add(50);
        dayjsonList.add(30);
        dayjsonList.add(20);
        dayjsonList.add(40);
        dayjsonList.add(40);
        dayjsonList.add(80);

        // 임시값
        timejsonList.add(10);timejsonList.add(10);timejsonList.add(50);timejsonList.add(30);timejsonList.add(20);timejsonList.add(44);
        timejsonList.add(94);timejsonList.add(77);timejsonList.add(75);timejsonList.add(38);timejsonList.add(25);timejsonList.add(40);
        timejsonList.add(2);timejsonList.add(63);timejsonList.add(59);timejsonList.add(32);timejsonList.add(63);timejsonList.add(80);
        timejsonList.add(88);timejsonList.add(6);timejsonList.add(10);timejsonList.add(12);timejsonList.add(17);timejsonList.add(22);

        DayBarChartGraph(daylabelList, dayjsonList);
        TimeBarChartGraph(timelabelList, timejsonList);
        daychart.setTouchEnabled(false); // 터치불가 --> 추후 변경

        daychart.getAxisRight().setAxisMaxValue(100); // 최대값(add의 최대값 + 20정도로 하기)
        daychart.getAxisLeft().setAxisMaxValue(100);

        timechart.getAxisRight().setAxisMaxValue(100); // 최대값(add의 최대값 + 20정도로 하기)
        timechart.getAxisLeft().setAxisMaxValue(100);
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
        depenses.setColors(ColorTemplate.LIBERTY_COLORS); // 컬러 팔레트
        daychart.setVisibleXRangeMaximum(7f); // 갯수표시
        xAxisday.setPosition(XAxis.XAxisPosition.BOTTOM); // 요일 표시 하단
        xAxisday.setDrawGridLines(false); // 세로선 삭제
        //barChart.getAxisLeft().setEnabled(false); // 왼쪽 수치 표시x

        BarData data = new BarData(labels, depenses);

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
        depenses.setColors(ColorTemplate.LIBERTY_COLORS);
        timechart.setVisibleXRangeMaximum(24f);
        xAxistime.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxistime.setDrawGridLines(false);
        //timechart.getAxisLeft().setEnabled(false);

        BarData data = new BarData(labels, depenses);

        timechart.setData(data);
        timechart.animateXY(1000, 1000);
        timechart.invalidate();
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }
}

/*
1. 일간, 시간 데이터 가져오기
2. 컬러 변경
3. 일간, 시간 데이터 현재를 제일 우측 표시
4. 현재 기준으로 필요 수 만큼 가져오기
5. 소수점 제거
6. 터치 기능 수정
7. 10이하 표기 시 0아래 뜨는 현상 수정
 + 디자인
*/

