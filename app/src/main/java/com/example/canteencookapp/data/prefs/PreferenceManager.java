package com.example.canteencookapp.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Type;

/**
 * Interface to save values in shared preferences and also for retrieve values from shared preferences
 */
public interface PreferenceManager {

    SharedPreferences getPreferences(Context context);

    SharedPreferences.Editor editPreferences(Context context);

    void setString(Context context, String key, String value);

    String getString(Context context, String key, String defVal);

    void setBoolean(Context context, String key, boolean value);

    boolean getBoolean(Context context, String key, boolean defVal);

    void setInteger(Context context, String key, int value);

    int getInteger(Context context, String key, int defVal);

    void setFloat(Context context, String key, float value);

    float getFloat(Context context, String key, float defVal);
}
