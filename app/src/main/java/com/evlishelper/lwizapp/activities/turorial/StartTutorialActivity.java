package com.evlishelper.lwizapp.activities.turorial;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.evlishelper.lwizapp.R;
import com.evlishelper.lwizapp.activities.main.MainActivity;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class StartTutorialActivity extends AppCompatActivity {

    private TutorialViewPagerAdapter adapter;
    private ViewPager tutorialViewPager;
    private PageIndicator tutorialPageIndicator;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_tutorial);
        adapter = new TutorialViewPagerAdapter(getSupportFragmentManager());

        tutorialViewPager = (ViewPager)findViewById(R.id.viewPager);
        tutorialViewPager.setAdapter(adapter);

        tutorialPageIndicator = (CirclePageIndicator)findViewById(R.id.titles);
        tutorialPageIndicator.setViewPager(tutorialViewPager);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartTutorialActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

}
