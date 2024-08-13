package edu.ifsp.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hc.core5.http.ParseException;
import org.json.JSONObject;

import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

/**
 * Servlet implementation class BuscarMusica
 */
@WebServlet("/musica/buscar")
public class BuscarMusica extends HttpServlet {
   
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json"); // Define o tipo de conteúdo como JSON
        response.setCharacterEncoding("UTF-8"); // Define a codificação como UTF-8

        try {
            String musicaBusca = request.getParameter("musicaBusca");
            
            // Executa a busca da música
            MusicaInfo musicaInfo = buscarMusica(musicaBusca);
            
            // Cria um JSON Object para armazenar as informações da música
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("nome", musicaInfo.getNome());
            jsonResponse.put("capa", musicaInfo.getCapa());

            // Envia a resposta como JSON
            response.getWriter().print(jsonResponse.toString());
            response.getWriter().flush();
            
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private MusicaInfo buscarMusica(String musica) throws ParseException, SpotifyWebApiException, IOException {
       SpotifyExample.clientCredentials_Sync();
        SearchTracksRequest searchTracksRequest = SpotifyExample.getSpotifyApi().searchTracks(musica).build();
        final Paging<Track> trackPaging = searchTracksRequest.execute();

        if (trackPaging.getItems().length > 0) {
            Track track = trackPaging.getItems()[0];
            
            // Prepara as informações da música para enviar como JSON
            MusicaInfo musicaInfo = new MusicaInfo();
            musicaInfo.setNome(track.getName());
            musicaInfo.setCapa(track.getAlbum().getImages()[0].getUrl());
            return musicaInfo;
        } else {
            // Caso não encontre música, retorna um objeto vazio ou algum outro valor padrão
            return new MusicaInfo(); // Ou considere lançar uma exceção ou retornar um JSON com uma mensagem de erro
        }
    }

    // Classe auxiliar para armazenar informações da música
    private class MusicaInfo {
        private String nome;
        private String capa;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCapa() {
            return capa;
        }

        public void setCapa(String capa) {
            this.capa = capa;
        }
    }
}

