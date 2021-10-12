package com.example.todolist.Login;

import android.content.SharedPreferences;

import com.example.todolist.Model.Account;

public class AuthManager {
    static private SharedPreferences prefs;
    static private SharedPreferences.Editor editor;

    public static AuthManager INSTANCE = null;

    public AuthManager(SharedPreferences prefs){
        this.prefs = prefs;
        this.editor = prefs.edit();
    }

    public static synchronized AuthManager getInstance(SharedPreferences prefs){
        if(INSTANCE == null){
            INSTANCE = new AuthManager(prefs);
        }
        return INSTANCE;
    }

    public void saveToken(Account auth){
        editor.putString("EMAIL", auth.getEmail()).commit();
        editor.putString("PASSWORD", auth.getPassword()).commit();
        editor.putInt("USER_ID", auth.getUserId()).commit();
        editor.putInt("AUTH_ID", auth.getId()).commit();
        editor.apply();
    }

    public boolean isLogin(){
        return (INSTANCE.getAuth()!=0);
    }

    public void deleteToken(){
        editor.remove("EMAIL").commit();
        editor.remove("PASSWORD").commit();
        editor.remove("USER_ID").commit();
        editor.remove("AUTH_ID").commit();
        editor.apply();
    }

    public Integer getAuth(){
        return prefs.getInt("AUTH_ID",0);
    }

}
