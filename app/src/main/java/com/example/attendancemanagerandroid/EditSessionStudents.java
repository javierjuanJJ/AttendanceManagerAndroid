package com.example.attendancemanagerandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;

public class EditSessionStudents extends AppCompatActivity {
    private Button btnNewSession;
    private ListView lvSessions;
    private int classID,studentID;
    private Database database;
    private ArrayList<Student> listStudents;
    private ArrayList<Integer> sesionsStudents, inserStudenrs, deletedStudents;
    private ListAdapterSessions listAdapter;
    private int sessionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_edit_session_students);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setUI();
    }

    private void setUI() {
        btnNewSession = findViewById(R.id.btnEditSession);
        lvSessions = findViewById(R.id.lvPresent);

        classID = getIntent().getIntExtra("Class ID", 0);
        sessionID = getIntent().getIntExtra("Session ID", 0);
        database = new Database();
        Session session =null;
        ArrayList<Student> studentArrayList = null;
        try {
            listStudents = database.getStudents(classID);
            session = database.getSession(classID, sessionID);
            studentArrayList = session.getStudents();
            sesionsStudents = new ArrayList<>();

            for (Student s : studentArrayList) {
                sesionsStudents.add(s.getId());
            }

            inserStudenrs = new ArrayList<>();
            deletedStudents = new ArrayList<>();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //listAdapter = new ListAdapterSessions(listStudents, getApplicationContext(),classID);
        lvSessions.setAdapter(listAdapter);

        btnNewSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    database.addStudentsToSession(classID,sessionID,inserStudenrs);
                    database.removeStudentsFromSession(classID,sessionID,deletedStudents);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            listStudents = database.getStudents(classID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listAdapter.notifyDataSetChanged();
    }
}