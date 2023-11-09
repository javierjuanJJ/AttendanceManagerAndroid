package com.example.attendancemanagerandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;

public class ViewSessions extends AppCompatActivity {
    private int classID,sessionID;
    private Database database;
    private ListView lvPresent,lvAbsent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_view_sessions);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setUI();
    }

    private void setUI() {
        classID = getIntent().getIntExtra("Class ID", 0);
        sessionID = getIntent().getIntExtra("Session ID", 0);
        database = new Database();
        lvAbsent = findViewById(R.id.lvAbsent);
        lvPresent = findViewById(R.id.lvPresent);

        try {
            Session session = database.getSession(classID, sessionID);
            ArrayList<Student> allStudents = database.getStudents(classID);
            ArrayList<Student> presentStudents = session.getStudents();
            ArrayList<Integer> presentIds = new ArrayList<>();
            ArrayList<Student> absentStudents = new ArrayList<>();

            for (Student s :
                    presentStudents) {
                presentIds.add(s.getId());
            }
            for (Student s :
                    allStudents) {
                if (!presentIds.contains(s.getId())) absentStudents.add(s);
            }

            ListAdapterStudents2 laPresent = new ListAdapterStudents2(presentStudents, getApplicationContext(), classID);
            ListAdapterStudents2 laAbsent = new ListAdapterStudents2(absentStudents, getApplicationContext(), classID);


            lvPresent.setAdapter(laPresent);
            lvAbsent.setAdapter(laAbsent);
            setListHeight(lvPresent);
            setListHeight(lvAbsent);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setListHeight(ListView list){
        android.widget.ListAdapter adapter = list.getAdapter();
        if (adapter != null){
            int height = 0;
            for (int counter = 0; counter < adapter.getCount(); counter++) {
                View listView = adapter.getView(counter, null, list);
                listView.measure(0,0);
                height += listView.getMeasuredHeight();
            }

            ViewGroup.LayoutParams par = list.getLayoutParams();
            par.height = height + (list.getDividerHeight() * (adapter.getCount() - 1));
            list.setLayoutParams(par);
            list.requestLayout();

        }
    }
}