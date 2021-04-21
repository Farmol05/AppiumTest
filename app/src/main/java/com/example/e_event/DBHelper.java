package com.example.e_event;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USER_FULLNAME = "USER_FULLNAME";
    public static final String COLUMN_USER_EMAIL = "USER_EMAIL";
    public static final String COLUMN_USER_PHONE = "USER_PHONE";
    public static final String COLUMN_USER_PASSWORD = "USER_PASSWORD";
    public static final String COLUMN_USER_LOGGED = "USER_LOGGED";

    public static final String EVENT_TABLE = "EVENT_TABLE";
    public static final String COLUMN_EVENT_TITLE = "EVENT_TITLE";
    public static final String COLUMN_EVENT_ORGANIZER = "EVENT_ORGANIZER";
    public static final String COLUMN_EVENT_DATE_START = "EVENT_DATE_START";
    public static final String COLUMN_EVENT_TICKET = "EVENT_TICKET";
    public static final String COLUMN_EVENT_DESC = "EVENT_DESC";
    public static final String COLUMN_EVENT_POSTER = "EVENT_POSTER";
    public static final String COLUMN_EVENT_ACTIVE = "EVENT_ACTIVE";
    public static final String COLUMN_EVENT_OWNER_ID = "EVENT_OWNER_ID";

    private String createUserTable(){
        String syntax = "CREATE TABLE USER_TABLE (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "USER_FULLNAME TEXT," +
                "USER_EMAIL TEXT," +
                "USER_PHONE TEXT," +
                "USER_PASSWORD TEXT," +
                "USER_LOGGED INT)" ;
        return syntax;
    }

    private String createEventTable(){
        String syntax = "CREATE TABLE EVENT_TABLE (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "EVENT_TITLE TEXT," +
                "EVENT_ORGANIZER TEXT," +
                "EVENT_DATE_START TEXT,"+
                "EVENT_TICKET INT," +
                "EVENT_DESC TEXT," +
                "EVENT_POSTER BLOB," +
                "EVENT_ACTIVE BOOL," +
                "EVENT_OWNER_ID INTEGER)" ;
        return syntax;
    }

    public DBHelper(Context context) {
        super(context, "event.db", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createUserTable());
        db.execSQL(createEventTable());

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
