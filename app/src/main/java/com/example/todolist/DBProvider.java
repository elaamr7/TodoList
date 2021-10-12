package com.example.todolist;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class DBProvider{

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DBHelper mDBHelper;
    private Context mContext;

    static final int USER = 100;
    static final int AUTH = 200;
    static final int TASK = 300;

    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DBContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, DBContract.PATH_USER, USER);
        matcher.addURI(authority, DBContract.PATH_AUTH, AUTH);
        matcher.addURI(authority, DBContract.PATH_TASK, TASK);

        return matcher;
    }

    public DBProvider(Context context) {
        mDBHelper = new DBHelper(context);
        mContext = context;
    }

    public int getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        return match;
    }

    public Cursor query(Uri uri, String[] columns, String selection, String[] selectionArgs,
                        String groupBy, String having, String orderBy, String limit) {

        Cursor retCursor=null;

        try {
            switch (sUriMatcher.match(uri)) {
                case USER: {
                    retCursor = mDBHelper.getReadableDatabase().query(
                            false,
                            DBContract.Register.TABLE_NAME,
                            columns,
                            selection,
                            selectionArgs,
                            groupBy,
                            having,
                            orderBy,
                            limit,
                            null
                    );
                    break;
                }
                case AUTH: {
                    retCursor = mDBHelper.getReadableDatabase().query(
                            false,
                            DBContract.Login.TABLE_NAME,
                            columns,
                            selection,
                            selectionArgs,
                            groupBy,
                            having,
                            orderBy,
                            limit,
                            null
                    );
                    break;
                }
                case TASK: {
                    retCursor = mDBHelper.getReadableDatabase().query(
                            false,
                            DBContract.Todos.TABLE_NAME,
                            columns,
                            selection,
                            selectionArgs,
                            groupBy,
                            having,
                            orderBy,
                            limit,
                            null
                    );
                    break;
                }
                default:
                    throw new UnsupportedOperationException("Uri unknown: " + uri);
            }
        } catch (Exception ex) {
            Log.e("Cursor", ex.toString());
        }
        return retCursor;
    }

    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        try {

            switch (match) {
                case USER: {
                    long _id = db.insert(DBContract.Register.TABLE_NAME, null, values);
                    if (_id > 0)
                        returnUri = DBContract.Register.buildUri(_id);
                    else
                        throw new android.database.SQLException("Error at inserting row in " + uri);
                    break;
                }
                case AUTH: {
                    long _id = db.insert(DBContract.Login.TABLE_NAME, null, values);
                    if (_id > 0)
                        returnUri = DBContract.Login.buildUri(_id);
                    else
                        throw new android.database.SQLException("Error at inserting row in " + uri);
                    break;
                }
                case TASK: {
                    long _id = db.insert(DBContract.Todos.TABLE_NAME, null, values);
                    if (_id > 0)
                        returnUri = DBContract.Todos.buildUri(_id);
                    else
                        throw new android.database.SQLException("Error at inserting row in " + uri);
                    break;
                }
                default:
                    throw new UnsupportedOperationException("Uri unknown: " + uri);
            }
            mContext.getContentResolver().notifyChange(uri, null);
            return returnUri;
        } catch (Exception ex) {
            Log.e("Insert", ex.toString());
            db.close();
        } finally {
            db.close();
        }
        return null;
    }


    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int deletedRows;
        if (null == selection) selection = "1";
        try {
            switch (match) {
                case USER:
                    deletedRows = db.delete(
                            DBContract.Register.TABLE_NAME, selection, selectionArgs);
                    break;
                case TASK:
                    deletedRows = db.delete(
                            DBContract.Todos.TABLE_NAME, selection, selectionArgs);
                    break;
                default:
                    throw new UnsupportedOperationException("Uri unknown: " + uri);
            }
            if (deletedRows != 0) {
                mContext.getContentResolver().notifyChange(uri, null);
            }
            return deletedRows;
        } catch (Exception ex) {
            Log.e("Insert", ex.toString());
        } finally {
            db.close();
        }
        return 0;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int updatedRows;
        try {
            switch (match) {
                case USER:
                    updatedRows = db.update(DBContract.Register.TABLE_NAME, values, selection, selectionArgs);
                    break;
                case AUTH:
                    updatedRows = db.update(DBContract.Register.TABLE_NAME, values, selection, selectionArgs);
                    break;
                case TASK:
                    updatedRows = db.update(DBContract.Todos.TABLE_NAME, values, selection, selectionArgs);
                    break;
                default:
                    throw new UnsupportedOperationException("Uri unknown: " + uri);
            }
            if (updatedRows != 0) {
                mContext.getContentResolver().notifyChange(uri, null);
            }
            return updatedRows;
        } catch (Exception ex) {
            Log.e("Update", ex.toString());
        } finally {
            db.close();
        }
        return -1;
    }

}