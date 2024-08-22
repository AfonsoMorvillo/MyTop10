package edu.ifsp.web;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import model.Musica;
import model.MusicaTop10DAO;

@WebListener
public class Top10Listener implements HttpSessionListener {

   public Top10Listener() {
   }


   /**
    * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
    */
   public void sessionDestroyed( HttpSessionEvent se ) {
      System.out.println( "sessionDestroyed" );
      HttpSession session = se.getSession();

      Map<Integer, List<Musica>> top10Map = (Map<Integer, List<Musica>>)session.getAttribute( "top10" );
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
