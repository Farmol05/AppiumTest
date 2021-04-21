package com.example.e_event;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DataUser {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public DataUser(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public void open(){
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public List<User> getAllUser(){
        db = dbHelper.getReadableDatabase();
        List<User> users = new ArrayList<User>();

        //select all d
        String allUsers = "SELECT * FROM " + DBHelper.USER_TABLE;
        Cursor cursor = db.rawQuery(allUsers, null);

        //looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(0));
                user.setFullname(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                user.setPhone(cursor.getString(3));
                user.setPassword(cursor.getString(4));

                //adding event to the list
                users.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }

    public User getLoggedInUser(){
        db = dbHelper.getReadableDatabase();
        User loggedUser = null;

        String allUsers = "SELECT * FROM " + DBHelper.USER_TABLE +" WHERE " +dbHelper.COLUMN_USER_LOGGED + "= 1";
        Cursor cursor = db.rawQuery(allUsers, null);

        //looping through all rows and adding to list
        if (cursor.moveToFirst()) {
                loggedUser = new User();
                loggedUser.setId(cursor.getInt(0));
                loggedUser.setFullname(cursor.getString(1));
                loggedUser.setEmail(cursor.getString(2));
                loggedUser.setPhone(cursor.getString(3));
                loggedUser.setPassword(cursor.getString(4));
                loggedUser.setLogged(cursor.getInt(5));
        }
        cursor.close();
        return loggedUser;
    }

    public void logOneUser(int id, int log){
        db = dbHelper.getWritableDatabase();
        String queryString = "UPDATE "+ DBHelper.USER_TABLE
                +" SET " + DBHelper.COLUMN_USER_LOGGED +" = "+ log + " WHERE " + DBHelper.COLUMN_ID +" = " +id;
        db.execSQL(queryString);

    }

    public void addUser(User user){
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_USER_FULLNAME, user.getFullname());
        cv.put(dbHelper.COLUMN_USER_EMAIL, user.getEmail());
        cv.put(dbHelper.COLUMN_USER_PHONE, user.getPhone());
        cv.put(dbHelper.COLUMN_USER_PASSWORD, user.getPassword());
        cv.put(dbHelper.COLUMN_USER_LOGGED, user.getLogged());
        db.insert(dbHelper.USER_TABLE, null, cv);
    }

}
