package com.example.alarm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextClock;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class ClockFragment extends Fragment {
    private TextClock currenttime;
    private CalendarView todaydate;
    private View view;
    private TextView date;

    public ClockFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_clock, container, false);
        currenttime=view.findViewById(R.id.currenttime);
        todaydate=view.findViewById(R.id.todaydate);
        date=view.findViewById(R.id.date);
        Calendar calendar=Calendar.getInstance();
        currenttime.getFormat12Hour();
        currenttime.getTimeZone();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        date.setText(formatter.format(calendar.getTime()));


        return view;
    }
}