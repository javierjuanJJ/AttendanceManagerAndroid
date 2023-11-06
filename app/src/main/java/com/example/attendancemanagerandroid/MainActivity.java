package com.example.attendancemanagerandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvClasses;
    private Button btnNewClass;
    private Database database;
    private ArrayList<Class> classes;
    private ListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS );
        setContentView(R.layout.activity_main);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setUI();

    }

    private void setUI() {
        btnNewClass = findViewById(R.id.btnNewClass);
        lvClasses = findViewById(R.id.lvClasses);


        btnNewClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ClassEditor.class);
                startActivity(intent);
            }
        });

        database = new Database();
        try {
            classes = database.getAllClasses();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listAdapter = new ListAdapter(classes, getApplicationContext());
        lvClasses.setAdapter(listAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();

        try {
            classes = database.getAllClasses();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listAdapter.notifyDataSetChanged();

    }
}