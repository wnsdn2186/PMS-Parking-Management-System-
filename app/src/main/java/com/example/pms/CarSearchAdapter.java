package com.example.pms;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.invoke.LambdaConversionException;
import java.util.ArrayList;
import java.util.List;

public class CarSearchAdapter extends RecyclerView.Adapter<CarSearchAdapter.CarSearchHolder> {
    private Context context;
    private List<CarSearchItem> items = null;

    public CarSearchAdapter(ArrayList<CarSearchItem> item) {
        items = item;
    }

    @NonNull
    @Override
    public CarSearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.carsearch_item, parent, false);
        CarSearchAdapter.CarSearchHolder viewHolder = new CarSearchAdapter.CarSearchHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarSearchHolder holder, int position) {
        CarSearchItem carSearchItem = items.get(position);

        holder.Cnt.setText(String.valueOf(carSearchItem.getCnt()));
        holder.Date.setText(carSearchItem.getDate());
        Glide.with(context).load(carSearchItem.getImgURL()).into(holder.NumPlate);
        holder.CarNum.setText(carSearchItem.getCarNum());
        holder.InTime.setText(carSearchItem.getInTime());
        holder.OutTime.setText(carSearchItem.getOutTime());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class CarSearchHolder extends RecyclerView.ViewHolder {
        private TextView Date;
        private ImageView NumPlate;
        private TextView CarNum;
        private TextView InTime;
        private TextView OutTime;
        private TextView Cnt;

        CarSearchHolder(@NonNull View itemView) {
            super(itemView);

            Cnt = itemView.findViewById(R.id.Cnt);
            Date = itemView.findViewById(R.id.Date);
            NumPlate = itemView.findViewById(R.id.NumPlate);
            NumPlate.setClipToOutline(true);
            CarNum = itemView.findViewById(R.id.CarNum);
            InTime = itemView.findViewById(R.id.InTime);
            OutTime = itemView.findViewById(R.id.OutTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (NumPlate.getVisibility() == View.VISIBLE) {
                            NumPlate.setVisibility(View.GONE);
                        } else {
                            NumPlate.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }
    }

    public void FilterList(ArrayList<CarSearchItem> FilteredList){
        items = FilteredList;
        notifyDataSetChanged();
    }
}
