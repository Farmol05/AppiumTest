package com.example.e_event;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DataEvent {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public DataEvent(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public void open(){
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void addEvent(Event event){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_EVENT_TITLE, event.getTitle());
        cv.put(DBHelper.COLUMN_EVENT_ORGANIZER, event.getOrganizer());
        cv.put(DBHelper.COLUMN_EVENT_DATE_START, event.getDate_started());
        cv.put(DBHelper.COLUMN_EVENT_TICKET, event.getTicket());
        cv.put(DBHelper.COLUMN_EVENT_DESC, event.getDesc());
        cv.put(DBHelper.COLUMN_EVENT_POSTER, event.getPoster());
        cv.put(DBHelper.COLUMN_EVENT_ACTIVE, event.getActive());
        cv.put(DBHelper.COLUMN_EVENT_OWNER_ID, event.getOwnerID());
        db.insert(DBHelper.EVENT_TABLE, null, cv);
    }

    public List<Event> getAllEvent(){
        db = dbHelper.getReadableDatabase();
        List<Event> events = new ArrayList<Event>();

        //select all d
        String allEvents = "SELECT * FROM " + DBHelper.EVENT_TABLE;
        Cursor cursor = db.rawQuery(allEvents, null);

        //looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(cursor.getInt(0));
                event.setTitle(cursor.getString(1));
                event.setOrganizer(cursor.getString(2));
                event.setDate_started(cursor.getString(3));
                event.setTicket(cursor.getInt(4));
                event.setDesc(cursor.getString(5));
                event.setPoster(cursor.getBlob(6));
                event.setActive(cursor.getInt(7));
                event.setOwnerID(cursor.getInt(8));

                //adding event to the list
                events.add(event);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return events;
    }

    public List<Event> getAllEvent(int id){
        db = dbHelper.getReadableDatabase();
        List<Event> events = new ArrayList<Event>();

        //select all d
        String allEvents = "SELECT * FROM " + DBHelper.EVENT_TABLE +" WHERE "+ DBHelper.COLUMN_EVENT_OWNER_ID +"=" + id;
        Cursor cursor = db.rawQuery(allEvents, null);

        //looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(cursor.getInt(0));
                event.setTitle(cursor.getString(1));
                event.setOrganizer(cursor.getString(2));
                event.setDate_started(cursor.getString(3));
                event.setTicket(cursor.getInt(4));
                event.setDesc(cursor.getString(5));
                event.setPoster(cursor.getBlob(6));
                event.setActive(cursor.getInt(7));
                event.setOwnerID(cursor.getInt(8));

                //adding event to the list
                events.add(event);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return events;
    }

    public Event getOneEvent(int id){
        db = dbHelper.getReadableDatabase();
        Event event = null;

        //select all d
        String allEvents = "SELECT * FROM " + DBHelper.EVENT_TABLE +" WHERE "+ DBHelper.COLUMN_ID +"=" + id;
        Cursor cursor = db.rawQuery(allEvents, null);

        //looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                event = new Event();
                event.setId(cursor.getInt(0));
                event.setTitle(cursor.getString(1));
                event.setOrganizer(cursor.getString(2));
                event.setDate_started(cursor.getString(3));
                event.setTicket(cursor.getInt(4));
                event.setDesc(cursor.getString(5));
                event.setPoster(cursor.getBlob(6));
                event.setPoster(cursor.getBlob(6));
                event.setActive(cursor.getInt(7));
                event.setOwnerID(cursor.getInt(8));

                //adding event to the list
            } while (cursor.moveToNext());
        }

        cursor.close();
        return event;
    }

    public void deleteOne(int ID) {
        String syntax = "DELETE FROM " + DBHelper.EVENT_TABLE + " WHERE " + DBHelper.COLUMN_ID + " = "+ ID;
        db.execSQL(syntax);
    }

    public void updateOne(int ID, String title, String org, String date, int ticket, String desc, byte[] poster, int active, int userID ){
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_EVENT_TITLE , title);
        cv.put(dbHelper.COLUMN_EVENT_ORGANIZER , org);
        cv.put(dbHelper.COLUMN_EVENT_DATE_START , date);
        cv.put(dbHelper.COLUMN_EVENT_TICKET , ticket);
        cv.put(dbHelper.COLUMN_EVENT_DESC , desc);
        cv.put(dbHelper.COLUMN_EVENT_POSTER , poster);
        cv.put(dbHelper.COLUMN_EVENT_ACTIVE , active);
        cv.put(dbHelper.COLUMN_EVENT_OWNER_ID , userID);
        db.update(dbHelper.EVENT_TABLE, cv, dbHelper.COLUMN_ID +" = "+ID,null);
    }

}
