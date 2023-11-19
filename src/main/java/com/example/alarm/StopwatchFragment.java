package com.example.alarm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class StopwatchFragment extends Fragment {
    private int seconds;
    View view;
    TextView stopclock;
    FloatingActionButton playpause,reset;
    private boolean play,played;

    public StopwatchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("seconds",seconds);
        outState.putBoolean("play",play);
        outState.putBoolean("played",played);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_stopwatch, container, false);
        stopclock=view.findViewById(R.id.stopclock);
        playpause=view.findViewById(R.id.playpause);
        reset=view.findViewById(R.id.reset);
        if(savedInstanceState!=null) {
            savedInstanceState.getInt("seconds");
            savedInstanceState.getBoolean("play");
            savedInstanceState.getBoolean("played");
        }
        runningwatch();
        playpause.setOnClickListener(new View.OnClickListener() {
            boolean flag=true;
            @Override
            public void onClick(View v) {
                if(flag) {
                    playpause.setImageResource(R.drawable.pause);
                    play = true;
                    flag=false;
                }
                else {
                    playpause.setImageResource(R.drawable.play);
                    play=false;
                    flag=true;
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play=false;
                seconds=0;
            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        played=play;
        play=false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(played)
            play=true;
    }

    public void runningwatch(){
        Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hour=seconds/3600;
                int minute=(seconds%3600)/60;
                int second=seconds%60;

                String timings=String.format("%2d:%2d:%2d",hour,minute,second);

                stopclock.setText(timings);
                if(play)
                    seconds++;
                handler.postDelayed(this,1000);
            }
        });
    }
}