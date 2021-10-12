package com.example.todolist.SplashScreen;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.todolist.Login.AuthManager;

public class SplashScreenViewModel extends ViewModel {

    public boolean isLogin(Context context){
        AuthManager authManager = AuthManager.getInstance(context.getSharedPreferences("Token",0));
        return authManager.isLogin();
    }
}
