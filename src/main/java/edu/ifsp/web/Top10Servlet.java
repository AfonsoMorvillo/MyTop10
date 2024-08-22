package edu.ifsp.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hc.core5.http.ParseException;

import edu.ifsp.web.templates.Template;
import model.Musica;
import model.MusicaTop10;
import model.MusicaTop10DAO;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
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


   private void setMusicas( Map<Integer, Musica> mapa, int chave ) {

      List<MusicaTop10> listarMusicaTop10PorTitulo = new MusicaTop10DAO().listarMusicaTop10PorTitulo( chave );

      if (listarMusicaTop10PorTitulo.isEmpty()) {
         return;
      }
      
      List<String> musicasID = listarMusicaTop10PorTitulo.stream().map( m -> m.getMusica().getId() ).collect( Collectors.toList() );

//      SpotifyExample.clientCredentials_Sync();

      String trackIdsString = String.join( ",", musicasID ); // Converte a lista de IDs em uma string separada por vírgulas
      
      GetSeveralTracksRequest several = SpotifyExample.getSpotifyApi().getSeveralTracks( trackIdsString ).build();

      Track[] tracks;
      try{
         tracks = several.execute();

         for( Track track : tracks ){
            listarMusicaTop10PorTitulo.stream().forEach( m -> {
               Musica musica = m.getMusica();
               if( musica.getId().equals( track.getId() ) ){
                  musica.setId( track.getId() );
                  musica.setTitulo( track.getName() );
                  musica.setCapa( track.getAlbum().getImages()[ 0 ].getUrl() );
                  musica.setAlbum( track.getAlbum().getName());
                  musica.setArtista( track.getArtists()[0].getName() );
                  mapa.put( m.getMusica().getOrdem(), musica );
               }
            } );
            ;
         }
      }
      catch( Exception e ){
         e.printStackTrace();
      }

   }


   @Override
   protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
      doGet( request, response );
   }
}
