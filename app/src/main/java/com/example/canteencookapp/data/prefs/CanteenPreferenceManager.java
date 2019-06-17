package com.example.canteencookapp.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class CanteenPreferenceManager implements PreferenceManager {

    /* shared Preference and its editor */
    private static final String PREFERENCE_NAME = "Canteen Data";
    private static CanteenPreferenceManager canteenPreferenceManager = null;
    /* preference mode - private , only be accessed by this application */
    private int PRIVATE_MODE = 0;


    /**
     * Singleton class which will be accessed through this instance
     *
     * @return Singleton class instance
     */
    public static CanteenPreferenceManager getInstance() {
        if (canteenPreferenceManager == null) {
            canteenPreferenceManager = new CanteenPreferenceManager();
        }
        return canteenPreferenceManager;
    }

    /**
     * To Clear all user related data.
     */
    void clearDataPreference(Context context) {
        editPreferences(context).clear().apply();
    }

    @Override
    public SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE);
    }

    @Override
    public SharedPreferences.Editor editPreferences(Context context) {
        return getPreferences(context).edit();
    }

    @Override
    public void setString(Context context, String key, String value) {
        editPreferences(context).putString(key, value).commit();
    }

    @Override
    public String getString(Context context, String key, String def) {
        return getPreferences(context).getString(key, def);
    }

    @Override
    public void setBoolean(Context context, String key, boolean value) {
        editPreferences(context).putBoolean(key, value).commit();
    }

    @Override
    public boolean getBoolean(Context context, String key, boolean def) {
        return getPreferences(context).getBoolean(key, def);
    }

    @Override
    public void setInteger(Context context, String key, int value) {
        editPreferences(context).putInt(key, value).commit();
    }

    @Override
    public int getInteger(Context context, String key, int def) {
        return getPreferences(context).getInt(key, def);
    }

    @Override
    public void setFloat(Context context, String key, float value) {
        editPreferences(context).putFloat(key, value).commit();
    }

    @Override
    public float getFloat(Context context, String key, float def) {
        return getPreferences(context).getFloat(key, def);
    }


}
