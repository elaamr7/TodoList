package com.example.todolist.Todo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TodoViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile TodoViewModelFactory INSTANCE=null;
    private final TodoRepository todoRepository;

    public TodoViewModelFactory(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        if (modelClass.isAssignableFrom(TodoViewModel.class)) {
            return (T) new TodoViewModel(todoRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
