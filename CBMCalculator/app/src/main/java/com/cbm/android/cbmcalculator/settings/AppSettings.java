package com.cbm.android.cbmcalculator.settings;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.ref.WeakReference;

import com.cbm.android.cbmcalculator.R;

public class AppSettings {
    public SharedPreferences sp;
    private WeakReference<Context> wrc;

    public AppSettings(Context c) {
        wrc = new WeakReference<>(c);
        sp = c.getSharedPreferences(c.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public SharedPreferences getSp() {return sp;}

    public static SharedPreferences getSp(Context c) {
        return new AppSettings(c).getSp();
    }

    public void saveBODMAS(boolean value) {
        sp.edit().putBoolean("BODMAS", value).apply();
    }

    public boolean getBODMAS() {return sp.getBoolean("BODMAS", false);}

    public void saveStandBy(boolean value) {
        sp.edit().putBoolean("StandBy", value).apply();
    }

    public boolean getStandBy() {return sp.getBoolean("StandBy", false);}

}
