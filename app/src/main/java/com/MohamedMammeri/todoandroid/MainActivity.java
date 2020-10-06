package com.MohamedMammeri.todoandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.MohamedMammeri.todoandroid.Db.Contract;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, Adapter.listItemLongClickListener {
    Adapter adapter;
    RecyclerView recyclerView;
    ImageView imageView;
    Cursor cursor;
    final static int LOADER_ID=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.imageView);
        //===================
        recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager manager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        adapter=new Adapter(this,this);
        recyclerView.setAdapter(adapter);


        getSupportLoaderManager().initLoader(LOADER_ID,null,this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor mCursor = null;

            @Override
            protected void onStartLoading() {
                if (mCursor != null) {
                    deliverResult(mCursor);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }
            public void deliverResult(Cursor data) {
                mCursor = data;
                super.deliverResult(data);
            }

            @Nullable
            @Override
            public Cursor loadInBackground() {
                try {

                    return getContentResolver().query(Contract.Entry.CONTENT_URI, null, null, null, Contract.Entry.COLUMN_PREORITY);
                }catch (Exception e){
                    Log.d("exception",e.toString());
                }
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

adapter.addCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
adapter.addCursor(null);
    }
    @Override
    protected void onResume() {
        super.onResume();

        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    public void noData(){
        recyclerView.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.VISIBLE);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_add) {
            Intent intent = new Intent(MainActivity.this, AddClass.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(int id) {
        Log.d("onClickpressed","true");
        getContentResolver().delete(Contract.Entry.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build(),
                null,null);
        getSupportLoaderManager().restartLoader(LOADER_ID,null,MainActivity.this);
    }
}