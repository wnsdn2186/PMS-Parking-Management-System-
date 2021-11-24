package com.example.pms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.SettingViewHolder>{
    private List<SettingItem> mDataList = null;
    private Context mContext = null;
    private OnCheckedChangeListener onItemCheckedListener;


    public interface OnCheckedChangeListener {
        void onCheckedChanged(int position , boolean isChecked);
    }

    public SettingAdapter(Context context, List<SettingItem> items) {
        mContext = context;
        mDataList = items;
    }


    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        onItemCheckedListener = listener;
    }

    @Override
    public SettingViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_item, parent, false);
        return new SettingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SettingViewHolder holder, int position){
        SettingItem settingItem = mDataList.get(position);

        holder.Setting_Title.setText(settingItem.getSettingTitle());
        if(settingItem.isStatus()){
            holder.control_switch.setChecked(true);
        }else{
            holder.control_switch.setChecked(false);
        }
        holder.control_switch.setTag(position);
        holder.control_switch.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class SettingViewHolder extends RecyclerView.ViewHolder{
        TextView Setting_Title;
        Switch control_switch;

        public SettingViewHolder(View itemView){
            super(itemView);
            Setting_Title = (TextView) itemView.findViewById(R.id.setting_title);
            control_switch = (Switch) itemView.findViewById(R.id.control_switch);
        }
    }

    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int position = (int) buttonView.getTag();

            if (onItemCheckedListener != null) {
                onItemCheckedListener.onCheckedChanged(position, isChecked);
            }
        }
    };

}
