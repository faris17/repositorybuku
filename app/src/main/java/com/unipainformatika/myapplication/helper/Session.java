package com.unipainformatika.myapplication.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    public static final String key_nim = "nim";
    public static final String key_level = "level";
    public static final String key_jurusan = "jurusan";

    SharedPreferences sesi;
    SharedPreferences.Editor Editor;

    public Session(Context context){
        sesi = context.getSharedPreferences(key_nim, Context.MODE_PRIVATE);
        Editor = sesi.edit();
    }

    public void saveSPString(String keySP, String value){
        Editor.putString(keySP, value);
        Editor.commit();
    }

    public void HapusSession(){
        Editor.clear();
        Editor.commit();
    }

    public void saveSPInt(String keySP, int value){
        Editor.putInt(keySP, value);
        Editor.commit();
    }


    public  String getSes_nim() {
        return sesi.getString(key_nim, "");
    }

    public  String getSes_level() {
        return sesi.getString(key_level, "");
    }

    public  String getSes_jurusan() {
        return sesi.getString(key_jurusan, "");
    }
}

