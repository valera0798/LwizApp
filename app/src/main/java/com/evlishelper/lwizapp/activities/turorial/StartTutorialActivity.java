package com.evlishelper.lwizapp.activities.turorial;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evlishelper.lwizapp.R;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class StartTutorialActivity extends AppCompatActivity {

    private TutorialViewPagerAdapter adapter;
    private ViewPager tutorialViewPager;
    private PageIndicator tutorialPageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_tutorial);
        adapter = new TutorialViewPagerAdapter(getSupportFragmentManager());

        tutorialViewPager = (ViewPager)findViewById(R.id.viewPager);
        tutorialViewPager.setAdapter(adapter);

        tutorialPageIndicator = (CirclePageIndicator)findViewById(R.id.titles);
        tutorialPageIndicator.setViewPager(tutorialViewPager);
    }
}
