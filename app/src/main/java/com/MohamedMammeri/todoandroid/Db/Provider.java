package com.MohamedMammeri.todoandroid.Db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Provider extends ContentProvider {
    Helper dbHelper;
    public static UriMatcher uriMatcher=buildMatcher();
    public static final int MULTIPLE_TASKS=100;
    public static final int SINGLE_TASK=101;


    public static UriMatcher buildMatcher(){
        UriMatcher uriMatcher1=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher1.addURI(Contract.AUTHORITY,Contract.PATH_TASKS,MULTIPLE_TASKS);
        uriMatcher1.addURI(Contract.AUTHORITY,Contract.PATH_TASKS+"/#",SINGLE_TASK);
        return uriMatcher1;
    }
    @Override
    public boolean onCreate() {
        dbHelper=new Helper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        final SQLiteDatabase db=dbHelper.getReadableDatabase();
        int matcher=uriMatcher.match(uri);
        Cursor mCursor;
        switch (matcher){
            case MULTIPLE_TASKS:
                mCursor=db.query(Contract.Entry.TABLE_NAME,strings,s,strings1,null,null,s1 +" DESC");
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        mCursor.setNotificationUri(getContext().getContentResolver(),uri);

        return mCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db=dbHelper.getWritableDatabase();
        int match=uriMatcher.match(uri);
        Uri uri1;
        switch (match){
            case MULTIPLE_TASKS:
                long id=db.insert(Contract.Entry.TABLE_NAME,null,contentValues);
                if (id>0){
                    uri1= ContentUris.withAppendedId(Contract.Entry.CONTENT_URI,id);

                }else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return uri1;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db=dbHelper.getWritableDatabase();
        int matcher=uriMatcher.match(uri);
        int flag;
        switch (matcher){
            case SINGLE_TASK:
                String id=uri.getPathSegments().get(1);
                flag=db.delete(Contract.Entry.TABLE_NAME,"ID=?",new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (flag != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return flag;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
