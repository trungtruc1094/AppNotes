package com.example.trungtrucnguyen.appnotes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;


public class SecondActivity extends AppCompatActivity {
    EditText editText;
    int noteId;

    public void submitNote(View view) {
        // Add new note
        if (MainActivity.notes.size() == 0 || MainActivity.addNewNote){
            MainActivity.notes.add(0, editText.getText().toString());
        } else {
            // Update current note
            MainActivity.notes.set(noteId, String.valueOf(editText.getText()));
        }
        // Adding sharedPreferences to store data
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.trungtrucnguyen.appnotes", Context.MODE_PRIVATE);
        HashSet<String> set = new HashSet<>(MainActivity.notes);
        sharedPreferences.edit().putStringSet("notes", set).apply();
//        Log.i("Set", set.toString());

        MainActivity.arrayAdapter.notifyDataSetChanged();
        // Back to Main Activity
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Declare intent to get value from main Activity
        Intent intent = getIntent();
        // Declare EditText
        editText = (EditText) findViewById(R.id.editText);
        noteId = intent.getIntExtra("noteId", -1);

        if (noteId != -1) {
            editText.setText(MainActivity.notes.get(noteId));
        } else {
            editText.setText("");
            noteId = MainActivity.notes.size();
            Log.i("noteID: ", String.valueOf(noteId));
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
