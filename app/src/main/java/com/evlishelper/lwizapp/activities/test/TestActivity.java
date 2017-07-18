package com.evlishelper.lwizapp.activities.test;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.evlishelper.lwizapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TimurID on 13.07.2017.
 */
public class TestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        List<Question> questionList = new ArrayList<>();
        inflateList(questionList, getList("questions.txt"));

        RecyclerView questionRV = (RecyclerView) findViewById(R.id.questions_rv);
        final QuestionAdapter adapter = new QuestionAdapter(questionList);
        questionRV.setAdapter(adapter);
        questionRV.setLayoutManager(new LinearLayoutManager(this));

        final Button finish = (Button) findViewById(R.id.finish_btn);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t = Toast.makeText(getApplicationContext(), R.string.app_name, Toast.LENGTH_SHORT);
                ImageView im = new ImageView(getApplicationContext());
                int numb = adapter.getNumberOfCorrect();
                if (numb < 4) {
                    im.setImageResource(R.drawable.god);
                    createMediaPlayer(Uri.parse("android.resource://com.evlishelper.lwizapp/raw/alright"));
                } else if (numb >= 4 && numb <= 7) {
                    im.setImageResource(R.drawable.med);
                    createMediaPlayer(Uri.parse("android.resource://com.evlishelper.lwizapp/raw/mb"));
                } else {
                    im.setImageResource(R.drawable.bad);
                    createMediaPlayer(Uri.parse("android.resource://com.evlishelper.lwizapp/raw/sad"));
                }

                t.setView(im);
                t.show();
                finish();

                String[] drugs = getList("drugs.txt");
                Toast t2 = Toast.makeText(getApplicationContext(), drugs[(int) (Math.random() * 7)], Toast.LENGTH_LONG);
                t2.setGravity(Gravity.BOTTOM, 0, 10);
                t2.show();
            }
        });
    }

    public void inflateList(List<Question> questionList, String[] strings) {
        String s = "";
        for (int i = 0; i < 10; i++) {
            int stringNumber = (int) (6 * i + Math.random() * 5);
            s = strings[stringNumber];
            questionList.add(new Question(s));
        }
    }

    public String[] getList(String filename) {
        byte[] buffer = null;
        InputStream is;
        try {
            is = getAssets().open(filename);
            int size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str_data = new String(buffer);
        String[] strings = str_data.split("\\n");
        return strings;
    }

    private void createMediaPlayer(Uri uri) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        try {
            mediaPlayer.setDataSource(getApplicationContext(), uri);
            mediaPlayer.prepare();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        mediaPlayer.setLooping(false);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
                mp = null;
            }
        });
    }
}
