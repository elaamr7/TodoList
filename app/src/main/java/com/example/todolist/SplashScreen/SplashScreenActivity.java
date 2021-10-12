package com.example.todolist.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.example.todolist.Login.LoginActivity;
import com.example.todolist.R;
import com.example.todolist.Todo.TodoActivity;

public class SplashScreenActivity extends AppCompatActivity {

    SplashScreenViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.
                NewInstanceFactory()).get(SplashScreenViewModel.class);

        if(viewModel.isLogin(this)){
            startActivity(new Intent(this, TodoActivity.class));
        }
        else{
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}