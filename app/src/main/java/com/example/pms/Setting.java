package com.example.pms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Setting extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView = null;
    private LinearLayoutManager layoutMgr = null;
    private List<SettingItem> mDataList = null;
    private SettingAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ImageButton backbtn = (ImageButton) findViewById(R.id.BackBtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.none, R.anim.horizon_exit);
            }
        });

        mDataList = new ArrayList<>();
        mDataList.add(new SettingItem("앱 잠금", true));
        mDataList.add(new SettingItem("PUSH 알림", true));
        mDataList.add(new SettingItem("자동로그인", true));

        recyclerView = (RecyclerView) findViewById(R.id.setList);
        layoutMgr = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutMgr);

        mAdapter = new SettingAdapter(this, mDataList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnCheckedChangeListener(mOnCheckedChangeListener);

    }

    private SettingAdapter.OnCheckedChangeListener mOnCheckedChangeListener = new SettingAdapter.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(int position, boolean isChecked) {
            switch (position) {
                case 0:
                    Toast.makeText(Setting.this, position + "앱 잠금", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Setting.this, ScreenLock.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.horizon_enter, R.anim.none);
                    break;

                case 1:
                    Toast.makeText(Setting.this, position + "PUSH 알림", Toast.LENGTH_LONG).show();
                    break;

                case 2:
                    Toast.makeText(Setting.this, position + "자동로그인", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
}
