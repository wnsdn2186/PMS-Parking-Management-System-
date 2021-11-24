package com.example.pms;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomViewHolder>{
    private ArrayList<Customer> cList = null;
    private Activity context = null;
    private ImageButton imgbtn;
    private static String IP_ADDRESS = "13.59.85.177";
    private OnDeleteClickListener mListener = null;
    private CustomDialog customDialog;
    private int pos;

    public interface OnDeleteClickListener {
        void onDelete(int idx);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.mListener = listener;
    }

    public CustomerAdapter(Activity context, ArrayList<Customer> list) {
        this.context = context;
        this.cList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView idx;
        protected TextView name;
        protected TextView pnum;
        protected TextView cnum;
        protected TextView rdate;
        protected ImageButton deletebtn;
        protected LinearLayout Period;
        protected ImageView ShowPeriodBtn;

        public CustomViewHolder(View view) {
            super(view);
            this.idx = (TextView) view.findViewById(R.id.cusIdx);
            this.name = (TextView) view.findViewById(R.id.cusName);
            this.cnum = (TextView) view.findViewById(R.id.cusCnum);
            this.deletebtn = (ImageButton) view.findViewById(R.id.deleteButton);
            this.Period = (LinearLayout) view.findViewById(R.id.linear);
            this.ShowPeriodBtn = (ImageView) view.findViewById(R.id.ShowPeriodBtn);

            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            mListener.onDelete(cList.get(pos).getId());
                        }
                    }
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(Period.getVisibility() == View.VISIBLE){
                            ShowPeriodBtn.setImageResource(R.drawable.ic_down_24);
                            Period.setVisibility(View.GONE);
                        }else{
                            ShowPeriodBtn.setImageResource(R.drawable.ic_up_24);
                            Period.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customer_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        viewholder.idx.setText(String.valueOf(position + 1));;
        viewholder.name.setText(cList.get(position).getName());
        viewholder.cnum.setText(cList.get(position).getCnum());
    }

    @Override
    public int getItemCount() {
        return (null != cList ? cList.size() : 0);
    }
}