package com.example.todolist.Model;

public class Todo {

    int id;
    String todo;
    String status;
    int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {

        this.userId = userId;
    }

    public Todo(String todo, int userId, String status) {
        this.todo = todo;
        this.userId = userId;
        this.status = status;
    }
    public Todo(String todo, String status){
        this.todo = todo;
        this.status = status;
    }
    public Todo(String todo) {

        this.todo = todo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Todo(int id, String todo, String status) {
        this.id = id;
        this.todo = todo;
        this.status = status;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodo() {

        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

}
