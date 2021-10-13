package com.example.pms;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyPage extends AppCompatActivity {
    private RecyclerView recyclerView = null;
    private MyPageAdapter adapter = null;
    ArrayList<MyPageItem> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        ImageButton backbtn = (ImageButton) findViewById(R.id.BackBtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.rcView);
        mList = new ArrayList<>();

        adapter = new MyPageAdapter(mList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        addItem(R.drawable.email, "이메일(아이디)", "pms@knu.ac.kr", R.drawable.color_gray_round);
        addItem(R.drawable.padlock, "비밀번호", "12345678", R.drawable.color_gray_round);
        addItem(R.drawable.calendar, "생년월일", "1997.11.11", R.drawable.color_gray_round);
        addItem(R.drawable.phone, "번호", "010-1234-5678", R.drawable.color_gray_round);
        addItem(R.drawable.registered, "가입일", "2021.10.11", R.drawable.color_white_round);

        adapter.notifyDataSetChanged();
    }

    private void addItem(int imgResource, String info_title, String info, int divColor){
        MyPageItem item = new MyPageItem();

        item.setImgResource(imgResource);
        item.setInfoTitle(info_title);
        item.setInfo(info);
        item.setDivColor(divColor);

        mList.add(item);
    }
}
