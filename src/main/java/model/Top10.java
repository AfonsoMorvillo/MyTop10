package model;

import java.util.List;

public class Top10 {
	private String titulo;
	private String descricao;
	private List<Musica> musicas;
	
	public Top10() {
	   
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<Musica> getMusicas() {
		return musicas;
	}

	public void setMusicas(List<Musica> musicas) {
		this.musicas = musicas;
	}

	public Top10(String descricao, String titulo) {
		this.descricao = descricao;
		this.titulo = titulo;
	}
	
}
