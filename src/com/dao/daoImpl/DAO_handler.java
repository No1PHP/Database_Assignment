package com.dao.daoImpl;

import com.dao.inters.iDAO_handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DAO_handler<T> implements iDAO_handler<T> {
    protected static final Connection getConnection(String url, String dbname, String username) {
        return getConnection("jdbc:mysql://"+url+"/"+dbname+"?serverTimezone=GMT%2B8", username, null);
    }

    protected static final Connection getConnection(String url, String dbname, String username, String password) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://"+url+"/"+dbname+"?serverTimezone=GMT%2B8", username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
