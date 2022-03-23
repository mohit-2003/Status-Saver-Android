package com.example.savestatus.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.savestatus.R;
import com.example.savestatus.adapter.VideoViewAdapter;
import com.example.savestatus.model.ModelStatus;
import com.example.savestatus.utils.Config;

import java.util.List;

public class VIdeoViewerActivity extends AppCompatActivity {

    private final String TAG = "VideoViewer";
    public LinearLayout btn_download, btn_share, btn_re_post;
    public ImageButton img_btn_download, img_btn_share, img_re_post;
    String videoPath = "", path = "", atype = "", package_name = "";
    String type = "";
    VideoView video_view;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
//        video_view = findViewById(R.id.video_view);

//        Intent intent = getIntent();
//        if (intent != null) {
//            videoPath = intent.getStringExtra("video");
//            type = intent.getStringExtra("type");
//            atype = intent.getStringExtra("atype");
////            video_view.setVideoPath(videoPath);
////            video_view.start();
//        }

        ViewPager2 viewPager = findViewById(R.id.videoViewPager);
        List<ModelStatus> list = Config.videoList;
        Log.d(TAG, "onCreate: " + list.size());
        VideoViewAdapter adapter = new VideoViewAdapter(list, this, viewPager);
        viewPager.setAdapter(adapter);

//        try {
//            video_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    video_view.seekTo(position);
//                    if (position == 0) {
//                        video_view.start();
//                    } else {
//                        video_view.resume();
//
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        video_view.setOnCompletionListener(MediaPlayer::start);
//
//        switch (type) {
//            case "0":
//                path = Config.WhatsAppSaveStatus;
//                package_name = "com.whatsapp";
//                break;
//
//            case "1":
//                path = Config.GBWhatsAppSaveStatus;
//                package_name = "com.gbwhatsapp";
//                break;
//
//            case "2":
//                path = Config.WhatsAppBusinessSaveStatus;
//                package_name = "com.whatsapp.w4b";
//                break;
//        }
//
//        btn_download = findViewById(R.id.btn_download);
//        btn_download.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                   /* type == 0 (WhatsApp Normal)
//                    type == 1 (WhatsApp GB)
//                    type == 2 (WhatsApp Business)*/
//                copyFileOrDirectory(videoPath, path);
//
//
//            }
//        });
//
//        img_btn_download = findViewById(R.id.img_btn_download);
//        img_btn_download.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                   /* type == 0 (WhatsApp Normal)
//                    type == 1 (WhatsApp GB)
//                    type == 2 (WhatsApp Business)*/
//                copyFileOrDirectory(videoPath, path);
//
//
//
//            }
//        });
//
//        btn_share = findViewById(R.id.btn_share);
//        btn_share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (videoPath.endsWith(".jpg")) {
//                    shareVia("image/jpg", videoPath);
//                } else if (videoPath.endsWith(".mp4")) {
//                    shareVia("video/mp4", videoPath);
//                }
//
//
//
//            }
//        });
//        img_btn_share = findViewById(R.id.img_btn_share);
//        img_btn_share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (videoPath.endsWith(".jpg")) {
//                    shareVia("image/jpg", videoPath);
//                } else if (videoPath.endsWith(".mp4")) {
//                    shareVia("video/mp4", videoPath);
//                }
//
//
//
//            }
//        });
//
//        btn_re_post = findViewById(R.id.btn_re_post);
//        btn_re_post.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (videoPath.endsWith(".jpg")) {
//                    shareViaWhatsApp("image/jpg", videoPath, package_name);
//                } else if (videoPath.endsWith(".mp4")) {
//                    shareViaWhatsApp("video/mp4", videoPath, package_name);
//                }
//
//
//
//            }
//        });
//        img_re_post = findViewById(R.id.img_re_post);
//        img_re_post.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (videoPath.endsWith(".jpg")) {
//                    shareViaWhatsApp("image/jpg", videoPath, package_name);
//                } else if (videoPath.endsWith(".mp4")) {
//                    shareViaWhatsApp("video/mp4", videoPath, package_name);
//                }
//
//
//            }
//        });
//
//        if (atype.equals("0")) {
//            btn_download.setVisibility(View.GONE);
//        } else if (atype.equals("1")) {
//            btn_download.setVisibility(View.VISIBLE);
//        }
//
//
//
//
//    }
//
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt("Position", video_view.getCurrentPosition());
//        video_view.pause();
//
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        position = savedInstanceState.getInt("Position");
//        video_view.seekTo(position);
//
//    }
//
//    public void copyFileOrDirectory(String srcDir, String dstDir) {
//
//        try {
//            File src = new File(srcDir);
//            File dst = new File(dstDir, src.getName());
//
//            if (src.isDirectory()) {
//
//                String files[] = src.list();
//                int filesLength = files.length;
//                for (int i = 0; i < filesLength; i++) {
//                    String src1 = (new File(src, files[i]).getPath());
//                    String dst1 = dst.getPath();
//                    copyFileOrDirectory(src1, dst1);
//
//                }
//            } else {
//                copyFile(src, dst);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void copyFile(File sourceFile, File destFile) throws IOException {
//        if (!destFile.getParentFile().exists())
//            destFile.getParentFile().mkdirs();
//
//        if (!destFile.exists()) {
//            destFile.createNewFile();
//        }
//
//        FileChannel source = null;
//        FileChannel destination = null;
//
//        try {
//            source = new FileInputStream(sourceFile).getChannel();
//            destination = new FileOutputStream(destFile).getChannel();
//            destination.transferFrom(source, 0, source.size());
//            Toast.makeText(getApplicationContext(), "Video Saved", Toast.LENGTH_SHORT).show();
//        } finally {
//            if (source != null) {
//                source.close();
//            }
//            if (destination != null) {
//                destination.close();
//            }
//        }
//    }
//
//    public void shareVia(String type, String path) {
//        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//        sharingIntent.setType(type);
//        File fileToShare = new File(path);
//        Uri uri = Uri.fromFile(fileToShare);
//        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
//        startActivity(Intent.createChooser(sharingIntent, "Share via"));
//
//    }
//
//
//    public void shareViaWhatsApp(String type, String path, String package_name) {
//
///*
//        com.whatsapp.w4b
//        com.gbwhatsapp
//        com.whatsapp
//*/
//        PackageManager pm = getPackageManager();
//        try {
//            PackageInfo info = pm.getPackageInfo(package_name, PackageManager.GET_META_DATA);
//
//            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//            sharingIntent.setType(type);
//            File fileToShare = new File(path);
//            Uri uri = Uri.fromFile(fileToShare);
//            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
//            sharingIntent.setPackage(package_name);//package name of the app
//            startActivity(Intent.createChooser(sharingIntent, "Share via"));
//
//        } catch (PackageManager.NameNotFoundException e) {
//            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
//                    .show();
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        video_view.start();
//        super.onResume();
    }
}
