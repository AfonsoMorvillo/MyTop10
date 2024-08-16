package model;

public class Musica {
	private String id;
	private int ordem;
	

	public Musica( String id, int ordem ) {
      this.id = id;
      this.ordem = ordem;
   }

   public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}
	

}
