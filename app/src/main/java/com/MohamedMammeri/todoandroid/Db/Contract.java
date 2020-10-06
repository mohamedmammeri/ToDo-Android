package com.MohamedMammeri.todoandroid.Db;

import android.net.Uri;
import android.provider.BaseColumns;

public class Contract {
    //===========================================
    public static final String AUTHORITY ="com.MohamedMammeri.todoandroid";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_TASKS = "ToDo";
    //============================================

    public static class Entry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();


        public static final String TABLE_NAME="ToDo";
        public static final String COLUMN_ID="ID";
        public static final String COLUMN_DO="DO";
        public static final String COLUMN_PREORITY="preority";
        public static final String COLUMN_DATE="date";

    }
}
