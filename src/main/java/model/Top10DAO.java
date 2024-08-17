package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.DatabaseConnection;

public class Top10DAO {
   public int criaTop10(Top10 top10) {
      DatabaseConnection instance = DatabaseConnection.getInstance();
      int idGerado = 0;
      String sql = "INSERT INTO top10 (Descricao, Titulo) VALUES (?,?);";

      try (PreparedStatement ps = instance.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
          ps.setString(1, top10.getDescricao());
          ps.setString(2, top10.getTitulo());

          int affectedRows = ps.executeUpdate();

          if (affectedRows > 0) {
              try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                  if (generatedKeys.next()) {
                      idGerado = generatedKeys.getInt(1);
                  } else {
                      System.out.println("Nenhum ID gerado.");
                  }
              }
          } else {
              System.out.println("Nenhuma linha afetada.");
          }
      } catch (SQLException e) {
          e.printStackTrace();
      } finally {
          instance.close();  // Fecha a conexão no final
      }
      return idGerado;
  }

}
