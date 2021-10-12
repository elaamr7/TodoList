package com.example.todolist.Model;

public class Account {

    String email;
    String password;
    int userId;
    int id;

    public Account(String email, String password){
        this.email = email;
        this.password = password;
    }
    public Account(Integer id, String email, String password, Integer user_id){
        this.id = id;
        this.email = email;
        this.password = password;
        this.userId = user_id;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
