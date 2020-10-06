package com.MohamedMammeri.todoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.MohamedMammeri.todoandroid.Db.Contract;

import java.util.Calendar;

public class AddClass extends AppCompatActivity {
EditText editText;
Button btn;
RadioGroup radioGroup;
ContentValues contentValues;
int priority=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
         contentValues=new ContentValues();
        editText=findViewById(R.id.edit_todo);
        btn=findViewById(R.id.done);
        radioGroup=findViewById(R.id.radioGroup_character);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.five:
                        priority=1;
                        break;
                    case R.id.four:
                        priority=2;
                        break;
                    case R.id.three:
                        priority=3;
                        break;
                    case R.id.two:
                        priority=4;
                        break;
                    case R.id.one:
                        priority=5;
                        break;
                }
                contentValues.put(Contract.Entry.COLUMN_PREORITY,priority);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=editText.getText().toString();
                if (s.length()==0){
                    Toast.makeText(AddClass.this,"please insert text",Toast.LENGTH_SHORT).show();
                    return;
                }
                contentValues.put(Contract.Entry.COLUMN_DO,s);

                String time= Calendar.getInstance().getTime().toString();
                contentValues.put(Contract.Entry.COLUMN_DATE,time);

                Uri uri = getContentResolver().insert(Contract.Entry.CONTENT_URI, contentValues);
                finish();
            }
        });

    }
}