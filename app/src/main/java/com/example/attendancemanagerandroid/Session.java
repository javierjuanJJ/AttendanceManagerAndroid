package com.example.attendancemanagerandroid;

import java.util.ArrayList;

public class Session {
    private int id;
    private ArrayList<Student> students;
    private String subject, date;

    public Session(int id, ArrayList<Student> students, String subject, String date) {
        this.id = id;
        this.students = students;
        this.subject = subject;
        this.date = date;
    }

    public Session() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
