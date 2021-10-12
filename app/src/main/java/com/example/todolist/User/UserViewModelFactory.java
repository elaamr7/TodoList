package com.example.todolist.User;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.todolist.Login.LoginViewModel;
import com.example.todolist.Register.RegisterViewModel;

public class UserViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile  UserViewModelFactory INSTANCE=null;
    private final UserRepository userRepository;

    public  UserViewModelFactory(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public static  UserViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized ( UserViewModelFactory.class) {
                INSTANCE = new  UserViewModelFactory(UserRepository.getINSTANCE(context));
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
            return (T) new RegisterViewModel(userRepository);
        }
        else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(userRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}