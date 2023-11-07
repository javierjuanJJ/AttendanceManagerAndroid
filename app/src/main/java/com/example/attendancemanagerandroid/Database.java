package com.example.attendancemanagerandroid;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
    private static final String URL = "jdbc:mysql//IP/attendancemanager";
    private static final String USER = "user";
    private static final String PASSWORD = "1234";

    private Statement statement;


    public Database() {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Class> getAllClasses() throws SQLException {
        ArrayList<Class> classes = new ArrayList<>();
        String select = "SELECT * FROM 'classes'";
        ResultSet rs = statement.executeQuery(select);

        while (rs.next()) {
            Class class1 = new Class(rs.getInt("ID"), rs.getString("ClassName"));
            classes.add(class1);
        }

        return classes;
    }


    public int getNextClassID() throws SQLException {
        int id = 0;

        ArrayList<Class> classes = getAllClasses();
        int size = classes.size();
        if (size != 0) {
            int last = size - 1;
            Class lastClass = classes.get(last);
            id = lastClass.getId() + 1;
        }

        return id;
    }


    public void addClass(Class classContent) throws SQLException {
        String insert = "INSERT INTO 'classes' ('ID','ClassName') VALUES ('" + classContent.getId()
                + "'+'" + classContent.getClassName() +
                "');";

        statement.execute(insert);
        String create1 = "CREATE TABLE IF NOT EXISTS '" +
                classContent.getId() + " - Sessions" +
                "' ('ID' integer, 'Subject' text, 'Date' text)";

        statement.execute(create1);

        String create2 = "CREATE TABLE IF NOT EXISTS '" +
                classContent.getId() + " - Students" +
                "' ('ID' integer, 'FirstName' text, 'LastName' text, 'Email' text, 'Telephone' text )";

        statement.execute(create2);

    }

    public void deleteClass(int idClass) throws SQLException {
        String delete = "DELETE FROM 'classes' WHERE 'ID' = " +
                idClass +
                " ;";
        statement.execute(delete);
        String drop1 = "DROP TABLE '" + idClass + "' - Sessions;";
        String drop2 = "DROP TABLE '" + idClass + "' - Students;";
        statement.execute(drop1);
        statement.execute(drop2);

    }

    public ArrayList<Student> getStudents(int idClass) throws SQLException {
        ArrayList<Student> list = new ArrayList<>();

        String select = "SELECT * FROM '" + idClass + " - Students'";
        ResultSet rs = statement.executeQuery(select);

        while (rs.next()) {
            Student class1 = new Student(rs.getInt("ID"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Email"), rs.getString("Telephone"));
            list.add(class1);
        }


        return list;
    }
    public int getNextStudentID(int idStudent) throws SQLException {
        int id = 0;

        ArrayList<Student> classes = getStudents(idStudent);
        int size = classes.size();
        if (size != 0) {
            int last = size - 1;
            Student lastClass = classes.get(last);
            id = lastClass.getId() + 1;
        }

        return id;
    }

    public Student getStudents(int idClass, int studentId) throws SQLException {
        String select = "SELECT 'ID','FirstName','LastName','Email','Telephone', FROM '" + idClass + " - Students' WHERE 'ID' = " + studentId + ";";
        ResultSet rs = statement.executeQuery(select);
        rs.next();
        Student class1 = new Student(rs.getInt("ID"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Email"), rs.getString("Telephone"));


        return class1;
    }

    public void addClass(Student classContent, int classID) throws SQLException {
        String insert = "INSERT INTO '" +
                classID +
                " - Students' ('ID','FirstName','LastName','Email','Telephone') VALUES ('" +
                classContent.getId() +
                "','" +
                classContent.getFirstName() +
                "','" +
                classContent.getLastName() +
                "','" +
                classContent.getEmail() +
                "','" +
                classContent.getTelephone() +
                "');";

        statement.execute(insert);

    }


    public void updateClass(Student classContent, int classID) throws SQLException {
        String insert = "UPDATE '" +
                classID +
                " - Students' SET ('FirstName' = '" +
                classContent.getFirstName() +
                "','LastName' = '" +
                classContent.getLastName() +
                "','Email' = '" +
                classContent.getEmail() +
                "','Telephone' = '" +
                classContent.getTelephone() +
                "' WHERE 'ID' = " +
                classContent.getId() +
                ");";

        statement.execute(insert);

    }
}
