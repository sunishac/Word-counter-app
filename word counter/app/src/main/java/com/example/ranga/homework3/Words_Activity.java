package com.example.ranga.homework3;

/*
Name: Dhenuka Bhargavi Rangam
      Sunisha Chalasani
Group: 12
 */

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Words_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_);
        LinearLayout l2 = (LinearLayout) findViewById(R.id.l2);

        ArrayList<Integer> count_finder = getIntent().getExtras().getIntegerArrayList("key");
        ArrayList<String> word_finder = getIntent().getExtras().getStringArrayList("keyw");


        for (int i = 0; i < count_finder.size(); i++) {
            LinearLayout l3 = new LinearLayout(this);
            TextView word = new TextView(this);
            TextView fre = new TextView(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                word.setTextAppearance(android.R.style.TextAppearance_Large);
                fre.setTextAppearance(android.R.style.TextAppearance_Large);
            }
            word.setPadding(10, 0, 0, 0);
            word.setText(word_finder.get(i) + ":");
            fre.setText(" " + count_finder.get(i));
            l3.addView(word);
            l3.addView(fre);
            l2.addView(l3);
        }
    }

    public void gotoMain(View view) {
        setResult(RESULT_OK);
        finish();
    }
}
