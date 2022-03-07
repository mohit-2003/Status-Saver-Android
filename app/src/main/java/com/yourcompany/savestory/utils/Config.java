package com.yourcompany.savestory.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class Config {
	
	
	//Downloaded From :	https://nulledsourcecode.com/
	//Contact us for reskin and making custom android app: https://nulledsourcecode.com/submit-ticket/

	
    public static final String WhatsAppDirectoryPath = "/storage/emulated/0/WhatsApp/Media/.Statuses/";
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
