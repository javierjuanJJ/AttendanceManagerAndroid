package com.example.attendancemanagerandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.Objects;

public class StudentEditor extends AppCompatActivity {

    private int classID,studentID;
    private TextView tvIdStudentEditor,tvDelete;
    private EditText etfirstNme, etLastNme, etEmail, etTelephone;
    private Button btnSubmit;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_student_editor);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setUI();
    }

    private void setUI() {

        tvIdStudentEditor = findViewById(R.id.tvIdStudentEditor);
        tvDelete = findViewById(R.id.tvDelete);

        etfirstNme = findViewById(R.id.etfirstNme);
        etLastNme = findViewById(R.id.etLastNme);
        etEmail = findViewById(R.id.etEmail);
        etTelephone = findViewById(R.id.etTelephone);

        btnSubmit = findViewById(R.id.btnSubmit);

        classID = getIntent().getIntExtra("Class ID", 0);

        database = new Database();
        if (getIntent().hasExtra("Student ID")){
            tvDelete.setVisibility(View.VISIBLE);
            classID = getIntent().getIntExtra("Student ID", 0);
            Student student = null;
            try {
                student = database.getStudents(classID, studentID);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            tvIdStudentEditor.setText(String.valueOf(Objects.requireNonNull(student).getId()));
            etfirstNme.setText(student.getFirstName());
            etLastNme.setText(student.getLastName());
            etEmail.setText(student.getEmail());
            etTelephone.setText(student.getTelephone());


            Student finalStudent = student;
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finalStudent.setFirstName(etfirstNme.getText().toString());
                    finalStudent.setLastName(etLastNme.getText().toString());
                    finalStudent.setEmail(etEmail.getText().toString());
                    finalStudent.setTelephone(etTelephone.getText().toString());

                    try {
                        database.updateClass(finalStudent, classID);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(StudentEditor.this, "Student updated succesfully", Toast.LENGTH_SHORT).show();
                }
            });
            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        database.deleteClass(classID);
                        Toast.makeText(StudentEditor.this, "Student deleted succesfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else {
            try {
                studentID = database.getNextStudentID(classID);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Student finalStudent = new Student();
                    finalStudent.setFirstName(etfirstNme.getText().toString());
                    finalStudent.setLastName(etLastNme.getText().toString());
                    finalStudent.setEmail(etEmail.getText().toString());
                    finalStudent.setTelephone(etTelephone.getText().toString());

                    try {
                        database.addClass(finalStudent, classID);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(StudentEditor.this, "Student added succesfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            tvIdStudentEditor.setText(String.valueOf(studentID));
        }

    }
}