package edu.ifsp.web;

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

public class SpotifyExample {
    private static final String clientId = "e588e7e675a54b1d8142a9ae06e99452";
    private static final String clientSecret = "d94f95f07cae45738016a5d6bdc89465";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/callback");

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();

    public static void clientCredentials_Sync() {
        try {
            ClientCredentials clientCredentials = spotifyApi.clientCredentials().build().execute();

            // Set access token to the Spotify API object
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        clientCredentials_Sync();
        try{
         searchTrackByName("Passionfruit");
      }
      catch(Exception e ){
         e.printStackTrace();
      }
    }
    
    private static void searchTrackByName(String trackName) throws IOException, SpotifyWebApiException, ParseException {
       SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(trackName).build();

       final Paging<Track> trackPaging = searchTracksRequest.execute();

       // Imprimindo as informações da primeira música encontrada
       if (trackPaging.getItems().length > 0) {
           Track track = trackPaging.getItems()[0];
           System.out.println("Música encontrada: " + track.getName());
           System.out.println("Artista: " + track.getArtists()[0].getName());
           System.out.println("Álbum: " + track.getAlbum().getName());
           System.out.println("URL: " + track.getExternalUrls().get("spotify"));
           String imageUrl = track.getAlbum().getImages()[0].getUrl();
           System.out.println("Imagem da capa: " + imageUrl);
       } else {
           System.out.println("Nenhuma música encontrada com o nome: " + trackName);
       }
   }
}

