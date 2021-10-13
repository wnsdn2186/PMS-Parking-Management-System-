package com.example.pms;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomViewHolder>{
    private ArrayList<Customer> cList = null;
    private Activity context = null;

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

        public CustomViewHolder(View view) {
            super(view);
            this.idx = (TextView) view.findViewById(R.id.cusIdx);
            this.name = (TextView) view.findViewById(R.id.cusName);
            this.cnum = (TextView) view.findViewById(R.id.cusCnum);
            //this.rdate = (TextView) view.findViewById(R.id.cusSdate);
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
        viewholder.idx.setText(String.valueOf(position + 1));
        viewholder.name.setText(cList.get(position).getName());
        viewholder.cnum.setText(cList.get(position).getCnum());
//        viewholder.rdate.setText(cList.get(position).getRdate());
    }

    @Override
    public int getItemCount() {
        return (null != cList ? cList.size() : 0);
    }

}