package com.mytop10.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mytop10.modelo.Musica;
import com.mytop10.modelo.MusicaTop10;

public class MusicaTop10DAO {
   public void salvarTop10( int id, List<Musica> musicas ) throws SQLException {
      DatabaseConnection instance = DatabaseConnection.getInstance();

      deleteAllMusicasInTop10( id, instance );

      if( musicas == null || musicas.isEmpty() ){
         return;
      }

      String sql = "INSERT INTO musica_top10 (ID_musica, ID_top10, ordem_musica) VALUES (?, ?, ?);";

      try( PreparedStatement ps = instance.prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS )){
         for( Musica musica : musicas ){
            ps.setString( 1, musica.getId() );
            ps.setInt( 2, id );
            ps.setInt( 3, musica.getOrdem() );
            ps.executeUpdate();
         }
      }
      catch( SQLException e ){
         e.printStackTrace();
      }
   }


   private void deleteAllMusicasInTop10( int id, DatabaseConnection instance ) {
      String sql = "DELETE FROM musica_top10 WHERE ID_top10 = ?;";

      try( PreparedStatement ps = instance.prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS )){
         ps.setInt( 1, id );
         ps.execute();
      }
      catch( SQLException e ){
         e.printStackTrace();
      }

   }


   public List<Musica> getMusicasTop10( Integer chave ) {
      List<Musica> resultado = new ArrayList<>();
      String sql = "SELECT mt.ordem_musica as ordem, mt.id_musica AS musica " + "FROM musica_top10 mt " + "WHERE mt.ID_top10 = ?";

      DatabaseConnection instance = DatabaseConnection.getInstance();

      try( PreparedStatement ps = instance.prepareStatement( sql, PreparedStatement.NO_GENERATED_KEYS )){

         ps.setInt( 1, chave );
         try( ResultSet rs = ps.executeQuery()){
            while( rs.next() ){
               Musica item = new Musica();
               item.setId( rs.getString( "musica" ) );
               item.setOrdem( rs.getInt( "ordem" ) );
               resultado.add( item );
            }
         }

      }
      catch( SQLException e ){
         e.printStackTrace();
      }

      return resultado;
   }
}
