package com.dao.daoImpl;

import com.dao.inters.iDAO_handler;

import java.sql.*;

/**
 * DAO handler
 * @param <T> entity type
 */
public abstract class DAO_handler<T> implements iDAO_handler<T> {
    private final String url;
    private final String dbname;
    private final String username;
    private final String password;

    public DAO_handler(String url, String dbname, String username, String password) {
        this.url = url;
        this.dbname = dbname;
        this.username = username;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://"+url+"/"+dbname+"?serverTimezone=GMT%2B8", username, password);
    }

    protected final PreparedStatement getPreparedStatement(String statement) throws SQLException {
        Connection conn = getConnection();
        return conn.prepareStatement(statement);
    }

    protected final Statement getStatement(String statement) throws SQLException {
        Connection conn = getConnection();
        return conn.createStatement();
    }
}
