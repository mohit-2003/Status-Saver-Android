package com.example.savestatus.utils;

import android.os.Environment;

import com.example.savestatus.model.ModelStatus;

import java.io.File;
import java.util.ArrayList;

public class Config {

    public static final ArrayList<ModelStatus> videoList = new ArrayList<>();
//    public static final ArrayList<VideoDetails> imgList = new ArrayList<>();
//    public static final ArrayList<VideoDetails> savedList = new ArrayList<>();

    public static final String WhatsAppDirectoryPath = new File(Environment.getExternalStorageDirectory() +
        File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses" + File.separator).toString();

    public static final String WhatsAppSaveStatus = "/storage/emulated/0/WhatsAppStatusesDir/Media/WhatsApp/";

    public static final String GBWhatsAppDirectoryPath = "/storage/emulated/0/GWAActivity/Media/.Statuses/";
    public static final String GBWhatsAppSaveStatus = "/storage/emulated/0/WhatsAppStatusesDir/Media/GWAActivity/";

    public static final String WhatsAppBusinessDirectoryPath = "/storage/emulated/0/WhatsApp Business/Media/.Statuses/";
    public static final String WhatsAppBusinessSaveStatus = "/storage/emulated/0/WhatsAppStatusesDir/Media/WhatsAppBusiness/";
}
