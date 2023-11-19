package com.example.alarm;

import static android.content.Context.ALARM_SERVICE;
import static java.lang.Math.abs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;


public class AlarmFragment extends Fragment {
    private View view;
    ArrayList<AlarmStructure> alarmStructure = new ArrayList<>();
    RecyclerView recyclerView;
    FloatingActionButton setbtn;
    private long offsetTime;

    public AlarmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_alarm, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        setbtn = view.findViewById(R.id.setalarm);


        DBHelper db = DBHelper.getDB(getContext());
        alarmStructure = (ArrayList<AlarmStructure>) db.alarmDao().fetching();
        CustomAlarmAdapter alarmAdapter = new CustomAlarmAdapter(getContext(), alarmStructure);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(alarmAdapter);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
        setbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showtimepicker(db, alarmManager);
                alarmStructure = (ArrayList<AlarmStructure>) db.alarmDao().fetching();
                CustomAlarmAdapter alarmAdapter = new CustomAlarmAdapter(getContext(), alarmStructure);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(alarmAdapter);
                alarmAdapter.notifyDataSetChanged();
                alarmAdapter.notifyItemChanged(alarmStructure.size() - 1);
            }
        });
        return view;
    }

    public void showtimepicker(DBHelper db, AlarmManager alarmManager) {
        Calendar current = Calendar.getInstance();
        int current_hour = current.get(Calendar.HOUR);
        int current_minute = current.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //int requestcode=(int) System.currentTimeMillis();
                //AlarmManage alarm=new AlarmManage(hourOfDay,minute);
                String zone;
                int exacthour;
                if (hourOfDay >= 12) {
                    if (hourOfDay == 12)
                        exacthour = 12;
                    else
                        exacthour = hourOfDay - 12;
                    db.alarmDao().alarmAdd(new AlarmStructure(Integer.toString((exacthour)), Integer.toString(minute), "PM", "ON", "0 minutes"));
                } else {
                    exacthour = hourOfDay;
                    db.alarmDao().alarmAdd(new AlarmStructure(Integer.toString(exacthour), Integer.toString(minute), "AM", "OFF", "O minutes"));
                }
                //offsetTime=(((hourOfDay*3600)+(minute*60))*1000)+ System.currentTimeMillis();
                Log.d("Time", "onTimeSet: sys" + System.currentTimeMillis());

                if(exacthour<current_hour){
                    offsetTime=((24-(current_hour-exacthour))*3600 + abs(minute - current_minute) * 60) * 990 + System.currentTimeMillis();
                } else if (exacthour==current_hour && minute>current_minute) {
                    offsetTime=((minute-current_minute)*60)*900 +System.currentTimeMillis();
                }
                else{
                    offsetTime = ((exacthour - current_hour) * 3600 + abs(minute - current_minute) * 60) * 990 + System.currentTimeMillis();
                }
                Log.d("Time", "onTimeSet: total" + offsetTime);
                //offsetTime=100000+System.currentTimeMillis();
                alarmStructure = (ArrayList<AlarmStructure>) db.alarmDao().fetching();
                Intent ibroadcast = new Intent(getContext(), AlarmBroadcastReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), alarmStructure.size(), ibroadcast, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, offsetTime, pendingIntent);
                CustomAlarmAdapter alarmAdapter = new CustomAlarmAdapter(getContext(), alarmStructure);
                recyclerView.setAdapter(alarmAdapter);
                recyclerView.scrollToPosition(alarmStructure.size() - 1);
                Toast.makeText(getContext(), alarmStructure.size() + " Alarm added", Toast.LENGTH_SHORT).show();
            }
        }, current_hour, current_minute, false);
        timePickerDialog.show();
    }

    public static void alarmSetter(Context context, ArrayList<AlarmStructure> alarmStructure, int position) {
        Calendar cal = Calendar.getInstance();
        long settingTime = ((abs(Integer.parseInt(alarmStructure.get(position).hour) - cal.get(Calendar.HOUR)) * 3600) + (abs(Integer.parseInt(alarmStructure.get(position).minute) - cal.get(Calendar.HOUR)) * 60)) * 1000;
        long setTime = settingTime + System.currentTimeMillis();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        int requestcode = position + 1;
        Intent ibroadcast = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestcode, ibroadcast, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, setTime, pendingIntent);
        Toast.makeText(context, requestcode + " Alarm is ON", Toast.LENGTH_SHORT).show();
    }

    public static void alarmcancel(Context context, ArrayList<AlarmStructure> alarmStructure, int position) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent ibroadcast = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (position + 1), ibroadcast, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context, (position + 1) + " Alarm is OFF", Toast.LENGTH_SHORT).show();
    }
}