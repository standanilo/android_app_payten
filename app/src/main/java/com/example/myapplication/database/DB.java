package com.example.myapplication.database;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
    private static final String username = "sa";
    private static final String password = "1234";
    private static final String database = "Store";
    private static final String ip = "192.168.1.17";
    private static final String port = "1433";

    private static final String connectionURL =
            "jdbc:jtds:sqlserver://" + ip + ":" + port + ";" + "databasename=" + database + ";user=" + username + ";password=" + password + ";";

    private Connection connection;

    private DB() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection(connectionURL);
            Log.d("Hello", "World");
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

    private static DB db = null;

    public static DB getInstance() {
        if (db == null) {
            db = new DB();
        }
        return db;
    }

    public Connection getConnection() {
        return connection;
    }
}
