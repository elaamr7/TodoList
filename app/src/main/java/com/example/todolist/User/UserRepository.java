package com.example.todolist.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todolist.DBContract;
import com.example.todolist.DBProvider;
import com.example.todolist.Login.AuthManager;
import com.example.todolist.Model.Account;
import com.example.todolist.Model.User;


public class UserRepository {

    private static volatile UserRepository INSTANCE = null;
    DBProvider dbProvider;
    Context mContext;
    AuthManager authManager;


    public UserRepository(Context context){
        dbProvider = new DBProvider(context);
        mContext = context;
        authManager = new AuthManager(mContext.getSharedPreferences("Token",0));
    }

    public static UserRepository getINSTANCE(Context context) {
        if (INSTANCE != null) {
            synchronized (UserRepository.class) {
                INSTANCE = new UserRepository(context);
            }
        }
        return INSTANCE;
    }
    public void saveUser(User user) {
        ContentValues userValues = new ContentValues();
        userValues.put(DBContract.Register.COLUMN_NAME, user.getName());
        userValues.put(DBContract.Register.COLUMN_GENDER, user.getGender());
        dbProvider.insert(DBContract.Register.CONTENT_URI,userValues);
    }
    public int getLastUser(){
        Integer userId = 0;
        String[] column = {DBContract.Register.COLUMN_ID};
        Cursor cursor = dbProvider.query(DBContract.Register.CONTENT_URI,column,
                null,null,null,null,
                DBContract.Register.COLUMN_ID + " DESC","1");

        if (cursor.moveToFirst()) {
            do {
                userId = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userId;
    }
    public Boolean isAnyAccount(String email, String password){
        boolean flag = false;
        String selection = DBContract.Login.COLUMN_EMAIL + " = ? AND " +
                DBContract.Login.COLUMN_PASSWORD + " = ?";
        String[] args = {email,password};


        Cursor cursor = dbProvider.query(DBContract.Login.CONTENT_URI, null,selection,args,
                null,null,null,null);


        if (cursor.moveToFirst()) {
            flag = true;
            do {
                int auth_id = cursor.getInt(0);
                String emailParam = cursor.getString(1);
                String passwordParam = cursor.getString(2);
                int user_id = cursor.getInt(3);
                authManager.saveToken(new Account(auth_id,email,password,user_id));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return flag;
    }
    public void saveUserAccount(Account auth){
        ContentValues userValues = new ContentValues();
        userValues.put(DBContract.Login.COLUMN_EMAIL, auth.getEmail());
        userValues.put(DBContract.Login.COLUMN_PASSWORD, auth.getPassword());
        userValues.put(DBContract.Login.COLUMN_FK_USER, getLastUser());
        dbProvider.insert(DBContract.Login.CONTENT_URI,userValues);
    }
    public void setSharedPreferenceAuth(){
        Integer userId = 0;

        Cursor cursor = dbProvider.query(DBContract.Login.CONTENT_URI,null,
                null,null,null,null,
                DBContract.Login.COLUMN_ID + " DESC","1");

        if (cursor.moveToFirst()) {
            do {
                int auth_id = cursor.getInt(0);
                String email = cursor.getString(1);
                String password = cursor.getString(2);
                int user_id = cursor.getInt(3);
                authManager.saveToken(new Account(auth_id,email,password,user_id));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

}
