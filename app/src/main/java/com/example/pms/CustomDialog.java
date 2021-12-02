package com.example.pms;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Objects;

public class CustomDialog extends Dialog {
    private Button Confirm;
    private Button Cancel;
    private final View.OnClickListener ConfirmBtn;
    private final View.OnClickListener CancelBtn;
    public TextView Title;
    public String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);

        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Confirm = findViewById(R.id.Confirm);
        Cancel = findViewById(R.id.Cancel);
        Title = findViewById(R.id.dialog_title);
        Confirm = findViewById(R.id.Confirm);
        Cancel = findViewById(R.id.Cancel);

        Confirm.setOnClickListener(ConfirmBtn);
        Cancel.setOnClickListener(CancelBtn);

        Title.setText(this.title);
    }

    public CustomDialog(@NonNull Context context, View.OnClickListener ConfirmBtn, View.OnClickListener CancelBtn, String title) {
        super(context);
        //생성자에서 리스너 및 텍스트 초기화
        this.ConfirmBtn = ConfirmBtn;
        this.CancelBtn = CancelBtn;
        this.title = title;
    }
}