package com.mytop10.web.command.musica;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hc.core5.http.ParseException;
import org.json.JSONObject;

import com.mytop10.modelo.Musica;
import com.mytop10.spotify.SpotifyApiSingleton;
import com.mytop10.web.command.Command;

import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

public class BuscarMusica implements Command {

   @Override
   public void execute( HttpServletRequest request, HttpServletResponse response ) throws Exception {
      response.setContentType( "application/json" );
      response.setCharacterEncoding( "UTF-8" );

      String musicaBusca = request.getParameter( "musicaBusca" );

      Musica musicaInfo = buscarMusica( musicaBusca );

      JSONObject jsonResponse = new JSONObject();
      jsonResponse.put( "nome", musicaInfo.getTitulo() );
      jsonResponse.put( "capa", musicaInfo.getCapa() );
      jsonResponse.put( "id", musicaInfo.getId() );
      jsonResponse.put( "album", musicaInfo.getAlbum() );
      jsonResponse.put( "artista", musicaInfo.getArtista() );

      response.getWriter().print( jsonResponse.toString() ); // Envia json resposta
      response.getWriter().flush();
   }


   private Musica buscarMusica( String musicaBusca ) throws ParseException, SpotifyWebApiException, IOException {
      SearchTracksRequest buscarMusicas = SpotifyApiSingleton.getSpotifyApi().searchTracks( musicaBusca ).build();

      final Paging<Track> musicas = buscarMusicas.execute();
      if( musicas.getItems().length > 0 ){
         Track musica = musicas.getItems()[ 0 ]; // retorna a primeira musica

         Musica musicaInfo = new Musica();
         musicaInfo.setId( musica.getId() );
         musicaInfo.setTitulo( musica.getName() );
         musicaInfo.setCapa( musica.getAlbum().getImages()[ 0 ].getUrl() );
         musicaInfo.setAlbum( musica.getAlbum().getName() );
         musicaInfo.setArtista( musica.getArtists()[ 0 ].getName() );
         return musicaInfo;
      }
      else{
         return new Musica();
      }
   }

}
