package com.evlishelper.lwizapp.activities.tuner;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.evlishelper.lwizapp.R;

import java.io.IOException;

public class TunerActivity extends AppCompatActivity {
    private View firstString;
    private View secondString;
    private View thirdString;
    private View fourthString;
    private View fifthString;
    private View sixthString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_tuner);



        final Animation animShake1 = AnimationUtils.loadAnimation(this, R.anim.shake);
        final Animation animShake2 = AnimationUtils.loadAnimation(this, R.anim.shake);
        final Animation animShake3 = AnimationUtils.loadAnimation(this, R.anim.shake);
        final Animation animShake4 = AnimationUtils.loadAnimation(this, R.anim.shake);
        final Animation animShake5 = AnimationUtils.loadAnimation(this, R.anim.shake);
        final Animation animShake6 = AnimationUtils.loadAnimation(this, R.anim.shake);


        firstString = findViewById(R.id.first_string);
        firstString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doThings(animShake1, v, Uri.parse("android.resource://com.evlishelper.lwizapp/raw/e"));
            }
        });
        secondString = findViewById(R.id.second_string);
        secondString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doThings(animShake2, v, Uri.parse("android.resource://com.evlishelper.lwizapp/raw/b"));
            }
        });
        thirdString = findViewById(R.id.third_string);
        thirdString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doThings(animShake3, v, Uri.parse("android.resource://com.evlishelper.lwizapp/raw/g"));
            }
        });
        fourthString = findViewById(R.id.fourth_string);
        fourthString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doThings(animShake4, v, Uri.parse("android.resource://com.evlishelper.lwizapp/raw/d"));
            }
        });
        fifthString = findViewById(R.id.fifth_string);
        fifthString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doThings(animShake5, v, Uri.parse("android.resource://com.evlishelper.lwizapp/raw/a"));
            }
        });
        sixthString = findViewById(R.id.sixth_string);
        sixthString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doThings(animShake6, v, Uri.parse("android.resource://com.evlishelper.lwizapp/raw/low_e"));
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void doThings(Animation animation, View view, Uri uri) {
        view.startAnimation(animation);
        createMediaPlayer(uri);
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
