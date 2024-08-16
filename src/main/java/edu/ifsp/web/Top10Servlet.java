package edu.ifsp.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ifsp.web.templates.Template;
import model.Musica;
import model.MusicaTop10;
import model.MusicaTop10DAO;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.tracks.GetSeveralTracksRequest;

@WebServlet( "/top10/*" )
public class Top10Servlet extends HttpServlet {

   @Override
   protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
      String pathInfo = request.getPathInfo();
      int id = 0;

      if( pathInfo != null && pathInfo.length() > 1 ){
         String ide = pathInfo.substring( 1 );
         id = Integer.parseInt( ide );
      }

      // request.getSession().setAttribute( "teste", "teste" );

      Map<Integer, Musica> mapa = new HashMap<>();

      for( int i = 1; i <= 10; i++ ){
         mapa.put( i, null );
      }

      setMusicas( mapa, id );

      request.setAttribute( "mapa", mapa );

      Template.render( "top10/index", request, response );
   }


   private ArrayList<MusicaInfo> setMusicas( Map<Integer, Musica> mapa, int chave ) {

      List<MusicaTop10> listarMusicaTop10PorTitulo = new MusicaTop10DAO().listarMusicaTop10PorTitulo( chave );
      List<String> tracks = new ArrayList<>();

      // SpotifyExample.clientCredentials_Sync();

      String trackIdsString = String.join( ",", tracks ); // Converte a lista de IDs em uma string separada por vírgulas
      GetSeveralTracksRequest several = SpotifyExample.getSpotifyApi().getSeveralTracks( trackIdsString ).build();

      Track[] track = several.execute();

      // Prepara as informações da música para enviar como JSON

      ArrayList<MusicaInfo> musicas = new ArrayList<>();

      for( Track track2 : track ){

         MusicaInfo musicaInfo = new MusicaInfo();

         musicaInfo.setId( track2.getId() );
         musicaInfo.setNome( track2.getName() );
         musicaInfo.setCapa( track2.getAlbum().getImages()[ 0 ].getUrl() );
         musicas.add( musicaInfo );
      }
      
      for( MusicaInfo musi : musicas ){
//         tracks.add( musica.getMusica().getId() );
         // int ordem = musica.getMusica().getOrdem();
         //
         // mapa.put( ordem, musica.getMusica() );
      }
      

      return musicas;


   }

   private class MusicaInfo {
      private String nome;
      private String capa;
      private String id;

      public String getNome() {
         return nome;
      }


      public void setNome( String nome ) {
         this.nome = nome;
      }


      public String getId() {
         return id;
      }


      public void setId( String id ) {
         this.id = id;
      }


      public String getCapa() {
         return capa;
      }


      public void setCapa( String capa ) {
         this.capa = capa;
      }
   }

   @Override
   protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
      doGet( request, response );
   }
}
