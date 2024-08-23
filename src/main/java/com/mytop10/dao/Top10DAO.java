package com.mytop10.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mytop10.modelo.Top10;

public class Top10DAO {

   public int criaTop10( Top10 top10 ) {
      DatabaseConnection instance = DatabaseConnection.getInstance();
      int idGerado = 0;
      String sql = "INSERT INTO top10 (Descricao, Titulo) VALUES (?,?);";

      try( PreparedStatement ps = instance.prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS )){
         ps.setString( 1, top10.getDescricao() );
         ps.setString( 2, top10.getTitulo() );

         int affectedRows = ps.executeUpdate();

         if( affectedRows > 0 ){
            try( ResultSet generatedKeys = ps.getGeneratedKeys()){
               if( generatedKeys.next() ){
                  idGerado = generatedKeys.getInt( 1 );
               }
               else{
                  System.out.println( "Nenhum ID gerado." );
               }
            }
         }
         else{
            System.out.println( "Nenhuma linha afetada." );
         }
      }
      catch( SQLException e ){
         e.printStackTrace();
      }
      finally{
         instance.close(); // Fecha a conexão no final
      }
      return idGerado;
   }


   public boolean existeTop10( int id ) {
      DatabaseConnection instance = DatabaseConnection.getInstance();

      String script = "SELECT 1 FROM top10 WHERE id = ?;";

      try( PreparedStatement ps = instance.prepareStatement( script )){
         ps.setInt( 1, id );

         ResultSet rs = ps.executeQuery();

         return rs.next();

      }
      catch( SQLException e ){
         e.printStackTrace();
      }
      return false;
   }


   public List<Top10> getAll() {
      DatabaseConnection instance = DatabaseConnection.getInstance();
      List<Top10> top10 = new ArrayList<>();

      try( Statement stmt = instance.statement()){

         ResultSet rs = stmt.executeQuery( "SELECT id, descricao, titulo FROM top10;" );

         while( rs.next() ){
            Top10 t = mapeiaTop10( rs );
            top10.add( t );
         }

      }
      catch( SQLException e ){
         e.printStackTrace();
      }
      return top10;
   }


   private Top10 mapeiaTop10( ResultSet rs ) throws SQLException {
      return new Top10( rs.getInt( "id" ), rs.getString( "titulo" ), rs.getString( "descricao" ) );
   }

}
