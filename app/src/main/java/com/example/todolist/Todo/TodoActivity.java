package com.example.todolist.Todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.todolist.Adapter.TodoAdapter;
import com.example.todolist.Login.AuthManager;
import com.example.todolist.Login.LoginActivity;
import com.example.todolist.Model.Todo;
import com.example.todolist.R;
import com.example.todolist.databinding.ActivityTodoBinding;
import com.example.todolist.databinding.GridTodoBinding;


public class TodoActivity extends AppCompatActivity implements
        View.OnClickListener, TodoAdapter.OnTodoClickedListener {

    ActivityTodoBinding binding;
    public  static TodoViewModel viewModel;
    public static AuthManager authManager;
    private TodoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setTodoList();

    }
    public void init(){
        binding = ActivityTodoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.fabAdd.setOnClickListener(this);
        TodoViewModelFactory factory = new TodoViewModelFactory(new TodoRepository(this));
        viewModel = new  ViewModelProvider(this, factory).get(TodoViewModel.class);
        authManager = AuthManager.getInstance(this.getSharedPreferences("Token",0));
        binding.btnLogout.setOnClickListener(this);
    }
    public void setTodoList(){
        RecyclerView rvTodoList = binding.rvTodoList;
        viewModel.getAllTaskUser(authManager.getAuth()).observe(this, todos->{
            adapter = new TodoAdapter(todos);
            rvTodoList.setLayoutManager(new LinearLayoutManager(this));
            rvTodoList.setAdapter(adapter);
            adapter.setOnTaskClickedListener(this);
        });
    }
    @Override
    public void onClick(View view) {
        if(view.findViewById(R.id.fab_add)==binding.fabAdd) {
            AddTaskDialogFragment dialog = new AddTaskDialogFragment();
            dialog.show(getSupportFragmentManager(), "dialog");
            dialog.setOnAddTaskDialogListener(new AddTaskDialogFragment.DialogListener() {
                @Override
                public void addToList(String task) {
                    viewModel.save(task, authManager.getAuth());
                    adapter.addNewTodo(task);
                }
            });
        }
        else if(view.findViewById(R.id.btn_logout)==binding.btnLogout){
            authManager.deleteToken();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public void onUpdate(Todo todo) {
        UpdateTaskDialogFragment dialog = new UpdateTaskDialogFragment();
        dialog.show(getSupportFragmentManager(), "dialog");
        dialog.setOnUpdateDialogListener(new UpdateTaskDialogFragment.UpdateDialogListener() {
            @Override
            public String sendData() {
                return todo.getTodo();
            }

            @Override
            public void updateData(String task) {
                viewModel.update(todo.getId(),task);
                adapter.updateTodo(todo,task);
            }
        });
    }

    @Override
    public void onDelete(Todo todo) {
        DeleteTaskDialogFragment dialog = new DeleteTaskDialogFragment();
        dialog.show(getSupportFragmentManager(),"dialog");
        dialog.setOnDeleteTaskDialogListener(new DeleteTaskDialogFragment.DialogListener() {
            @Override
            public void delete() {
                viewModel.delete(todo.getId());
                adapter.deleteTodo(todo);
            }
        });
    }
}