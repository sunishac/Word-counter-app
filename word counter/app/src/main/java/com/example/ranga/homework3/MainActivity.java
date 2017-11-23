package com.example.ranga.homework3;
/*
Name: Dhenuka Bhargavi Rangam
      Sunisha Chalasani
Group: 12
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity {

    ImageButton b;
    LinearLayout l1, local1;
    ProgressDialog pd;
    int i, btno, j = 0;
    int p;
    ArrayList<LinearLayout> local = new ArrayList<>();
    ArrayList<EditText> et1 = new ArrayList<>();
    ArrayList<FloatingActionButton> ib1 = new ArrayList<>();

    ArrayList<Integer> cfinder = new ArrayList<Integer>();
    ArrayList<String> wfinder = new ArrayList<String>();
    Button search;
    String book;
    int count_done = 0;
    CheckBox cb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        setContentView(R.layout.activity_main);
        l1 = (LinearLayout) findViewById(R.id.ll1);
        generator(0);
        cb = (CheckBox) findViewById(R.id.checkBox);
        cb.setChecked(false);
        search = (Button) findViewById(R.id.button1);
        try {
            InputStream is = getAssets().open("textfile.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            book = new String(buffer);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;

                wfinder = new ArrayList<String>();
                for (EditText et :
                        et1) {

                    wfinder.add(i, String.valueOf(et.getText()));
                    i++;
                }
                if (wfinder.contains("")) {
                    Toast.makeText(MainActivity.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
                } else {
                    pd.show();
                    if (cb.isChecked()) {
                        for (String c :
                                wfinder) {
                            new searcher().execute(c, "yes");
                        }
                    } else {
                        for (String c :
                                wfinder) {
                            new searcher().execute(c, "no");
                        }
                    }
                }
            }
        });
    }

    public void opgenerator(int i) {
        //CharSequence count_finder[] = new CharSequence[et1.size()];
        cfinder.add(j, i);
        j++;


        if (count_done == et1.size()) {
            pd.dismiss();
            Intent intent = new Intent(MainActivity.this, Words_Activity.class);
            intent.putExtra("key", cfinder);
            intent.putExtra("keyw", wfinder);

            startActivityForResult(intent, 100);

        }
    }

    public void generator(int i) {
        final int j = i;

        ib1.add(i, new FloatingActionButton(this));

        et1.add(i, new EditText(this));
        et1.get(i).setMinimumWidth(1000);

        ib1.get(i).setMaxHeight(3);
        ib1.get(i).setMaxWidth(3);
        ib1.get(i).setId(View.generateViewId());
        ib1.get(i).setImageResource(R.drawable.plus);
        et1.get(i).setId(View.generateViewId());
        local.add(i, new LinearLayout(this));
        local.get(i).addView(et1.get(i));
        local.get(i).addView(ib1.get(i));
        l1.addView(local.get(i));

        for (final ImageButton b :
                ib1) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int removal = ib1.indexOf(b);
                    l1.removeView(local.get(removal));
                    local.remove(removal);
                    ib1.remove(removal);
                    et1.remove(removal);

                }
            });

            ib1.get(ib1.size() - 1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (et1.get(j).getText().toString().equals("")) {
                        Toast.makeText(MainActivity.this, "Field should not be empty", Toast.LENGTH_LONG).show();
                    } else {
                        if (ib1.size() < 20) {
                            ib1.get(ib1.size() - 1).setImageResource(R.drawable.minus);
                            //et1.get(ib1.size() - 1).setText(ib1.size() - 1 + "generated");
                            generator(ib1.size());
                        } else {


                            Toast.makeText(MainActivity.this, "only 20 words are allowed to search at a time", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });


        }

    }


    public class searcher extends AsyncTask<String, String, Integer> {


        @Override
        protected Integer doInBackground(String... params) {
            String s = params[0];
            int count = 0;

            StringTokenizer st = new StringTokenizer(book, " ");
            //Log.d("demo",st.countTokens()+"tokesn");
            while (st.hasMoreTokens()) {
                if (params[1] == "no") {
                    if (s.equalsIgnoreCase(st.nextToken())) {
                        count++;
                    }
                } else {
                    if (s.equals(st.nextToken())) {
                        count++;
                    }

                }
            }
            publishProgress("done");
            return count;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            if (values[0].equals("done")) {
                count_done++;
            }
        }

        @Override
        protected void onPostExecute(Integer o) {

            opgenerator(o);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }
}
