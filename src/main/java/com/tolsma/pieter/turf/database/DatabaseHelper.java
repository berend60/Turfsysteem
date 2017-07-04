package com.tolsma.pieter.turf.database;

import com.tolsma.pieter.turf.util.DatabaseURLRetriever;

import java.sql.*;
import java.util.Properties;

/**
 * Created by pietertolsma on 7/4/17.
 */
public class DatabaseHelper {

    private String user, password, hostAndDB;
    private Connection conn;

    private String url = DatabaseURLRetriever.retrieveURL();

    private static DatabaseHelper db = new DatabaseHelper();

    public static DatabaseHelper getDB() {
        return db;
    }

    public DatabaseHelper() {
        String[] s1 = url.split(":");
        user = s1[1].substring(2, s1[1].length());
        password = s1[2].split("@")[0];
        hostAndDB = url.split("@")[1];

        conn = setupConnection();
        if (conn != null) System.out.println("Connection to database established!");
        else System.out.println("Failed to connect to database");
    }

    public boolean isConnected() {
        try {
            return conn.isValid(500);
        } catch (SQLException e) {
            return false;
        }
    }

    public ResultSet query(String query){
        try {
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(query);
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void statement(String query) {
        try {
            Statement smt = conn.createStatement();
            smt.executeUpdate(query);
            smt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection setupConnection() {
        try {

            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!");
            e.printStackTrace();

        }
        Connection connection = null;
        Properties props = new Properties();
        props.setProperty("ssl", "true");
        String newUrl = "jdbc:postgresql://" + hostAndDB + "?user=" + user +"&password=" + password + "&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
        try {
            connection = DriverManager.getConnection(newUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

}
