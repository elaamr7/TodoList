package com.example.todolist.Login;

import android.util.Patterns;
import androidx.lifecycle.ViewModel;
import com.example.todolist.User.UserRepository;

public class LoginViewModel extends ViewModel {

    UserRepository userRepository;
    LoginListener listener;

    public LoginViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void setListener(LoginListener listener){
        this.listener = listener;
    }
    public Boolean isValid(String email, String password){
        if(!isCorrectEmail(email) || !isCorrectPassword(password) || !isAnyAccount(email,password))
            return false;
        return true;
    }
    private Boolean isAnyAccount(String email, String password){
        if(!userRepository.isAnyAccount(email, password)){
            listener.onNoAccount(true);
            return false;
        }
        return true;
    }
    private Boolean isCorrectEmail(String email){
        Boolean isEmpty=false;
        Boolean wrongFormat=false;

        if(email.isEmpty()){
            isEmpty = true;
            listener.onWrongEmail(wrongFormat,isEmpty);
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            wrongFormat = true;
            listener.onWrongEmail(wrongFormat,isEmpty);
            return false;
        }
        return true;
    }
    private Boolean isCorrectPassword(String password){
        Boolean isEmpty=false;
        Boolean wrongFormat=false;

        if(password.isEmpty()){
            isEmpty = true;
            listener.onWrongPassword(wrongFormat,isEmpty);
            return false;
        }
        return true;
    }

    public interface LoginListener{
        void onWrongEmail(Boolean wrongFormat , Boolean isEmpty);
        void onWrongPassword(Boolean wrongFormat , Boolean isEmpty);
        void onNoAccount(Boolean noAccount);
    }
}
