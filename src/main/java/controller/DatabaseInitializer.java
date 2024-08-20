package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DatabaseInitializer implements ServletContextListener {

   @Override
   public void contextInitialized(ServletContextEvent sce) {
       DatabaseConnection dbConnection = DatabaseConnection.getInstance();

       try {
           executeSqlScript("schema.sql", dbConnection);
           executeSqlScript("data.sql", dbConnection);

       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           dbConnection.close(); 
       }
   }

   @Override
   public void contextDestroyed( ServletContextEvent sce ) {
      // Limpeza, se necessário
   }


   private void executeSqlScript(String scriptName, DatabaseConnection dbConnection) throws SQLException {
      try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(scriptName);
           BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
          String line;
          StringBuilder sqlBuilder = new StringBuilder();

          while ((line = reader.readLine()) != null) {
              sqlBuilder.append(line);
              if (line.trim().endsWith(";")) {
                  try (PreparedStatement ps = dbConnection.prepareStatement(sqlBuilder.toString(), PreparedStatement.NO_GENERATED_KEYS)) {
                      ps.execute();
                  }
                  sqlBuilder.setLength(0); // Limpa o StringBuilder para o próximo comando
              }
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
}