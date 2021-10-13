package com.example.pms;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyPage extends AppCompatActivity {
    private RecyclerView recyclerView = null;
    private MyPageAdapter adapter = null;
    ArrayList<MyPageItem> mList;

    private RecyclerView recyclerView2 = null;
    private MyPageAdapter2 adapter2 = null;
    ArrayList<MyPageItem2> mList2;


    private RecyclerView recyclerView3 = null;
    private MyPageAdapter2 adapter3 = null;
    ArrayList<MyPageItem2> mList3;

    private RecyclerView recyclerView4 = null;
    private MyPageAdapter2 adapter4 = null;
    ArrayList<MyPageItem2> mList4;

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

        addItem(R.drawable.ic_email_24, "이메일(아이디)", "pms@knu.ac.kr", R.drawable.color_gray_round);
        addItem(R.drawable.ic__key_24, "비밀번호", "12345678", R.drawable.color_gray_round);
        addItem(R.drawable.ic_date_24, "생년월일", "1997.11.11", R.drawable.color_gray_round);
        addItem(R.drawable.ic_phone_24, "번호", "010-1234-5678", R.drawable.color_gray_round);
        addItem(R.drawable.ic_register_24, "가입일", "2021.10.11", R.drawable.color_white_round);

        recyclerView2 = findViewById(R.id.rcView2);
        mList2 = new ArrayList<>();

        adapter2 = new MyPageAdapter2(mList2);
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        addItem2(R.drawable.ic_forward_36, "회원정보 수정", R.drawable.color_gray_round, mList2);
        addItem2(R.drawable.ic_forward_36, "회원 탈퇴", R.drawable.color_white_round, mList2);

        recyclerView3 = findViewById(R.id.rcView3);
        mList3 = new ArrayList<>();

        adapter3 = new MyPageAdapter2(mList3);
        recyclerView3.setAdapter(adapter3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        addItem2(R.drawable.ic_forward_36, "공지사항", R.drawable.color_gray_round, mList3);
        addItem2(R.drawable.ic_forward_36, "고객센터", R.drawable.color_white_round, mList3);

        recyclerView4 = findViewById(R.id.rcView4);
        mList4 = new ArrayList<>();

        adapter4 = new MyPageAdapter2(mList4);
        recyclerView4.setAdapter(adapter4);
        recyclerView4.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        addItem2(R.drawable.ic_forward_36, "서비스 이용약관", R.drawable.color_gray_round, mList4);
        addItem2(R.drawable.ic_forward_36, "개인정보 처리방침", R.drawable.color_white_round, mList4);


        adapter.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        adapter3.notifyDataSetChanged();
        adapter4.notifyDataSetChanged();
    }

    private void addItem(int imgResource, String info_title, String info, int divColor){
        MyPageItem item = new MyPageItem();

        item.setImgResource(imgResource);
        item.setInfoTitle(info_title);
        item.setInfo(info);
        item.setDivColor(divColor);

        mList.add(item);
    }

    private void addItem2(int imgResource, String sub_title, int divColor, ArrayList<MyPageItem2> list){
        MyPageItem2 item2 = new MyPageItem2();

        item2.setImgResource(imgResource);
        item2.setSubTitle(sub_title);
        item2.setDivColor(divColor);

        list.add(item2);
    }
}
