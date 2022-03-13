package com.example.savestatus.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.savestatus.R;
import com.example.savestatus.utils.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class ImageViewerActivity extends AppCompatActivity {

    private final String TAG = "ImageViewer";
    public LinearLayout btn_download, btn_share, btn_re_post;
    public ImageButton img_btn_download, img_btn_share, img_re_post;
    String image_path = "", path = "", atype = "", package_name = "";
    String type = "";
    ImageView imageView;
    String listenerValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        if (intent != null) {
            image_path = intent.getStringExtra("image");
            type = intent.getStringExtra("type");
            atype = intent.getStringExtra("atype");

            if (image_path != null) {
                Glide.with(this).load(image_path)
                        .into(imageView);
            }
        }

        if (type.equals("0")) {
            path = Config.WhatsAppSaveStatus;
            package_name = "com.whatsapp";

        } else if (type.equals("1")) {
            path = Config.GBWhatsAppSaveStatus;
            package_name = "com.gbwhatsapp";

        } else if (type.equals("2")) {
            path = Config.WhatsAppBusinessSaveStatus;
            package_name = "com.whatsapp.w4b";

        }


        btn_download = findViewById(R.id.btn_download);
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   /* type == 0 (WhatsApp Normal)
                    type == 1 (WhatsApp GB)
                    type == 2 (WhatsApp Business)*/
                copyFileOrDirectory(image_path, path);

            }
        });
        img_btn_download = findViewById(R.id.img_btn_download);
        img_btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   /* type == 0 (WhatsApp Normal)
                    type == 1 (WhatsApp GB)
                    type == 2 (WhatsApp Business)*/
                copyFileOrDirectory(image_path, path);

            }
        });


        btn_share = findViewById(R.id.btn_share);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (image_path.endsWith(".jpg")) {
                    shareVia("image/jpg", image_path);
                } else if (image_path.endsWith(".mp4")) {
                    shareVia("video/mp4", image_path);
                }


            }
        });
        img_btn_share = findViewById(R.id.img_btn_share);
        img_btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (image_path.endsWith(".jpg")) {
                    shareVia("image/jpg", image_path);
                } else if (image_path.endsWith(".mp4")) {
                    shareVia("video/mp4", image_path);
                }


            }
        });


        btn_re_post = findViewById(R.id.btn_re_post);
        btn_re_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (image_path.endsWith(".jpg")) {

                    shareViaWhatsApp("image/jpg", image_path, package_name);
                } else if (image_path.endsWith(".mp4")) {

                    shareViaWhatsApp("video/mp4", image_path, package_name);
                }


            }
        });
        img_re_post = findViewById(R.id.img_re_post);
        img_re_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (image_path.endsWith(".jpg")) {

                    shareViaWhatsApp("image/jpg", image_path, package_name);
                } else if (image_path.endsWith(".mp4")) {

                    shareViaWhatsApp("video/mp4", image_path, package_name);
                }

            }
        });

        if (atype.equals("0")) {
            btn_download.setVisibility(View.GONE);
        } else if (atype.equals("1")) {
            btn_download.setVisibility(View.VISIBLE);
        }



    }

    public void copyFileOrDirectory(String srcDir, String dstDir) {

        try {
            File src = new File(srcDir);
            File dst = new File(dstDir, src.getName());

            if (src.isDirectory()) {

                String files[] = src.list();
                int filesLength = files.length;
                for (int i = 0; i < filesLength; i++) {
                    String src1 = (new File(src, files[i]).getPath());
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
            Toast.makeText(getApplicationContext(), "Picture Saved", Toast.LENGTH_SHORT).show();
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
/*
        sharingIntent.setPackage("com.whatsapp");//package name of the app
*/
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }

    public void shareViaWhatsApp(String type, String path, String package_name) {

/*
        com.whatsapp.w4b
        com.gbwhatsapp
        com.whatsapp
*/
        PackageManager pm = getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(package_name, PackageManager.GET_META_DATA);
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType(type);
            File fileToShare = new File(path);
            Uri uri = Uri.fromFile(fileToShare);
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
            sharingIntent.setPackage(package_name);//package name of the app
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }
    }

}
