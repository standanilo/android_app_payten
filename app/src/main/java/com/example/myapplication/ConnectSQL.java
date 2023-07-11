package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectSQL {

    Connection connection;

    @SuppressLint("NewApi")
    public Connection connectionClass() {
        String ip = "172.16.5.163";
//        String ip = "172.16.9.45";
        String port = "1433";
        String database = "Store";
        String username = "sa";
        String password = "1234";

        StrictMode.ThreadPolicy a = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(a);

        String connectionURL = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";" + "databasename=" + database + ";user=" + username + ";password=" + password + ";";
            connection = DriverManager.getConnection(connectionURL);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return connection;
    }

}
