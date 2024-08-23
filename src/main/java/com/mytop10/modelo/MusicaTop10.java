package com.mytop10.modelo;

public class MusicaTop10 {
   private Musica musica;
   private Top10  top10;

   public MusicaTop10() {
      this.musica = new Musica();
      this.top10 = new Top10();
   }


   public MusicaTop10( Musica musica, Top10 top10 ) {
      this.musica = musica;
      this.top10 = top10;
   }


   public Musica getMusica() {
      return musica;
   }


   public void setMusica( Musica musica ) {
      this.musica = musica;
   }


   public Top10 getTop10() {
      return top10;
   }


   public void setTop10( Top10 top10 ) {
      this.top10 = top10;
   }

}
