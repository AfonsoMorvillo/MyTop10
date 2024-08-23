package com.mytop10.web;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import com.mytop10.dao.MusicaTop10DAO;
import com.mytop10.modelo.Musica;

public class Top10Session {

   public static Map<Integer, List<Musica>> getTop10MapSession( HttpSession session ) {
      Map<Integer, List<Musica>> map = (Map<Integer, List<Musica>>)session.getAttribute( "top10" );
      return map == null ? new HashMap<>() : map;
   }


   public static void saveTop10Session( HttpSession session ) {
      Map<Integer, List<Musica>> top10Map = getTop10MapSession( session );
      if( top10Map == null ){
         Logger.getGlobal().info( "Nenhum top 10 para salvar." );
         return;
      }

      MusicaTop10DAO musicaTop10DAO = new MusicaTop10DAO();
      top10Map.forEach( ( idTop10, musicas ) -> {
         try{
            musicaTop10DAO.salvarTop10( idTop10, musicas );
         }
         catch( SQLException e ){
            e.printStackTrace();
         }
      } );
   }

}
