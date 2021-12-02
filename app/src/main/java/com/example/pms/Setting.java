package com.example.pms;

import android.content.Intent;
import android.os.Bundle;
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
    private final String On = "on";
    private final String Off = "off";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        PrefsHelper.init(getApplicationContext());

        ImageButton backbtn = (ImageButton) findViewById(R.id.BackBtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.none, R.anim.horizon_exit);
            }
        });

        mDataList = new ArrayList<>();

        if (On.equals(PrefsHelper.read("Lock", ""))) {
            mDataList.add(new SettingItem("앱 잠금", true));
        } else {
            mDataList.add(new SettingItem("앱 잠금", false));
        }

        if (Off.equals(PrefsHelper.read("Push", ""))) {
            mDataList.add(new SettingItem("차단기 PUSH 알림", false));
        } else {
            mDataList.add(new SettingItem("차단기 PUSH 알림", true));
        }

        if (On.equals(PrefsHelper.read("AutoLogin", ""))) {
            mDataList.add(new SettingItem("자동로그인", true));
        } else {
            mDataList.add(new SettingItem("자동로그인", false));
        }

        recyclerView = (RecyclerView) findViewById(R.id.setList);
        layoutMgr = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutMgr);

        mAdapter = new SettingAdapter(this, mDataList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnCheckedChangeListener(mOnCheckedChangeListener);


    }

    private final SettingAdapter.OnCheckedChangeListener mOnCheckedChangeListener = new SettingAdapter.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(int position, boolean isChecked) {
            switch (position) {
                case 0:
                    if (On.equals(PrefsHelper.read("AutoLogin", ""))) {
                        if (isChecked) {
                            PrefsHelper.write("Lock", "on");
                            Intent intent = new Intent(Setting.this, ScreenLock.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.horizon_enter, R.anim.none);
                        } else {
                            PrefsHelper.write("Lock", "off");
                        }
                    } else {
                        Toast.makeText(Setting.this, "자동로그인을 먼저 해주세요", Toast.LENGTH_LONG).show();
                    }
                    break;

                case 1:
                    if (isChecked) {
                        PrefsHelper.write("Push", "on");
                    } else {
                        PrefsHelper.write("Push", "off");
                    }
                    break;

                case 2:
                    if (isChecked) {
                        PrefsHelper.write("AutoLogin", "on");
                    } else {
                        PrefsHelper.write("AutoLogin", "off");
                    }
                    break;
            }
        }
    };
}
