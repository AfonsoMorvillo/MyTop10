package com.mytop10.modelo;

import java.util.List;

public class Top10 {
   private int          id;
   private String       titulo;
   private String       descricao;
   private List<Musica> musicas;

   public Top10() {
   }


   public Top10( int id, String titulo, String descricao ) {
      this.id = id;
      this.titulo = titulo;
      this.descricao = descricao;
   }


   public Top10( String descricao, String titulo ) {
      this.descricao = descricao;
      this.titulo = titulo;
   }


   public int getId() {
      return id;
   }


   public void setId( int id ) {
      this.id = id;
   }


   public String getDescricao() {
      return descricao;
   }


   public void setDescricao( String descricao ) {
      this.descricao = descricao;
   }


   public String getTitulo() {
      return titulo;
   }


   public void setTitulo( String titulo ) {
      this.titulo = titulo;
   }


   public List<Musica> getMusicas() {
      return musicas;
   }


   public void setMusicas( List<Musica> musicas ) {
      this.musicas = musicas;
   }

}
