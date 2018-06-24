package com.example.trungtrucnguyen.appnotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import android.content.Intent;

import static android.widget.AdapterView.*;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> notes = new ArrayList<String>();
    static ArrayAdapter arrayAdapter;
    private int currentClickedNote;
    static boolean addNewNote = false;

    // Create ADD NOTE menu - Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.addNewNote){
            currentClickedNote = -1;
            addNewNote = true;
            toSecondActivity();
            return true;
        } else {
            return false;
        }
    }

    public void toSecondActivity() {

        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        intent.putExtra("noteId", currentClickedNote);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView noteList = (ListView) findViewById(R.id.noteList);

//        notes.add("Note A");

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.trungtrucnguyen.appnotes", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

        if (set != null) {
            notes = new ArrayList(set);
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);

        noteList.setAdapter(arrayAdapter);

        noteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentClickedNote = position;
                addNewNote = false;
                toSecondActivity();
            }
        });

        noteList.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {

                final int itemDeleted = pos;

                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure?")
                .setMessage("Do you want to delete this note?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Delete note
                        notes.remove(itemDeleted);
                        arrayAdapter.notifyDataSetChanged();

                        // Adding sharedPreferences to store data
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.trungtrucnguyen.appnotes", Context.MODE_PRIVATE);
                        HashSet<String> set = new HashSet<>(MainActivity.notes);
                        sharedPreferences.edit().putStringSet("notes", set).apply();
                    }
                })
                .setNegativeButton("No", null)
                .show();

                return true;
            }
        });



    }
}
