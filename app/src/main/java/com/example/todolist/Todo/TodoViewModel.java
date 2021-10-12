package com.example.todolist.Todo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist.Model.Todo;

import java.util.List;

public class TodoViewModel extends ViewModel {

    TodoRepository todoRepository;
    private static volatile TodoViewModel INSTANCE=null;


    public TodoViewModel(TodoRepository todoRepository){
        this.todoRepository = todoRepository ;
    }

    public static TodoViewModel getInstance(TodoRepository todoRepository) {
        if (INSTANCE == null) {
            synchronized (TodoViewModelFactory.class) {
                INSTANCE = new TodoViewModel(todoRepository);
            }
        }
        return INSTANCE;
    }

    public LiveData<List<Todo>> getAllTaskUser(Integer userId){
        return todoRepository.getAllTodoUser(userId);
    }
    public  void save(String newTask, Integer userId){
        Todo task = new Todo(newTask,userId,"todo");
        todoRepository.saveTask(task);
    }
    public void delete(Integer taskId){

        todoRepository.deleteTask(taskId);
    }
    public void update(Integer taskId, String newTask){
        todoRepository.updateTask(taskId,newTask);
    }
}