package fr.insta.cl.pacinc.pacdrive.data.model;

import fr.insta.cl.pacinc.pacdrive.tools.HardCodedParameters;
import fr.insta.cl.pacinc.pacdrive.tools.Position;

public class Joueur extends Movable {
	
	private int health ;
	private int munition ;
	
	public Joueur(Position p) {
		super(p);

	}

	@Override
	protected void initSize() {
		hauteur = HardCodedParameters.HERO_SIZE_X;
		largeur = HardCodedParameters.HERO_SIZE_Y;
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
