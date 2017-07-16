package com.evlishelper.lwizapp.activities.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.evlishelper.lwizapp.R;
import com.evlishelper.lwizapp.activities.notes.NotesActivity;
import com.evlishelper.lwizapp.activities.test.TestActivity;
import com.evlishelper.lwizapp.activities.tuner.TunerActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnNotesAct, btnTestAct, btnTunerAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTunerAct = (Button) findViewById(R.id.btn_tuner);
        btnNotesAct = (Button) findViewById(R.id.btn_notes);
        btnTestAct = (Button) findViewById(R.id.btn_test);

        btnTunerAct.setOnClickListener(this);
        btnNotesAct.setOnClickListener(this);
        btnTestAct.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_tuner:
                intent = new Intent(this, TunerActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_notes:
                intent = new Intent(this, NotesActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_test:
                intent = new Intent(this, TestActivity.class);
                startActivity(intent);
                break;
        }
    }
}
