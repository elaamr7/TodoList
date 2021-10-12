package com.example.todolist.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Model.Todo;
import com.example.todolist.R;
import com.example.todolist.databinding.GridTodoBinding;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoAdapterViewHolder> {

    List<Todo> todos = new ArrayList<>();
    OnTodoClickedListener listener;

    public TodoAdapter(List<Todo> todos){
        this.todos = todos;
    }

    public void setOnTaskClickedListener(OnTodoClickedListener mCallBack){
        listener = mCallBack;
    }

    @NonNull
    @Override
    public TodoAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GridTodoBinding binding = GridTodoBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new TodoAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapterViewHolder holder, int position) {
        Todo todo = todos.get(position);
        holder.bind(todo);
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public class TodoAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        GridTodoBinding binding;
        Todo todo;

        public TodoAdapterViewHolder(GridTodoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Todo todo){
            this.todo = todo;
            binding.tvTask.setText(this.todo.getTodo());
            binding.btnDelete.setOnClickListener(this);
            binding.btnEdit.setOnClickListener(this);
            binding.btnDone.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if(view.findViewById(R.id.btn_edit)==binding.btnEdit)listener.onUpdate(todo);
            else if(view.findViewById(R.id.btn_delete)==binding.btnDelete) listener.onDelete(todo);

        }
    }
    public void addNewTodo(String task){
        todos.add(new Todo(task,"todo"));
        notifySetDataChanged();
    }
    public void deleteTodo(Todo todo){
        todos.remove(todo);
        notifySetDataChanged();
    }
    public void updateTodo(Todo todo, String task){
        int index = todos.indexOf(todo);
        todo.setTodo(task);
        todos.set(index,todo);
        notifySetDataChanged();
    }
    public void notifySetDataChanged(){
        notifyDataSetChanged();
    }
    public interface OnTodoClickedListener {
        void onUpdate(Todo todo);
        void onDelete(Todo todo);
    }
}
