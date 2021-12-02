package com.example.pms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class CarSearchAdapter extends RecyclerView.Adapter<CarSearchAdapter.CarSearchHolder> {
    private final Activity context;
    private List<CarSearchItem> items = null;
    private String URL;

    public CarSearchAdapter(ArrayList<CarSearchItem> item, Activity mContext) {
        context = mContext;
        items = item;
    }

    @NonNull
    @Override
    public CarSearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) (parent.getContext()).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.carsearch_item, parent, false);
        CarSearchAdapter.CarSearchHolder viewHolder = new CarSearchAdapter.CarSearchHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarSearchHolder holder, int position) {
        CarSearchItem carSearchItem = items.get(position);

        holder.Cnt.setText(String.valueOf(carSearchItem.getCnt()));
        holder.Date.setText(carSearchItem.getDate());
        Glide.with(context).load(carSearchItem.getImgURL()).apply(RequestOptions.bitmapTransform(new RoundedCorners(15))).into(holder.NumPlate);
        holder.CarNum.setText(carSearchItem.getCarNum());
        holder.InTime.setText(carSearchItem.getInTime());
        holder.OutTime.setText(carSearchItem.getOutTime());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class CarSearchHolder extends RecyclerView.ViewHolder {
        private final TextView Cnt;
        private final TextView Date;
        private final TextView CarNum;
        private final TextView InTime;
        private final TextView OutTime;
        private final ImageView NumPlate;
        private final ImageView ShowPicBtn;

        CarSearchHolder(@NonNull View itemView) {
            super(itemView);

            Cnt = itemView.findViewById(R.id.Cnt);
            Date = itemView.findViewById(R.id.Date);

            CarNum = itemView.findViewById(R.id.CarNum);
            InTime = itemView.findViewById(R.id.InTime);
            OutTime = itemView.findViewById(R.id.OutTime);

            NumPlate = itemView.findViewById(R.id.NumPlate);
            NumPlate.setClipToOutline(true);

            ShowPicBtn = itemView.findViewById(R.id.ShowPicBtn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (NumPlate.getVisibility() == View.VISIBLE) {
                            ShowPicBtn.setImageResource(R.drawable.ic_down_24);
                            NumPlate.setVisibility(View.GONE);
                        } else {
                            ShowPicBtn.setImageResource(R.drawable.ic_up_24);
                            NumPlate.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

            NumPlate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        CarSearchItem item = items.get(pos);
                        Intent intent = new Intent(context, MagnifyingImage.class);
                        intent.putExtra("URL", item.getImgURL());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    public void FilterList(ArrayList<CarSearchItem> FilteredList) {
        items = FilteredList;
        notifyDataSetChanged();
    }
}
