package com.chen.xinyueweather.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.greenrobot.greendao.database.DatabaseOpenHelper;

/**
 * author long
 * date 17-10-18
 * desc
 */

class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DBNAME = "location.db";

    private static final int VERSION = 1;

    public DBOpenHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
