package com.example.todolist.Todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.lifecycle.MutableLiveData;

import com.example.todolist.DBContract;
import com.example.todolist.DBProvider;
import com.example.todolist.Model.Todo;

import java.util.ArrayList;
import java.util.List;

public class TodoRepository {
    private static volatile TodoRepository INSTANCE = null;
    DBProvider dbProvider;
    Context mContext;

    public TodoRepository(Context context){
        dbProvider = new DBProvider(context);
        mContext = context;
    }

    public static TodoRepository getINSTANCE(Context context) {
        if (INSTANCE != null) {
            synchronized (TodoRepository.class) {
                INSTANCE = new TodoRepository(context);
            }
        }
        return INSTANCE;
    }

    public void saveTask(Todo task){
        ContentValues values = new ContentValues();
        values.put(DBContract.Todos.COLUMN_TASK, task.getTodo());
        values.put(DBContract.Todos.COLUMN_FK_USER, task.getUserId());
        values.put(DBContract.Todos.COLUMN_STATUS, task.getStatus());

        dbProvider.insert(DBContract.Todos.CONTENT_URI,values);
    }
    public MutableLiveData<List<Todo>> getAllTodoUser(int userId){
        MutableLiveData<List<Todo>> data = new MutableLiveData<>();
        List<Todo> todos = new ArrayList<>();
        String selection = DBContract.Todos.COLUMN_FK_USER + " = ?";
        String[] args = { String.valueOf(userId) };
        String[] column = { DBContract.Todos.COLUMN_ID, DBContract.Todos.COLUMN_TASK,DBContract.Todos.COLUMN_STATUS};
        Cursor cursor = dbProvider.query(DBContract.Todos.CONTENT_URI,column,
                selection,args,null,null, null,null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String task = cursor.getString(1);
                String status = cursor.getString(2);
                todos.add(new Todo(id, task,status));
            } while (cursor.moveToNext());
        }
        cursor.close();
        data.setValue(todos);
        return data;
    }
    public void deleteTask(Integer taskId){
        String selection = DBContract.Todos.COLUMN_ID+ " = ?";
        String[] args = { String.valueOf(taskId) };

        dbProvider.delete(DBContract.Todos.CONTENT_URI, selection, args);
    }

    public void updateTask(Integer taskId, String newTask){
        ContentValues values = new ContentValues();
        values.put(DBContract.Todos.COLUMN_TASK, newTask);
        String selection = DBContract.Todos.COLUMN_ID+ " = ?";
        String[] args = {String.valueOf(taskId)};

        dbProvider.update(DBContract.Todos.CONTENT_URI,values,selection,args);
    }
}
