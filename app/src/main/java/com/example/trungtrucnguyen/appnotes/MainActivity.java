package com.example.trungtrucnguyen.appnotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> notes = new ArrayList<String>();

    public void toSecondActivity(View view, String noteString) {

        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("noteString", noteString);
        MainActivity.this.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView noteList = (ListView) findViewById(R.id.noteList);

        notes.add("Note A");
        notes.add("Note B");
        notes.add("Note C");
        notes.add("Note D");


        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);

        noteList.setAdapter(arrayAdapter);

        noteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String noteString = notes.get(position);
                toSecondActivity(view, noteString);
            }
        });
    }
}
