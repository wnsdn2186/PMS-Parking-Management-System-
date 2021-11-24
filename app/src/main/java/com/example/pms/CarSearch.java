package com.example.pms;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CarSearch extends AppCompatActivity {
    private TextView search_box;
    private RecyclerView recyclerView = null;
    private CarSearchAdapter adapter = null;
    private ArrayList<CarSearchItem> mList, FilteredList;
    private int cnt = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_search);

        ImageButton backbtn = (ImageButton) findViewById(R.id.BackBtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.none, R.anim.horizon_exit);
            }
        });

        FilteredList = new ArrayList<>();
        search_box = findViewById(R.id.search_box);
        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String CarNum = search_box.getText().toString();
                searchFilter(CarNum);
            }
        });

        mList = new ArrayList<>();
        adapter = new CarSearchAdapter(mList, this);
        recyclerView = findViewById(R.id.car_search_list);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addItem(cnt++, "2021-11-13", "http://13.59.85.177/img/icon.png", "123가4568", "17:23", "19:20");
        addItem(cnt++, "2021-11-13", "https://sikigobucket.s3.ap-northeast-2.amazonaws.com/test2.jpg", "403주2975", "14:23", "15:20");
        addItem(cnt++, "2021-11-12", "https://sikigobucket.s3.ap-northeast-2.amazonaws.com/test3.PNG", "381마8947", "12:23", "15:20");
        addItem(cnt++, "2021-11-11", "https://sikigobucket.s3.ap-northeast-2.amazonaws.com/test4.PNG", "43고3171", "09:14", "15:16");

        adapter.notifyDataSetChanged();
    }

    private void addItem(Integer Cnt, String Date, String ImgURL, String CarNum, String InTime, String OutTime) {
        CarSearchItem items = new CarSearchItem();

        items.setCnt(Cnt);
        items.setDate(Date);
        items.setImgURL(ImgURL);
        items.setCarNum(CarNum);
        items.setInTime(InTime);
        items.setOutTime(OutTime);

        mList.add(items);
    }

    private void searchFilter(String SearchText) {
        FilteredList.clear();

        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getCarNum().toLowerCase().contains(SearchText.toLowerCase())) {
                FilteredList.add(mList.get(i));
            }
        }

        adapter.FilterList(FilteredList);
    }
}
