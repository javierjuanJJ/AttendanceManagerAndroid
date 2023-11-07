package com.example.attendancemanagerandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;

public class ClassEditor extends AppCompatActivity {

    private LinearLayout lvStudents;
    private Button btnAddStudents,btnDelete,btnCreate;
    private ListView lvClasses;
    private EditText etStudentName;

    private int id;

    private ArrayList<Student> studentArrayList;
    private Database database;
    private ListAdapterStudents listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_class_editor);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            studentArrayList = database.getStudents(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listAdapter.notifyDataSetChanged();
    }

    private void setUI() {
        lvStudents = findViewById(R.id.lvStudents);

        btnAddStudents = findViewById(R.id.btnAddStudents);
        btnDelete = findViewById(R.id.btnDelete);
        btnCreate = findViewById(R.id.btnCreate);

        lvClasses = findViewById(R.id.lvClasses);

        etStudentName = findViewById(R.id.etStudentName);

        if (getIntent().hasExtra("Class ID")){
            etStudentName.setText(getIntent().getStringExtra("Class Name"));
            id = getIntent().getIntExtra("Class ID", 0);
            etStudentName.setEnabled(false);
            etStudentName.setVisibility(View.GONE);
        }
        else {
            btnAddStudents.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }

        database = new Database();
        try {
            studentArrayList = database.getStudents(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        listAdapter = new ListAdapterStudents(studentArrayList, getApplicationContext(), id);

        btnAddStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassEditor.this, StudentEditor.class);
                intent.putExtra("Class ID", id);
                startActivity(intent);
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Class c = new Class(database.getNextClassID(), etStudentName.getText().toString());
                    database.addClass(c);
                    etStudentName.setEnabled(false);
                    btnCreate.setVisibility(View.GONE);
                    btnAddStudents.setVisibility(View.VISIBLE);
                    btnDelete.setVisibility(View.VISIBLE);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    database.deleteClass(id);
                    finish();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}