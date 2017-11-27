package fr.insta.cl.pacinc.pacdrive.data.model;

public class Joueur extends Movable {
	
	private int health ;
	

	private int munition ;
	
	public Joueur() {
		super();
	}
	
	public Joueur(int health, int munition) {
		super();
		this.health = health;
		this.munition = munition;
	}
	
	public Joueur(int health, int munition, int x, int y) {
		super();
		this.health = health;
		this.munition = munition;
		this.position.x = x;
		this.position.y = y;
	}

	//getters and setters
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMunition() {
		return munition;
	}

	public void setMunition(int munition) {
		this.munition = munition;
	}

}
