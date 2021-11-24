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

public class MyPageAdapter extends RecyclerView.Adapter<MyPageAdapter.MyViewHolder> {
    private List<MyPageItem> items = null;

    public MyPageAdapter(ArrayList<MyPageItem> item){
        items = item;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.mypage_item, parent, false);
        MyPageAdapter.MyViewHolder viewHolder = new MyPageAdapter.MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position){
        MyPageItem myPageItem = items.get(position);

        holder.Info_Icon.setImageResource(myPageItem.getImgResource());
        holder.Info_Title.setText(myPageItem.getInfoTitle());
        holder.Info.setText(myPageItem.getInfo());
        holder.DivLine.setImageResource(myPageItem.getDivColor());
    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView Info_Icon;
        private TextView Info_Title;
        private TextView Info;
        private ImageView DivLine;

        MyViewHolder(@NonNull View itemView){
            super(itemView);

            Info_Icon = itemView.findViewById(R.id.mypage_info_icon);
            Info_Title = itemView.findViewById(R.id.mypage_info_title);
            Info = itemView.findViewById(R.id.mypage_info);
            DivLine = itemView.findViewById(R.id.divline);
        }
    }
}
