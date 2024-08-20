package edu.ifsp.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.Musica;
import model.MusicaTop10DAO;

@WebServlet( "/salvar" )
public class SalvarTop10 extends HttpServlet {
   private static final long serialVersionUID = 1L;

   protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
      BufferedReader reader = request.getReader();
      StringBuilder stringBuilder = new StringBuilder();
      String line;
      while( ( line = reader.readLine() ) != null ){
         stringBuilder.append( line );
      }
      reader.close();

      String jsonData = stringBuilder.toString();

      Gson gson = new Gson();
      List<CardInfo> cardData = gson.fromJson( jsonData, new TypeToken<List<CardInfo>>() {
      }.getType() );

      HttpSession session = request.getSession();

      Map<Integer, List<CardInfo>> carrinho = (Map<Integer, List<CardInfo>>)session.getAttribute( "top10" );

      if( carrinho == null ){
         carrinho = new HashMap<>();
      }

      int top10 = cardData.get( 0 ).getTop10Id();

      MusicaTop10DAO musicaTop10DAO = new MusicaTop10DAO();

      ArrayList<Musica> musicasArray = new ArrayList<Musica>();

      for( CardInfo musica : cardData ){
         musicasArray.add( new Musica( musica.getId(), musica.getPosicao() ) );
      }

      try{
         musicaTop10DAO.salvarTop10( top10, musicasArray );
      }
      catch( SQLException e ){
         e.printStackTrace();
      }

      for( CardInfo card : cardData ){
         int top10Id = card.getTop10Id(); // Obtém o ID da URL

         carrinho.putIfAbsent( top10Id, new ArrayList<>() );

         carrinho.get( top10Id ).add( card );
      }

      session.setAttribute( "top10", carrinho );

      for( CardInfo card : cardData ){
         System.out.println( "Card ID: " + card.getId() + ", Posição: " + card.getPosicao() + ", Top10 ID: " + card.getTop10Id() );
      }

      response.setStatus( HttpServletResponse.SC_OK );
   }

   private class CardInfo {
      private String id;
      private int    posicao; // Novo atributo para posição
      private int    top10Id; // Novo atributo para ID do Top 10

      public String getId() {
         return id;
      }


      public void setId( String id ) {
         this.id = id;
      }


      public int getPosicao() {
         return posicao;
      }


      public void setPosicao( int posicao ) {
         this.posicao = posicao;
      }


      public int getTop10Id() {
         return top10Id;
      }


      public void setTop10Id( int top10Id ) {
         this.top10Id = top10Id;
      }
   }
}
