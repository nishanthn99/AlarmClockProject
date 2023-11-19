package com.example.alarm;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class CustomAlarmAdapter extends RecyclerView.Adapter<CustomAlarmAdapter.ViewHolder> {
    private static ArrayList<AlarmStructure> alarmdata;
    private static Context context;

    private String[] snooze=new String[]{"0 minutes","2 minutes","5 minutes","10 minutes","15 minutes"};
    public CustomAlarmAdapter(Context context, ArrayList<AlarmStructure> alarmdata) {
        this.context=context;
        this.alarmdata=alarmdata;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customlayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        int pos=position;
        holder.hour.setText(alarmdata.get(position).hour);
        holder.minute.setText(alarmdata.get(position).minute);
        holder.zone.setText(alarmdata.get(position).zone);
        ArrayAdapter<String> adapter=new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item,snooze);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        holder.snoozeing.setAdapter(adapter);
//    try {
//        holder.snoozeing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(context, "clicked" + position, Toast.LENGTH_SHORT).show();
//                Log.d("check", "onItemClick: cameee");
//                //               DBHelper.getDB(context).alarmDao().alarmEdit(new AlarmStructure(alarmdata.get(pos).hour,alarmdata.get(pos).minute,alarmdata.get(pos).zone,alarmdata.get(pos).status,parent.getItemAtPosition(position).toString()));
//            }
//
//            public void onNothingSelected(AdapterView<?> parent) {
//                Log.d("Nothing", "onNothingSelected: not selected");
//            }
//        });
//    }
//    catch (Exception ex){
//        ex.printStackTrace();
//    }
        holder.toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int flag=1;
                if(!buttonView.isChecked() && flag==1){
                    DBHelper.getDB(context).alarmDao().alarmEdit(new AlarmStructure(alarmdata.get(pos).hour,alarmdata.get(pos).minute,alarmdata.get(pos).zone,"OFF",alarmdata.get(pos).snooze));
                    AlarmFragment.alarmcancel(context,alarmdata,pos);
                    flag=0;
                }
                else {
                    DBHelper.getDB(context).alarmDao().alarmEdit(new AlarmStructure(alarmdata.get(pos).hour,alarmdata.get(pos).minute,alarmdata.get(pos).zone,"ON",alarmdata.get(pos).snooze));
                    AlarmFragment.alarmSetter(context,alarmdata,pos);
                    flag=1;
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return alarmdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView hour,minute,zone;
        public Spinner snoozeing;
        public Switch toggle;

        public ViewHolder(View view) {
            super(view);
            hour=(TextView) view.findViewById(R.id.hour);
            minute=(TextView)view.findViewById(R.id.minute);
            zone=(TextView) view.findViewById(R.id.zone);
            toggle=(Switch) view.findViewById(R.id.toggle);
            snoozeing=(Spinner) view.findViewById(R.id.spinner);
        }
    }

}
