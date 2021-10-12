package com.example.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Todos";


    final String CREATE_TODOS_TABLE = "CREATE TABLE " + DBContract.Todos.TABLE_NAME + "("
            + DBContract.Todos.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DBContract.Todos.COLUMN_TASK + " TEXT,"
            + DBContract.Todos.COLUMN_FK_USER + " INTEGER,"
            + DBContract.Todos.COLUMN_STATUS + " TEXT,"
            + "FOREIGN KEY("+DBContract.Todos.COLUMN_FK_USER+") REFERENCES "+ DBContract.Register.TABLE_NAME+"("+DBContract.Register.COLUMN_ID+")"
            + ")";


    final String CREATE_AUTH_TABLE = "CREATE TABLE " + DBContract.Login.TABLE_NAME + "("
            + DBContract.Login.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DBContract.Login.COLUMN_EMAIL + " TEXT,"
            + DBContract.Login.COLUMN_PASSWORD + " TEXT,"
            + DBContract.Login.COLUMN_FK_USER + " INTEGER,"
            + "FOREIGN KEY("+DBContract.Login.COLUMN_FK_USER+") REFERENCES "+ DBContract.Register.TABLE_NAME+"("+DBContract.Register.COLUMN_ID+")"
            + ")";


    final String CRETAE_USER_TABLE = "CREATE TABLE " + DBContract.Register.TABLE_NAME + "("
            + DBContract.Register.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DBContract.Register.COLUMN_NAME + " TEXT,"
            + DBContract.Register.COLUMN_GENDER + " TEXT"
            + ")";



    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODOS_TABLE);
        db.execSQL(CREATE_AUTH_TABLE);
        db.execSQL(CRETAE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.Todos.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.Register.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.Login.TABLE_NAME);
        onCreate(db);
    }
}
