package com.mytop10.web.command.top10;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mytop10.dao.MusicaTop10DAO;
import com.mytop10.dao.Top10DAO;
import com.mytop10.modelo.Musica;
import com.mytop10.modelo.MusicaTop10;
import com.mytop10.spotify.SpotifyApiSingleton;
import com.mytop10.web.Top10Session;
import com.mytop10.web.command.Command;
import com.mytop10.web.templates.Template;

import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.tracks.GetSeveralTracksRequest;

public class EditarTop10 implements Command {

   @Override
   public void execute( HttpServletRequest request, HttpServletResponse response ) throws Exception {
      String[] caminho = request.getPathInfo().split( "/" );
      int idTop10 = Integer.parseInt( caminho[ caminho.length - 1 ] );

      Map<Integer, Musica> musicasMapa = new HashMap<>();

      for( int i = 1; i <= 10; i++ ){ // Mapa com 10 chaves definindo a ordem do top 10
         musicasMapa.put( i, null );
      }

      List<Musica> musicasDoTop10 = getMusicasSessao( request, idTop10 );

      //Se o top 10 nao estiver salvo na sessao busca no banco
      if( musicasDoTop10 == null ){

         boolean existe = new Top10DAO().existeTop10( idTop10 );

         if( !existe ){
            response.sendRedirect( request.getContextPath() + "/top10/novo" );
            return;
         }

         musicasDoTop10 = new MusicaTop10DAO().getMusicasTop10( idTop10 );

      }

      setMusicas( musicasMapa, idTop10, musicasDoTop10 );

      request.setAttribute( "top10musicas", musicasMapa );

      Template.render( "top10/top10", request, response );
   }


   private List<Musica> getMusicasSessao( HttpServletRequest request, int idTop10 ) {
      Map<Integer, List<Musica>> mapTop10Musicas = Top10Session.getTop10MapSession( request.getSession() );

      return mapTop10Musicas.get( idTop10 );
   }


   private void setMusicas( Map<Integer, Musica> musicasMapa, int chave, List<Musica> musicasDoTop10 ) {

      // List<MusicaTop10> musicasDoTop10 = new MusicaTop10DAO().listarMusicaTop10PorTitulo( chave );

      if( musicasDoTop10.isEmpty() ){
         return;
      }

      List<String> musicasID = musicasDoTop10.stream().map( m -> m.getId() ).collect( Collectors.toList() );

      String trackIdsString = String.join( ",", musicasID );

      GetSeveralTracksRequest several = SpotifyApiSingleton.getSpotifyApi().getSeveralTracks( trackIdsString ).build();

      Track[] tracks;
      try{
         tracks = several.execute();

         for( Track track : tracks ){
            musicasDoTop10.stream().forEach( musica -> {
               if( musica.getId().equals( track.getId() ) ){
                  musica.setId( track.getId() );
                  musica.setTitulo( track.getName() );
                  musica.setCapa( track.getAlbum().getImages()[ 0 ].getUrl() );
                  musica.setAlbum( track.getAlbum().getName() );
                  musica.setArtista( track.getArtists()[ 0 ].getName() );
                  musicasMapa.put( musica.getOrdem(), musica );
               }
            } );
            ;
         }
      }
      catch( Exception e ){
         e.printStackTrace();
      }

   }

}