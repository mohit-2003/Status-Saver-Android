package com.example.savestatus.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.savestatus.R;
import com.example.savestatus.adapter.ImageViewAdapter;
import com.example.savestatus.adapter.VideoViewAdapter;
import com.example.savestatus.model.StatusModel;
import com.example.savestatus.utils.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

public class ImageViewerActivity extends AppCompatActivity {

    private int position;
    private boolean isSavedFragment;
    private ImageViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ViewPager2 viewPager = findViewById(R.id.imageViewPager);
        Intent intent = getIntent();
        if (intent != null) {
            isSavedFragment = intent.getBooleanExtra("isSaved", false);
            position = intent.getIntExtra("position", 0);
        }
        if (isSavedFragment){
            adapter = new ImageViewAdapter(Config.savedList, this);
        } else
            adapter = new ImageViewAdapter(Config.imgList, this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position, false);
    }
}
