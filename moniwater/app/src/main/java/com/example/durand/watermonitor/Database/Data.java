package com.example.durand.watermonitor.Database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sharath Kumar on 4/14/2018.
 */

public class Data {
    private SharedPreferences pref;

    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    // Sharedpref file name
    private static final String PREF_NAME = "Data";

    private static final String TOKEN = "token";
    public Data(Context context){
        Context _context = context;
        int PRIVATE_MODE = 0;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setToken(String message){
        editor.putString(TOKEN, message);
        editor.commit();
    }

    public String getToken(){
        return pref.getString(TOKEN, "empty");
    }
}
