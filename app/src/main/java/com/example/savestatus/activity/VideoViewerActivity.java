package com.example.savestatus.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.savestatus.R;
import com.example.savestatus.adapter.VideoViewAdapter;
import com.example.savestatus.model.StatusModel;
import com.example.savestatus.utils.Config;

import java.util.List;

public class VideoViewerActivity extends AppCompatActivity {

    private int position;
    private boolean isSavedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        ViewPager2 viewPager = findViewById(R.id.videoViewPager);
        Intent intent = getIntent();
        if (intent != null) {
            isSavedFragment = intent.getBooleanExtra("isSaved", false);
            position = intent.getIntExtra("position", 0);
        }
        VideoViewAdapter adapter;
        if (isSavedFragment){
            adapter = new VideoViewAdapter(Config.savedList, this);
        } else
            adapter = new VideoViewAdapter(Config.videoList, this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position, false);
    }
}
