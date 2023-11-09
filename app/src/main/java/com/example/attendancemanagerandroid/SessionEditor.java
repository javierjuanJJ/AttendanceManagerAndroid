package com.example.attendancemanagerandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class SessionEditor extends AppCompatActivity {
    private int sessionID,studentID;
    private TextView tvIdSessionEditor,tvDelete,tvEditSession;
    private EditText etSubject, etDate;
    private Button btnSubmit;
    private Database database;
    private int classID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_session_editor);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setUI();
    }

    private void setUI() {
        tvIdSessionEditor = findViewById(R.id.tvIdSessionEditor);
        tvDelete = findViewById(R.id.tvDelete);
        tvEditSession = findViewById(R.id.tvEditSession);

        etSubject = findViewById(R.id.etSubject);
        etDate = findViewById(R.id.etDate);

        btnSubmit = findViewById(R.id.btnSubmit);

        sessionID = getIntent().getIntExtra("Class ID", 0);

        database = new Database();

        if (getIntent().hasExtra("Session ID")){
            Session session = null;
            try {
                tvDelete.setVisibility(View.VISIBLE);
                tvEditSession.setVisibility(View.VISIBLE);
                sessionID = getIntent().getIntExtra("Session ID", sessionID);
                session = database.getSession(classID,sessionID);
                etSubject.setText(session.getSubject());
                etDate.setText(session.getDate());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Session finalSession = session;
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        finalSession.setSubject(etSubject.getText().toString());
                        finalSession.setDate(etDate.getText().toString());
                        database.updateSessionData(finalSession, classID);
                        Toast.makeText(SessionEditor.this, "Session updated succesfully", Toast.LENGTH_SHORT).show();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        database.deleteSession(classID, sessionID);
                        Toast.makeText(SessionEditor.this, "Session deleted succesfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });

            tvEditSession.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SessionEditor.this, EditSessionStudents.class);
                    intent.putExtra("Class ID", classID);
                    intent.putExtra("Session ID", sessionID);
                    startActivity(intent);
                    finish();
                }
            });
        }
        else {
            try {
                sessionID = database.getNextSessionID(classID);
                tvIdSessionEditor.setText(String.valueOf(sessionID));
                Session session = new Session();
                session.setId(sessionID);
                session.setSubject(etSubject.getText().toString());
                session.setDate(etDate.getText().toString());
                database.addSession(session, sessionID);
                Toast.makeText(this, "Session added succesfully", Toast.LENGTH_SHORT).show();
                finish();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
}