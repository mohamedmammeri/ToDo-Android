package com.MohamedMammeri.todoandroid.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper extends SQLiteOpenHelper {
    public static final String FILE_NAME="ToDo.db";
    public static final int TABLE_VERSION=1;


    public Helper( Context context) {
        super(context, FILE_NAME, null, TABLE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+Contract.Entry.TABLE_NAME+" ("+ Contract.Entry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                Contract.Entry.COLUMN_DO + " TEXT NOT NULL, "+
                Contract.Entry.COLUMN_DATE + " TEXT NOT NULL, "+
                Contract.Entry.COLUMN_PREORITY + " INTEGER NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Contract.Entry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
