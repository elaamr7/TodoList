package com.example.todolist.Register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist.Model.Account;
import com.example.todolist.Model.User;
import com.example.todolist.User.UserRepository;

public class RegisterViewModel extends ViewModel {

    UserRepository userRepository;

    public RegisterViewModel(UserRepository userRepository){
     this.userRepository = userRepository;
    }
    public void doRegist(User user, Account account){
        userRepository.saveUser(user);
        userRepository.saveUserAccount(account);
        userRepository.setSharedPreferenceAuth();
    }
    public LiveData<Boolean> validation(){
        return null;
    }
}
