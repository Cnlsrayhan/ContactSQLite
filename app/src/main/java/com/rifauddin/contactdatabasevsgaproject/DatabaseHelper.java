package com.rifauddin.contactdatabasevsgaproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DB_NAME = "DBKontak";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "kontak";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "nama";
    private static final String KEY_ALAMAT = "alamat";
    private static final String KEY_NOMER = "nomer";



    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_KONTAK =
                String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT" +
                        ", %s TEXT, %s TEXT)", TABLE_NAME, KEY_ID, KEY_NAME,
                        KEY_ALAMAT, KEY_NOMER);

        db.execSQL(CREATE_TABLE_KONTAK);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public long addDataKontak(String nama, String alamat, String nomer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, nama);
        values.put(KEY_ALAMAT, alamat);
        values.put(KEY_NOMER, nomer);

        long insert = db.insert(TABLE_NAME, null, values);
        return  insert;
    }

    public ArrayList<Map<String, Object>> getAllKontak(String filter){
        ArrayList<Map<String, Object>> arrayData =
                new ArrayList<>();


        String nama, alamat, no;
        String selectQuery = String.format(
                "SELECT * FROM %s WHERE %s LIKE '%%%s%%' or %s LIKE '%%%s%%' or %s LIKE '%%%s%%'",
                TABLE_NAME, KEY_NOMER, filter, KEY_ALAMAT, filter, KEY_NAME , filter

        );
        if(filter == ""){

            selectQuery =
                    "SELECT * FROM " + TABLE_NAME;
        }


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        c.moveToPosition(-1);

        while (c.moveToNext()){
            nama = c.getString(c.getColumnIndex(KEY_NAME));
            alamat = c.getString(2);
            no = c.getString(3);
            int id = c.getInt(0);

            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("nama", nama);
            itemMap.put("alamat", alamat);
            itemMap.put("no", no);
            itemMap.put("id", id);

            arrayData.add(itemMap);

        }

        Log.i("DBASE", arrayData.toString() );
        return  arrayData;
    }


    public  void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery =
                String.format("DELETE FROM %s WHERE %s = %d",
                        TABLE_NAME, KEY_ID, id );

        db.execSQL(deleteQuery);
        db.close();


    }

    public  void update(int id, String nama, String alamat, String no){
        SQLiteDatabase db = this.getWritableDatabase();
        String updateQuery =
                String.format("UPDATE %s SET " +
                                " %s = '%s' ," +
                                " %s = '%s' , " +
                                " %s = '%s'  WHERE  %s = %d",
                        TABLE_NAME, KEY_NAME, nama, KEY_ALAMAT, alamat
                        , KEY_NOMER, no, KEY_ID, id);
        db.execSQL(updateQuery);
        db.close();

    }




}
