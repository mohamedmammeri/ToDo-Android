package com.MohamedMammeri.todoandroid;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MohamedMammeri.todoandroid.Db.Contract;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {
    Cursor mCursor;
    Context context;
    final listItemLongClickListener mClickListener;
    public interface listItemLongClickListener{
        void onClick(int id);
    }
    public Adapter(Context context,listItemLongClickListener listener){
        this.context=context;
        this.mClickListener=listener;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_items,parent,false);
        viewHolder viewHolder=new viewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        mCursor.moveToPosition(position);
        holder.todo.setText(mCursor.getString(mCursor.getColumnIndex(Contract.Entry.COLUMN_DO)));
        holder.date.setText(mCursor.getString(mCursor.getColumnIndex(Contract.Entry.COLUMN_DATE)));

        GradientDrawable priorityCircle = (GradientDrawable) holder.priority.getBackground();
        int mPriotity=mCursor.getInt(mCursor.getColumnIndex(Contract.Entry.COLUMN_PREORITY));
        int priorityColor=Color.BLUE;
        switch (mPriotity){
            case 5:
                priorityColor=Color.WHITE;
                break;
            case 4:
                priorityColor=Color.GRAY;
                break;
            case 3:
                priorityColor=Color.GREEN;
                break;
            case 2:
                priorityColor=Color.YELLOW;
                break;
            case 1:
                priorityColor=Color.RED;
                break;
        }
        priorityCircle.setColor(priorityColor);
        holder.priority.setText(String.valueOf(mPriotity));


    }

    @Override
    public int getItemCount() {

        if (mCursor!=null)
        return mCursor.getCount();
        return 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView todo,date,priority;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            todo=itemView.findViewById(R.id.text_todo);
            date=itemView.findViewById(R.id.text_date);
            priority=itemView.findViewById(R.id.priority);
            todo.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {

            mClickListener.onClick(mCursor.getInt(mCursor.getColumnIndex(Contract.Entry.COLUMN_ID)));

            return true;
        }
    }
    public void addCursor(Cursor cursor){
        this.mCursor=cursor;
        notifyDataSetChanged();
    }
}
