package com.example.pms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CardView register, search, barrier, analytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (CardView)findViewById(R.id.register_card);
        search = (CardView)findViewById(R.id.search_card);
        barrier = (CardView)findViewById(R.id.barrier_card);
        analytics = (CardView)findViewById(R.id.analytics_card);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ManageCustomer.class));
                overridePendingTransition(R.anim.horizon_enter, R.anim.none);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CarSearch.class));
                overridePendingTransition(R.anim.horizon_enter, R.anim.none);
            }
        });

        barrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "차단기 On/Off", Toast.LENGTH_SHORT).show();
            }
        });

        analytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Statistics.class));
                overridePendingTransition(R.anim.horizon_enter, R.anim.none);
            }
        });



    }
}