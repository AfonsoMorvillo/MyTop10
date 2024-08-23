package com.mytop10.web.command.top10;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mytop10.dao.MusicaTop10DAO;
import com.mytop10.modelo.Musica;
import com.mytop10.web.Top10Session;
import com.mytop10.web.command.Command;

public class SalvarTop10 implements Command {

   @Override
   public void execute( HttpServletRequest request, HttpServletResponse response ) throws Exception {

      String jsonData = getStringBuilder( request ).toString();

      Gson gson = new Gson();
      Map<String, Object> dadosRequisicao = gson.fromJson( jsonData, new TypeToken<Map<String, Object>>() {
      }.getType() );

      int top10Id = Integer.parseInt( (String)dadosRequisicao.get( "top10Id" ) );

      List<Musica> musicas = gson.fromJson( gson.toJson( dadosRequisicao.get( "items" ) ), new TypeToken<List<Musica>>() {
      }.getType() );

      HttpSession session = request.getSession();
      Map<Integer, List<Musica>> mapTop10Musicas = Top10Session.getTop10MapSession( session );

      MusicaTop10DAO musicaTop10DAO = new MusicaTop10DAO();

      mapTop10Musicas.put( top10Id, musicas );

      // try{
      // musicaTop10DAO.salvarTop10( top10Id, musicas );
      // }
      // catch( SQLException e ){
      // e.printStackTrace();
      // }
      session.setAttribute( "top10", mapTop10Musicas );

      response.setStatus( HttpServletResponse.SC_OK );

   }


   private StringBuilder getStringBuilder( HttpServletRequest request ) throws IOException {
      BufferedReader reader = request.getReader();
      StringBuilder stringBuilder = new StringBuilder();
      String line;
      while( ( line = reader.readLine() ) != null ){
         stringBuilder.append( line );
      }
      reader.close();
      return stringBuilder;
   }

}
