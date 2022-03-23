package com.example.savestatus.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.savestatus.R;
import com.example.savestatus.model.ModelStatus;
import com.example.savestatus.utils.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

public class VideoViewAdapter extends RecyclerView.Adapter<VideoViewAdapter.viewHolder> {

    String atype = "", package_name = "", path;
    private int pos = 0;
    Context context;
    ViewPager2 viewPager;
    List<ModelStatus> videoList;

    public VideoViewAdapter(List<ModelStatus> videoList, Context context, ViewPager2 viewPager) {
        this.videoList = videoList;
        this.context = context;
        this.viewPager = viewPager;
        Log.d("mht", "onBindViewHolder: cons " +videoList.size() );
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.video_viewer_item,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        ModelStatus video = videoList.get(position);
        holder.videoView.setVideoPath(video.getFull_path());

        if (position == videoList.size()-1)
            Toast.makeText(context, "No more videos available...", Toast.LENGTH_SHORT).show();

        try {
            holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                        mp.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.videoView.setOnCompletionListener(MediaPlayer::start); // after complete

        switch (String.valueOf(video.getType())) {
            case "0":
                path = Config.WhatsAppSaveStatus;
                package_name = "com.whatsapp";
                break;

            case "1":
                path = Config.GBWhatsAppSaveStatus;
                package_name = "com.gbwhatsapp";
                break;

            case "2":
                path = Config.WhatsAppBusinessSaveStatus;
                package_name = "com.whatsapp.w4b";
                break;
        }

        holder.btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyFileOrDirectory(video.getFull_path(), path);
            }
        });

        holder.img_btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyFileOrDirectory(video.getFull_path(), path);
            }
        });

        holder.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (video.getFull_path().endsWith(".jpg")) {
                    shareVia("image/jpg", video.getFull_path());
                } else if (video.getFull_path().endsWith(".mp4")) {
                    shareVia("video/mp4", video.getFull_path());
                }

            }
        });

        holder.img_btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (video.getFull_path().endsWith(".jpg")) {
                    shareVia("image/jpg", video.getFull_path());
                } else if (video.getFull_path().endsWith(".mp4")) {
                    shareVia("video/mp4", video.getFull_path());
                }

            }
        });

        holder.btn_re_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (video.getFull_path().endsWith(".jpg")) {
                    shareViaWhatsApp("image/jpg", video.getFull_path(), package_name);
                } else if (video.getFull_path().endsWith(".mp4")) {
                    shareViaWhatsApp("video/mp4", video.getFull_path(), package_name);
                }



            }
        });
        holder.img_re_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (video.getFull_path().endsWith(".jpg")) {
                    shareViaWhatsApp("image/jpg", video.getFull_path(), package_name);
                } else if (video.getFull_path().endsWith(".mp4")) {
                    shareViaWhatsApp("video/mp4", video.getFull_path(), package_name);
                }


            }
        });

        if (atype.equals("0")) {
            holder.btn_download.setVisibility(View.GONE);
        } else if (atype.equals("1")) {
            holder.btn_download.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        VideoView videoView;
        public LinearLayout btn_download, btn_share, btn_re_post;
        public ImageButton img_btn_download, img_btn_share, img_re_post;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.video_view);
            btn_download = itemView.findViewById(R.id.btn_download);
            img_btn_download = itemView.findViewById(R.id.img_btn_download);
            btn_share = itemView.findViewById(R.id.btn_share);
            img_btn_share = itemView.findViewById(R.id.img_btn_share);
            btn_re_post = itemView.findViewById(R.id.btn_re_post);
            img_re_post = itemView.findViewById(R.id.img_re_post);

        }
    }

    public void copyFileOrDirectory(String srcDir, String dstDir) {

        try {
            File src = new File(srcDir);
            File dst = new File(dstDir, src.getName());

            if (src.isDirectory()) {

                String files[] = src.list();
                for (String file : files) {
                    String src1 = (new File(src, file).getPath());
                    String dst1 = dst.getPath();
                    copyFileOrDirectory(src1, dst1);

                }
            } else {
                copyFile(src, dst);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
            Toast.makeText(context, "Video Saved", Toast.LENGTH_SHORT).show();
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    public void shareVia(String type, String path) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType(type);
        File fileToShare = new File(path);
        Uri uri = Uri.fromFile(fileToShare);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }


    public void shareViaWhatsApp(String type, String path, String package_name) {

        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(package_name, PackageManager.GET_META_DATA);

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType(type);
            File fileToShare = new File(path);
            Uri uri = Uri.fromFile(fileToShare);
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
            sharingIntent.setPackage(package_name);//package name of the app
            context.startActivity(Intent.createChooser(sharingIntent, "Share via"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
