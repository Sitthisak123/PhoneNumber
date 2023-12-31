package com.cs.sitthisak.phonenumber.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import com.cs.sitthisak.phonenumber.model.Contact;

public class PhoneNumberDBHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "phonenumberdb.sqlite";
    private static int DATABASE_VERSION = 2;
    private String TABLE_NAME = "contact";
    private String COLUMN_ID = "id";
    private String COLUMN_NAME = "name";
    private String COLUMN_MOBILE = "mobile";
    private Context context;

    public PhoneNumberDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME
                + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_MOBILE + " TEXT NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);

        onCreate(db);
    }

    public boolean insert(Contact contact){
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contact.getName());
        values.put(COLUMN_MOBILE, contact.getMobile());

        long row = db.insert(TABLE_NAME, null, values);
        if (row>0)
            result = true;
        db.close();
        return result;
    }
    public boolean update(Contact contact){
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contact.getName());
        values.put(COLUMN_MOBILE, contact.getMobile());

        int row = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(contact.getId())});
        if (row>0)
            result = true;
        db.close();
        return result;
    }
    public boolean delete(int pk){
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        int row = db.delete(TABLE_NAME, COLUMN_ID + " = ?",new String[]{String.valueOf(pk)});
        if (row>0)
            result = true;
        db.close();
        return result;
    }
    public void delete(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM " + TABLE_NAME;
        db.execSQL(sql);
        db.close();
    }

    public List<Contact> select(){
        List<Contact> contacts = new ArrayList<Contact>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String mobile = cursor.getString(2);

                Contact contact = new Contact(id, name , mobile);
                contacts.add(contact);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contacts;
    }

    public List<Contact> select(String str){
        List<Contact> contacts = new ArrayList<Contact>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + COLUMN_NAME + " LIKE '%"
                + str + "%'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String mobile = cursor.getString(2);

                Contact contact = new Contact(id, name , mobile);
                contacts.add(contact);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contacts;
    }

    public Contact select(int pk){
        Contact contact = new Contact();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + COLUMN_ID + " = " + pk;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()){
            contact.setId(cursor.getInt(0));
            contact.setName(cursor.getString(1));
            contact.setMobile(cursor.getString(2));
        }
        cursor.close();
        db.close();
        return contact;
    }

}
