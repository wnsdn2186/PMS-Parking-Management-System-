package com.example.pms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

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

    CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        ImageButton backbtn = (ImageButton) findViewById(R.id.BackBtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.none, R.anim.horizon_exit);
            }
        });

        recyclerView = findViewById(R.id.rcView);
        mList = new ArrayList<>();

        adapter = new MyPageAdapter(mList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        addItem(R.drawable.ic_email_24, "이메일(아이디)", "admin@knu.ac.kr", R.drawable.color_grey_round);
        addItem(R.drawable.ic__key_24, "비밀번호", "admin", R.drawable.color_grey_round);
        addItem(R.drawable.ic_date_24, "생년월일", "1997.11.11", R.drawable.color_grey_round);
        addItem(R.drawable.ic_phone_24, "번호", "010-1234-5678", R.drawable.color_grey_round);
        addItem(R.drawable.ic_register_24, "가입일", "2021.10.16", R.drawable.color_white_round);

        recyclerView2 = findViewById(R.id.rcView2);
        mList2 = new ArrayList<>();

        adapter2 = new MyPageAdapter2(mList2);
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        addItem2(R.drawable.ic_forward_36, "로그아웃", R.drawable.color_grey_round, mList2);
        addItem2(R.drawable.ic_forward_36, "회원정보 수정", R.drawable.color_grey_round, mList2);
        addItem2(R.drawable.ic_forward_36, "회원 탈퇴", R.drawable.color_white_round, mList2);

        recyclerView3 = findViewById(R.id.rcView3);
        mList3 = new ArrayList<>();

        adapter3 = new MyPageAdapter2(mList3);
        recyclerView3.setAdapter(adapter3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        addItem2(R.drawable.ic_forward_36, "공지사항", R.drawable.color_grey_round, mList3);
        addItem2(R.drawable.ic_forward_36, "고객센터", R.drawable.color_white_round, mList3);

        recyclerView4 = findViewById(R.id.rcView4);
        mList4 = new ArrayList<>();

        adapter4 = new MyPageAdapter2(mList4);
        recyclerView4.setAdapter(adapter4);
        recyclerView4.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        addItem2(R.drawable.ic_forward_36, "서비스 이용약관", R.drawable.color_grey_round, mList4);
        addItem2(R.drawable.ic_forward_36, "개인정보 처리방침", R.drawable.color_white_round, mList4);


        adapter.notifyDataSetChanged();

        adapter2.notifyDataSetChanged();
        adapter2.setOnItemClickListener(new OnMyPageItemClickListener() {
            @Override
            public void onItemClick(MyPageAdapter2.MyViewHolder2 holder, View view, int position) {
                switch (position){
                    case 0:
                        customDialog = new CustomDialog(MyPage.this, Confirm, Cancel, "로그아웃 하시겠습니까?");
                        customDialog.show();
                        break;

                    case 1:
                        Intent intent = new Intent(MyPage.this, EditAccount.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.horizon_enter, R.anim.none);
                        break;

                    case 2:
                        customDialog = new CustomDialog(MyPage.this, Confirm, Cancel, "정말 탈퇴하시겠습니까?");
                        customDialog.show();
                        break;

                }
            }
        });

        adapter3.notifyDataSetChanged();
        adapter3.setOnItemClickListener(new OnMyPageItemClickListener() {
            @Override
            public void onItemClick(MyPageAdapter2.MyViewHolder2 holder, View view, int position) {
                MyPageItem2 myPageItem2 = adapter3.getItem(position);
                Toast.makeText(getApplicationContext(), myPageItem2.getSubTitle(), Toast.LENGTH_LONG).show();
            }
        });

        adapter4.notifyDataSetChanged();
        adapter4.setOnItemClickListener(new OnMyPageItemClickListener() {
            @Override
            public void onItemClick(MyPageAdapter2.MyViewHolder2 holder, View view, int position) {
                MyPageItem2 myPageItem2 = adapter4.getItem(position);
                Toast.makeText(getApplicationContext(), myPageItem2.getSubTitle(), Toast.LENGTH_LONG).show();
            }
        });
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

    private View.OnClickListener Confirm = new View.OnClickListener() {
        public void onClick(View v) {
            Intent logout_intent = new Intent(MyPage.this, Login.class);
            logout_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(logout_intent);
            customDialog.dismiss();
        }
    };

    private View.OnClickListener Cancel = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_LONG).show();
            customDialog.dismiss();
        }
    };
}
