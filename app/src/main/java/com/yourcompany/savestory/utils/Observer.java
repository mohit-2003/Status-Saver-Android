package com.yourcompany.savestory.utils;

import android.content.Context;

public interface Observer {
    void update(final String value, Context context);
}