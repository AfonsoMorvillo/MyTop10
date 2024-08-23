package com.mytop10.spotify;

import java.io.IOException;
import java.net.URI;

import org.apache.hc.core5.http.ParseException;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

public class SpotifyApiSingleton {
   private static final String     clientId     = "e588e7e675a54b1d8142a9ae06e99452";
   private static final String     clientSecret = "d94f95f07cae45738016a5d6bdc89465";
   private static final URI        redirectUri  = SpotifyHttpManager.makeUri( "http://localhost:8080/callback" );

   private static final SpotifyApi spotifyApi   = new SpotifyApi.Builder().setClientId( clientId ).setClientSecret( clientSecret ).setRedirectUri( redirectUri ).build();

   public static SpotifyApi getSpotifyApi() {
      return spotifyApi;
   }


   public static void sincronizaCredencials() {
      try{
         ClientCredentials credentials = spotifyApi.clientCredentials().build().execute();

         spotifyApi.setAccessToken( credentials.getAccessToken() );

         System.out.println( "Tempo:" + credentials.getExpiresIn() );
      }
      catch( IOException | SpotifyWebApiException | ParseException e ){
         e.printStackTrace();
      }
   }

}
