package com.example.pms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyPageAdapter2 extends RecyclerView.Adapter<MyPageAdapter2.MyViewHolder2> implements OnMyPageItemClickListener{
    private List<MyPageItem2> items = null;
    OnMyPageItemClickListener listener;

    public MyPageAdapter2(ArrayList<MyPageItem2> item) {
        items = item;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.mypage_item2, parent, false);
        MyViewHolder2 viewHolder = new MyViewHolder2(view, this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        MyPageItem2 myPageItem2 = items.get(position);

        holder.SubIcon.setImageResource(myPageItem2.getImgResource());
        holder.SubTitle.setText(myPageItem2.getSubTitle());
        holder.SubDivLine.setImageResource(myPageItem2.getDivColor());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnMyPageItemClickListener listener) {
        this.listener = listener;
    }

    @Override public void onItemClick(MyViewHolder2 holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }



    public static class MyViewHolder2 extends RecyclerView.ViewHolder {
        private ImageView SubIcon;
        private TextView SubTitle;
        private ImageView SubDivLine;

        public MyViewHolder2(@NonNull View itemView, final OnMyPageItemClickListener listener) {
            super(itemView);

            SubIcon = itemView.findViewById(R.id.mypage_sub_icon);
            SubTitle = itemView.findViewById(R.id.mypage_sub_title);
            SubDivLine = itemView.findViewById(R.id.sub_divline);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(MyViewHolder2.this, v, position);
                    }
                }
            });
        }
    }

    public MyPageItem2 getItem(int position){
        return items.get(position);
    }
}
