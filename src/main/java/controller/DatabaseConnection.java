package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseConnection {
    private static final String URL = "jdbc:h2:~/test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    
    private static DatabaseConnection singleton  = new DatabaseConnection();
    
    private Connection                connection = null;
    private Statement                 statement  = null;
    private ResultSet                 resultSet  = null;
    private ResultSetMetaData         metaData   = null;

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseConnection getInstance() {
       return singleton;
    }


    public Connection getConn() {
       return connection;
    }


    public Statement getStatement() {
       return statement;
    } 


    public ResultSet getResultset() {
       return resultSet;
    }
    

    public void executeQuery( String query ) throws SQLException {

       if( statement == null ){
          createStatement();
       }
       
       System.out.println( query );
       resultSet = statement.executeQuery( query );
       metaData = resultSet.getMetaData();

    }


    public boolean execute( String sql ) throws SQLException {

       if( statement == null ){
          createStatement();
       }

       System.out.println( sql );
       return statement.execute( sql );
    }


    public void createStatement() throws SQLException {

       if( connection == null ){
          connect();
       }
       statement = connection.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
    }


    public void connect() throws SQLException {

       if( connection == null ){
          connection = DriverManager.getConnection( URL, USER, PASSWORD);
       }
    }


    public void disconnectFromDatabase() throws SQLException {

       if( resultSet != null ){
          resultSet.close();

       }
       if( connection != null ){
          connection.close();

       }
       if( statement != null ){
          statement.close();

       }
       connection = null;
       statement = null;
       resultSet = null;
    }
}
