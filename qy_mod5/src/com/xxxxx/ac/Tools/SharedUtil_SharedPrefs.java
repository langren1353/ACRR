package com.xxxxx.ac.Tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedUtil_SharedPrefs {  
    public String SETTING = "Setting";
    public  Context context;
    public SharedUtil_SharedPrefs(Context context, String KEY){
    	this.context = context;
    	this.SETTING = KEY;
    }
    public void putValue(String key, int value) {  
         Editor sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE).edit();  
         sp.putInt(key, value);  
         sp.commit();  
    }  
    public  void putValue(String key, boolean value) {  
         Editor sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE).edit();  
         sp.putBoolean(key, value);  
         sp.commit();  
    }  
    public  void putValue(String key, String value) {  
         Editor sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE).edit();  
         sp.putString(key, value);  
         sp.commit();  
    }  
    public  int getValue(String key, int defValue) {  
        SharedPreferences sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);  
        int value = sp.getInt(key, defValue);  
        return value;  
    }  
    public  boolean getValue(String key, boolean defValue) {  
        SharedPreferences sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);  
        boolean value = sp.getBoolean(key, defValue);  
        return value;  
    }  
    public  String getValue(String key, String defValue) {  
        SharedPreferences sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);  
        String value = sp.getString(key, defValue);  
        return value;  
    }  
}  
