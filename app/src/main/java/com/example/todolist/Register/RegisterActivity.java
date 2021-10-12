package com.example.todolist.Register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.todolist.Model.Account;
import com.example.todolist.Model.User;
import com.example.todolist.User.UserRepository;
import com.example.todolist.User.UserViewModelFactory;
import com.example.todolist.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    ActivityRegisterBinding binding;
    RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        init();
    }
    public void init(){
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        UserViewModelFactory factory = new UserViewModelFactory(new UserRepository(this));
        registerViewModel = new ViewModelProvider(this, factory).get(RegisterViewModel.class);
        binding.buttonRegist.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        String name = binding.etName.getText().toString();
        String gender = binding.etGender.getText().toString();
        String email = binding.etRegistrasiEmail.getText().toString();
        String password = binding.etRegistrasiPassword.getText().toString();

        registerViewModel.doRegist(new User(name,gender), new Account(email,password));
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }


}