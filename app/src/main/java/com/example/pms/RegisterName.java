package com.example.pms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class RegisterName extends AppCompatActivity {
    private FloatingActionButton next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_name);

        next_btn = (FloatingActionButton)findViewById(R.id.nameNext);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.nameField);
                String cname = name.getText().toString();

                if (cname.length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "성함을 입력하세요!", Toast.LENGTH_LONG).show();
                    name.requestFocus();
                } else {
                    Intent it = new Intent(RegisterName.this, RegisterPhone.class);
                    it.putExtra("name", cname);
                    startActivity(it);
                    overridePendingTransition(R.anim.horizon_enter, R.anim.none);
                    finish();
                }
            }
        });
    }
}
