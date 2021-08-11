package com.example.schedulerapp.sql;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


public class MySQLiteHelper extends SQLiteOpenHelper{


    // Table Place
    public static final String TABLE_PLACE = "tbl_place";
    public static final String COLUMN_PLACE_ID = "id";
    public static final String COLUMN_PLACE_NAME = "place_name";
    public static final String COLUMN_PLACE_TYPE = "place_type";
    public static final String COLUMN_PLACE_DESCRIPTION = "place_description";
    public static final String COLUMN_PLACE_LAT = "place_lat";
    public static final String COLUMN_PLACE_LNG = "place_lng";
    public static final String COLUMN_PLACE_IMAGES = "images";


    // Table User
    public static final String TABLE_USER = "tbl_user";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_NAME = "á»§e_name";
    public static final String COLUMN_USER_FULL_NAME = "user_full_name";
    public static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String DATABASE_NAME = "tourist.db";
    private static final int DATABASE_VERSION = 3;


    // Database creation sql statement


    private static final String DATABASE_CREATE_PLACE = String.format("create table %s(%s integer primary key autoincrement, %s text not null, %s text not null, %s text not null, %sfloat null,%s float null,%s text not null);", TABLE_PLACE, COLUMN_PLACE_ID, COLUMN_PLACE_NAME, COLUMN_PLACE_TYPE, COLUMN_PLACE_DESCRIPTION, COLUMN_PLACE_LAT, COLUMN_PLACE_LNG, COLUMN_PLACE_IMAGES);
    private static final String DATABASE_CREATE_USER = "create table "
            + TABLE_USER + "(" + COLUMN_USER_ID
            + " integer primary key autoincrement, "
            + COLUMN_USER_NAME + " text not null, "
            + COLUMN_USER_FULL_NAME + " text not null, "
            + COLUMN_USER_PASSWORD + " text not null);";


    public MySQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE_PLACE);
        db.execSQL(DATABASE_CREATE_USER);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);


    }
}
