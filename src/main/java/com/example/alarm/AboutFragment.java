package com.example.alarm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

public class AboutFragment extends Fragment {
    private View view;

    private TextView aboutid1,aboutid2,aboutid3,aboutid4,aboutid5,aboutid6,aboutid7,aboutid8,aboutid9;
    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_about, container, false);
        aboutid1=view.findViewById(R.id.aboutid1);
        aboutid2=view.findViewById(R.id.aboutid2);
        aboutid3=view.findViewById(R.id.aboutid3);
        aboutid4=view.findViewById(R.id.aboutid4);
        aboutid5=view.findViewById(R.id.aboutid5);
        aboutid6=view.findViewById(R.id.aboutid6);
        aboutid7=view.findViewById(R.id.aboutid7);
        aboutid8=view.findViewById(R.id.aboutid8);
        aboutid9=view.findViewById(R.id.aboutid9);
        Animation translate= AnimationUtils.loadAnimation(getContext(),R.anim.translate);
        aboutid1.setAnimation(translate);
        aboutid2.setAnimation(translate);
        aboutid3.setAnimation(translate);
        aboutid4.setAnimation(translate);
        aboutid5.setAnimation(translate);
        aboutid6.setAnimation(translate);
        aboutid7.setAnimation(translate);
        aboutid8.setAnimation(translate);
        aboutid9.setAnimation(translate);
        Toast.makeText(getContext(), "about", Toast.LENGTH_SHORT).show();
        return view;
    }
}