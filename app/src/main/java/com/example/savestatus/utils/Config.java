package com.example.savestatus.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.File;


public class Config {

    public static final String WhatsAppDirectoryPath = new File(Environment.getExternalStorageDirectory() +
        File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses" + File.separator).toString();

    public static final String WhatsAppSaveStatus = "/storage/emulated/0/WhatsAppStatusesDir/Media/WhatsApp/";

    public static final String GBWhatsAppDirectoryPath = "/storage/emulated/0/GWAActivity/Media/.Statuses/";
    public static final String GBWhatsAppSaveStatus = "/storage/emulated/0/WhatsAppStatusesDir/Media/GWAActivity/";

    public static final String WhatsAppBusinessDirectoryPath = "/storage/emulated/0/WhatsApp Business/Media/.Statuses/";
    public static final String WhatsAppBusinessSaveStatus = "/storage/emulated/0/WhatsAppStatusesDir/Media/WhatsAppBusiness/";

    public static final int count = 6;

    public static String getALLState(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("PREFRENCE", Context.MODE_PRIVATE);
        if (prefs.getString("ALL", "").length() > 0) {
            return prefs.getString("ALL", "");
        } else
            return "";
    }
}
