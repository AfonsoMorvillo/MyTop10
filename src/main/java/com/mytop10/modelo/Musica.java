package com.mytop10.modelo;

public class Musica {
   private String id;
   private int    ordem;
   private String capa;
   private String titulo;
   private String album;
   private String artista;

   public Musica() {
   }


   public String getAlbum() {
      return album;
   }


   public void setAlbum( String album ) {
      this.album = album;
   }


   public String getArtista() {
      return artista;
   }


   public void setArtista( String artista ) {
      this.artista = artista;
   }


   public Musica( String id, int ordem ) {
      this.id = id;
      this.ordem = ordem;
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


   public String getTitulo() {
      return titulo;
   }


   public void setTitulo( String titulo ) {
      this.titulo = titulo;
   }


   public int getOrdem() {
      return ordem;
   }


   public void setOrdem( int ordem ) {
      this.ordem = ordem;
   }

}
