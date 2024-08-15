package model;

import java.util.HashMap;

public class MusicaTop10 {
	private int id;
	private HashMap<Integer, Musica> listaMuscias;
	private Top10 top10;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HashMap<Integer, Musica> getListaMuscias() {
		return listaMuscias;
	}

	public void setListaMuscias(HashMap<Integer, Musica> listaMuscias) {
		this.listaMuscias = listaMuscias;
	}

	public Top10 getTop10() {
		return top10;
	}

	public void setTop10(Top10 top10) {
		this.top10 = top10;
	}

}
