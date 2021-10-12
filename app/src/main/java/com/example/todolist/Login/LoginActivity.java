package com.example.todolist.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.todolist.Main.MainActivity;
import com.example.todolist.R;
import com.example.todolist.Register.RegisterActivity;
import com.example.todolist.User.UserRepository;
import com.example.todolist.User.UserViewModelFactory;
import com.example.todolist.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,
        LoginViewModel.LoginListener {

    ActivityLoginBinding binding;
    LoginViewModel viewModel;
    AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    private void init(){
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnLogin.setOnClickListener(this);
        UserViewModelFactory factory = new UserViewModelFactory(new UserRepository(this));
        viewModel = new ViewModelProvider(this,factory).get(LoginViewModel.class);
        authManager = AuthManager.getInstance(this.getSharedPreferences("Token",0));
        viewModel.setListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.findViewById(R.id.btn_login) == binding.btnLogin){
            String email = binding.etEmail.getText().toString();
            String password = binding.etPassword.getText().toString();

            if(viewModel.isValid(email,password)){
                startActivity(new Intent(this, MainActivity.class));
            }
        }
        else if(view.findViewById(R.id.register)==binding.register){
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }
    @Override
    public void onWrongEmail(Boolean wrongFormat, Boolean isEmpty) {
        if(isEmpty)binding.etEmail.setError("Emailmu harus diisi");
        else if(wrongFormat) binding.etEmail.setError("Format emailmu salah");
    }

    @Override
    public void onWrongPassword(Boolean wrongFormat , Boolean isEmpty) {
        if(isEmpty)binding.etPassword.setError("Passwordmu harus diisi");
        else if(wrongFormat)binding.etPassword.setError("Format passwordmu salah");
    }

    @Override
    public void onNoAccount(Boolean noAccount) {
        binding.etPassword.setError("Email atau passwordmu salah");
    }
}