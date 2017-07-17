package com.evlishelper.lwizapp.activities.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.evlishelper.lwizapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        List<Question> questionList = new ArrayList<>();
        final Button finish = (Button) findViewById(R.id.finish_btn);
        String[] strings = getList("questions.txt");
        String s = "";
        for (int i = 0; i < 10; i++) {
            int stringNumber = (int) (6 * i + Math.random() * 5);
            s = strings[stringNumber];
            questionList.add(new Question(s));
        }
        RecyclerView questionRV = (RecyclerView) findViewById(R.id.questions_rv);
        final QuestionAdapter adapter = new QuestionAdapter(questionList, this);
        questionRV.setAdapter(adapter);
        questionRV.setLayoutManager(new LinearLayoutManager(this));

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t = Toast.makeText(getApplicationContext(), R.string.app_name, Toast.LENGTH_SHORT);
                ImageView im = new ImageView(getApplicationContext());
                int numb = adapter.getNumberOfCorrect();
                String s = "";
                if (numb < 4)
                    im.setImageResource(R.drawable.god);
                else if (numb >= 4 && numb <= 7)
                    im.setImageResource(R.drawable.med);
                else im.setImageResource(R.drawable.bad);
                t.setView(im);
                t.show();
                finish();
                String[] drugs = getList("drugs.txt");
                Toast.makeText(getApplicationContext(), drugs[(int) (Math.random() * 8)], Toast.LENGTH_LONG).show();
            }
        });
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
        String[] strings = str_data.split("\n");
        return strings;
    }
}
