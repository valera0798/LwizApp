package com.evlishelper.lwizapp.activities.notes;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.evlishelper.lwizapp.R;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {

    private NoteAdapter adapter;
    Toolbar toolbar;
    private SortType sortType = SortType.NO;
    ArrayList<Note> notes;
    DBHelper dbHelper;
    SharedPreferences.Editor editor;

    public static final int ADD_NOTE = 1;
    public static final int EDIT_NOTE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNote = new Intent(NotesActivity.this, EditNoteActivity.class);
                addNote.putParcelableArrayListExtra("List", notes);
                addNote.putExtra("Action", "Add");
                startActivityForResult(addNote, ADD_NOTE);
            }
        });

        SharedPreferences preferences = getSharedPreferences("com.evlishelper.lwizapp_preferences", MODE_PRIVATE);
        editor = preferences.edit();
        if (!preferences.contains("Default_Sort_Type")) {
            editor.putInt("Default_Sort_Type", SortType.NO.ordinal());
            editor.apply();
        } else {
            sortType = SortType.values()[preferences.getInt("Default_Sort_Type", SortType.NO.ordinal())];
        }

        dbHelper = new DBHelper(this);
        notes = getData(sortType);
        RecyclerView listNotes = (RecyclerView) findViewById(R.id.listNotes);
        listNotes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(notes);
        listNotes.setAdapter(adapter);

        // Tap on note -> edit it.
        listNotes.addOnItemTouchListener(new NoteItemClickListener(this, listNotes, new NoteItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Intent editNote = new Intent(NotesActivity.this, EditNoteActivity.class);
                editNote.putExtra("Note", notes.get(position));
                editNote.putExtra("Action", "Edit");
                startActivityForResult(editNote, EDIT_NOTE);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        registerForContextMenu(listNotes);
    }

    // On creating or editing notes.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            ContentValues cv = new ContentValues();
            Note note;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            switch (requestCode) {
                case ADD_NOTE:
                    note = data.getParcelableExtra("Note");
                    cv.put("Title", note.title);
                    cv.put("Text", note.text);
                    cv.put("Date", note.date);
                    cv.put("CreateTime", note.createTime);
                    cv.put("Color", note.color);
                    db.insert("noteTable", null, cv);
                    notes.clear();
                    notes.addAll(getData(sortType));
                    adapter.notifyDataSetChanged();
                    break;
                case EDIT_NOTE:
                    note = data.getParcelableExtra("Note");
                    cv.put("CreateTime", note.createTime);
                    cv.put("Title", note.title);
                    cv.put("Text", note.text);
                    cv.put("Date", note.date);
                    cv.put("Color", note.color);
                    db.update("noteTable", cv, "CreateTime = " + note.createTime, null);
                    notes.clear();
                    notes.addAll(getData(sortType));
                    adapter.notifyDataSetChanged();
                    break;
            }
            db.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 0, "Sort from newer to older");
        menu.add(1, 2, 0, "Sort from older to newer");
        menu.add(1, 3, 0, "Sort A-Z");
        menu.add(1, 4, 0, "Sort Z-A");
        return true;
    }

    // Edit and delete context menu.
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position;
        try {
            position = adapter.getPosition();
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case 0:
                Intent editNote = new Intent(NotesActivity.this, EditNoteActivity.class);
                editNote.putExtra("Note", notes.get(position));
                editNote.putExtra("Action", "Edit");
                startActivityForResult(editNote, EDIT_NOTE);
                break;
            case 1:
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("noteTable", "CreateTime = " + notes.get(position).createTime, null);
                adapter.notifyItemRemoved(position);
                notes.remove(position);
                db.close();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                sortType = SortType.NO;
                notes.clear();
                notes.addAll(getData(sortType));
                adapter.notifyDataSetChanged();
                editor.putInt("Default_Sort_Type", SortType.NO.ordinal());
                break;
            case 2:
                sortType = SortType.ON;
                notes.clear();
                notes.addAll(getData(sortType));
                adapter.notifyDataSetChanged();
                editor.putInt("Default_Sort_Type", SortType.ON.ordinal());
                break;
            case 3:
                sortType = SortType.AZ;
                notes.clear();
                notes.addAll(getData(sortType));
                adapter.notifyDataSetChanged();
                editor.putInt("Default_Sort_Type", SortType.AZ.ordinal());
                break;
            case 4:
                sortType = SortType.ZA;
                notes.clear();
                notes.addAll(getData(sortType));
                adapter.notifyDataSetChanged();
                editor.putInt("Default_Sort_Type", SortType.ZA.ordinal());
                break;
        }
        editor.apply();
        return true;
    }

    private class DBHelper extends SQLiteOpenHelper {

        static final String DATABASE_NAME = "LwizApp.db";

        DBHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // создаем таблицу с полями
            db.execSQL("create table noteTable ("
                    + "CreateTime integer primary key,"
                    + "Title text,"
                    + "Text text,"
                    + "Date integer,"
                    + "Color integer" + ");");
            ContentValues cv = new ContentValues();
            cv.put("Title", "Hello from 20!7");
            cv.put("Text", "Note");
            cv.put("Color", -16728876);
            cv.put("CreateTime", 0);
            cv.put("Date", 0);
            db.insert("noteTable", null, cv);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public ArrayList<Note> getData(SortType sortType) {
        ArrayList<Note> notes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String orderBy;
        switch (sortType) {
            case ON:
                orderBy = "Date";
                break;
            case AZ:
                orderBy = "Title COLLATE NOCASE ASC";
                break;
            case ZA:
                orderBy = "Title COLLATE NOCASE DESC";
                break;
            default:
                orderBy = "Date DESC";
                break;
        }
        Cursor c = db.query("noteTable", null, null, null, null, null, orderBy);
        if (c.moveToFirst()) {
            int createTimeIndex = c.getColumnIndex("CreateTime");
            int titleIndex = c.getColumnIndex("Title");
            int textIndex = c.getColumnIndex("Text");
            int dateIndex = c.getColumnIndex("Date");
            int colorIndex = c.getColumnIndex("Color");
            do {
                notes.add(new Note(c.getString(titleIndex), c.getString(textIndex),
                        c.getLong(dateIndex), c.getInt(colorIndex), c.getLong(createTimeIndex)));
            } while (c.moveToNext());

        }
        c.close();
        db.close();
        return notes;
    }

    private enum SortType {
        NO, ON, AZ, ZA
    }
}

