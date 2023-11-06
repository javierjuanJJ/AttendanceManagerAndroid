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

}
