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

public class ShowSessions extends AppCompatActivity {
    private Button btnNewSession;
    private ListView lvSessions;
    private int classID,studentID;
    private Database database;
    private ArrayList<Session> sessions;
    private ListAdapterSessions listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_show_sessions);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setUI();
    }

    private void setUI() {
        btnNewSession = findViewById(R.id.btnEditSession);
        lvSessions = findViewById(R.id.lvPresent);

        classID = getIntent().getIntExtra("Class ID", 0);
        database = new Database();
        try {
            sessions = database.getSessions(classID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listAdapter = new ListAdapterSessions(sessions, getApplicationContext(),classID);
        lvSessions.setAdapter(listAdapter);

        btnNewSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowSessions.this, SessionEditor.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            sessions = database.getSessions(classID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listAdapter.notifyDataSetChanged();
    }
}