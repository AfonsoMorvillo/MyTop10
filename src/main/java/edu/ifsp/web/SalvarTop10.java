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
      Map<String, Object> receivedData = gson.fromJson( jsonData, new TypeToken<Map<String, Object>>() {
      }.getType() );

      String teste = (String)receivedData.get( "top10Id" );
      int top10Id = Integer.parseInt( teste );

      List<Musica> musicas = gson.fromJson( gson.toJson( receivedData.get( "items" ) ), new TypeToken<List<Musica>>() {
      }.getType() );

      HttpSession session = request.getSession();

      Map<Integer, List<Musica>> carrinho = (Map<Integer, List<Musica>>)session.getAttribute( "top10" );

      if( carrinho == null ){
         carrinho = new HashMap<>();
      }

      MusicaTop10DAO musicaTop10DAO = new MusicaTop10DAO();


      try{
         musicaTop10DAO.salvarTop10( top10Id, musicas );
      }
      catch( SQLException e ){
         e.printStackTrace();
      }

      for( Musica musica : musicas ){

         carrinho.putIfAbsent( top10Id, new ArrayList<>() );

         carrinho.get( top10Id ).add( musica );
      }

      session.setAttribute( "top10", carrinho );

      response.setStatus( HttpServletResponse.SC_OK );
   }

}
