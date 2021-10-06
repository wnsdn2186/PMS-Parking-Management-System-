package com.example.pms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // toolbar setting
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        Button RegisterBtn = (Button)findViewById(R.id.CarRegister);//차량등록 버튼
        Button SearchBtn = (Button)findViewById(R.id.CarSearch);//차량검색 버튼
        Button BarrierBtn = (Button)findViewById(R.id.Barrier);//차단기 On/Off
        Button StatisticsBtn = (Button)findViewById(R.id.Statistics);//통계 버튼

        //차량등록 Activity 연결
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CarRegister.class));
                overridePendingTransition(R.anim.horizon_enter, R.anim.none);
            }
        });

        //차량검색 Activity 연결
        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CarSearch.class));
                overridePendingTransition(R.anim.horizon_enter, R.anim.none);
            }
        });

        //차단기 On/Off
        BarrierBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                //여기 코드에서 바로
                //차단기 On/Off 할 수 있도록
                Toast.makeText(MainActivity.this, "차단기 On/Off", Toast.LENGTH_SHORT).show();
            }
        });

        //통계 Activity 연결
        StatisticsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Statistics.class));
                overridePendingTransition(R.anim.horizon_enter, R.anim.none);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Setting:
                Toast.makeText(getApplicationContext(), "설정", Toast.LENGTH_LONG).show();
                return true;

            case R.id.Mypage:
                Toast.makeText(getApplicationContext(), "마이페이지", Toast.LENGTH_LONG).show();
                return true;

            case R.id.Logout:
                Toast.makeText(getApplicationContext(), "로그아웃", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}