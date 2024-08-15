package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DatabaseInitializer implements ServletContextListener {

   @Override
   public void contextInitialized( ServletContextEvent sce ) {
      try{
         DatabaseConnection dbConnection = DatabaseConnection.getInstance();
         dbConnection.createStatement();
         
         Statement stmt = dbConnection.getStatement();

         // Executar o script schema.sql
         executeSqlScript( "schema.sql", stmt );
         executeSqlScript( "data.sql", stmt );
//         teste( stmt );

      }
      catch( SQLException e ){
         e.printStackTrace();
      }
   }


   @Override
   public void contextDestroyed( ServletContextEvent sce ) {
      // Limpeza, se necessário
   }


   private void executeSqlScript( String scriptPath, Statement stmt ) {
      try( BufferedReader reader = new BufferedReader( new InputStreamReader( getClass().getClassLoader().getResourceAsStream( scriptPath ) ) )){

         String line;
         StringBuilder sql = new StringBuilder();

         while( ( line = reader.readLine() ) != null ){
            sql.append( line ).append( "\n" );
            // Executar comandos em blocos
            if( line.trim().endsWith( ";" ) ){
               stmt.execute( sql.toString() );
               sql.setLength( 0 );
            }
         }

         // Executar o restante, se houver
         if( sql.length() > 0 ){
            stmt.execute( sql.toString() );
         }

      }
      catch( Exception e ){
         e.printStackTrace();
      }
   }


   public void teste( Statement stmt ) throws SQLException {

      String selectSQL = "SELECT * FROM musica";
      ResultSet rs = stmt.executeQuery( selectSQL );

      while( rs.next() ){
         int id = rs.getInt( "id" );
         String descricao = rs.getString( "descricao" );
         System.out.println( "ID: " + id + ", Descrição: " + descricao );
      }

      rs.close();

   }
}