package br.com.alura.ecommerce.database;

import java.sql.*;

public class LocalDatabase {

    private final Connection connection;

    public LocalDatabase(String name) throws SQLException {
        String url = "jdbc:sqlite:target/" + name + ".db";
        connection = DriverManager.getConnection(url);

    }
    //Yes, this would cause a Sql Injection.
    //You may do in another way, but this is only a simple example
    //according to your database tool. avoid injection
    public void createIfNotExists(String sql){
        try {
            connection.createStatement().execute(sql);
        } catch(SQLException ex) {
            // be careful, the sql could be wrong, be reallllly careful
            ex.printStackTrace();
        }
    }

    public boolean update(String statement, String... params) throws SQLException {
        return prepare(statement, params).execute();
    }

    public ResultSet query(String query, String... params) throws SQLException {
        return prepare(query, params).executeQuery();
    }

    private PreparedStatement prepare(String statement, String[] params) throws SQLException {
        var preparedStatement = connection.prepareStatement(statement);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setString(i + 1, params[i]);
        }
        return preparedStatement;
    }

    public void close() throws SQLException {
        connection.close();
    }
}
