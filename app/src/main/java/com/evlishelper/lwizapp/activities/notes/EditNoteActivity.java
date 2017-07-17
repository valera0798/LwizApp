package com.evlishelper.lwizapp.activities.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import com.evlishelper.lwizapp.R;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

public class EditNoteActivity extends AppCompatActivity implements ColorPickerDialogListener {

    private int[] colors = ColorPickerDialog.MATERIAL_COLORS;
    private EditText title;
    private EditText text;
    private int color;
    Note note;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        toolbar = (Toolbar) findViewById(R.id.editToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setTitle("");
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        title = (EditText) findViewById(R.id.editTitle);
        text = (EditText) findViewById(R.id.editText);
        text.requestFocus();
        final Intent editData = getIntent();
        if (editData.getStringExtra("Action").contentEquals("Add"))
            getSupportActionBar().setTitle("Add note");
        else if (editData.getStringExtra("Action").contentEquals("Edit"))
            getSupportActionBar().setTitle("Edit note");
        note = editData.getParcelableExtra("Note");
        if (note != null) {
            title.setText(note.title);
            text.setText(note.text);
            color = note.color;
        } else {
            color = 16728876;
            color *= -1;
        }
        findViewById(R.id.editActivity).setBackgroundColor(color);
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f;
        int colorDarker = Color.HSVToColor(hsv);
        title.setBackgroundColor(colorDarker);
        StyleActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder ays = new AlertDialog.Builder(this);
        ays.setMessage("Are you sure want to exit? Changes won't be saved.");
        ays.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        ays.setNegativeButton("No", null);
        ays.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editDone:
                Intent result = new Intent(EditNoteActivity.this, EditNoteActivity.class);
                if (note == null) {
                    note = new Note(String.valueOf(title.getText()), String.valueOf(text.getText()),
                            System.currentTimeMillis(), color, System.currentTimeMillis());
                } else {
                    note.title = String.valueOf(title.getText());
                    note.text = String.valueOf(text.getText());
                }
                note.color = color;
                note.date = System.currentTimeMillis();
                result.putExtra("Note", note);
                setResult(RESULT_OK, result);
                finish();
                break;
            case R.id.colorPicker:
                ColorPickerDialog.newBuilder().setAllowCustom(false).setShowColorShades(false)
                        .setColor(color).setDialogType(ColorPickerDialog.TYPE_PRESETS)
                        .setPresets(colors).show(EditNoteActivity.this);
                break;
        }
        return true;
    }

    @Override
    public void onColorSelected(int dialogId, @ColorInt int color) {
        this.color = color;
        StyleActivity();
    }

    private void StyleActivity() {
        Log.d("Color", title.getCurrentHintTextColor() + "");
        if (color == Color.BLACK) {
            title.setTextColor(Color.WHITE);
            title.setHintTextColor(Color.WHITE);
            text.setTextColor(Color.WHITE);
            text.setHintTextColor(Color.WHITE);
        } else {
            title.setTextColor(Color.BLACK);
            title.setHintTextColor(1627389952);
            text.setTextColor(Color.BLACK);
            text.setHintTextColor(1627389952);
        }
        findViewById(R.id.editActivity).setBackgroundColor(color);
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f;
        int colorDarker = Color.HSVToColor(hsv);
        title.setBackgroundColor(colorDarker);
        toolbar.setBackgroundColor(color);
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}
