package com.mytop10.web.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mytop10.dao.DatabaseConnection;
import com.mytop10.spotify.SpotifyApiSingleton;

@WebListener
public class AppTop10Listener implements ServletContextListener {

   private Timer timer;

   @Override
   public void contextInitialized( ServletContextEvent sce ) {

      timer = new Timer( true );
      int period = 3600 * 1000;
      timer.scheduleAtFixedRate( new TimerTask() {
         @Override
         public void run() {
            SpotifyApiSingleton.sincronizaCredencials();
         }
      }, 0, period );

      startDatabase();
   }


   private void startDatabase() {
      DatabaseConnection dbConnection = DatabaseConnection.getInstance();

      try{
         executeSqlScript( "schema.sql", dbConnection );
         executeSqlScript( "data.sql", dbConnection );

      }
      catch( SQLException e ){
         e.printStackTrace();
      }
      finally{
         dbConnection.close();
      }

   }


   private void executeSqlScript( String scriptName, DatabaseConnection dbConnection ) throws SQLException {
      try( InputStream inputStream = getClass().getClassLoader().getResourceAsStream( scriptName ); BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) )){
         String linha;
         StringBuilder sql = new StringBuilder();

         while( ( linha = reader.readLine() ) != null ){
            sql.append( linha );
            if( linha.trim().endsWith( ";" ) ){
               try( PreparedStatement ps = dbConnection.prepareStatement( sql.toString(), PreparedStatement.NO_GENERATED_KEYS )){
                  ps.execute();
               }
               sql.setLength( 0 );
            }
         }
      }
      catch( IOException e ){
         e.printStackTrace();
      }
   }


   @Override
   public void contextDestroyed( ServletContextEvent sce ) {
      System.out.println( "Aplicação encerrada" );
      if( timer != null ){
         timer.cancel();
      }
   }

}
