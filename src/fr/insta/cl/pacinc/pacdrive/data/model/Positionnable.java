package fr.insta.cl.pacinc.pacdrive.data.model;

import fr.insta.cl.pacinc.pacdrive.specifications.MovableService;
import fr.insta.cl.pacinc.pacdrive.specifications.PositionnableService;
import fr.insta.cl.pacinc.pacdrive.tools.Position;

public abstract class Positionnable implements PositionnableService{

	public Position position = new Position(0,0) ;
	public double hauteur, largeur;

	public Positionnable(Position p){
		this.position = p;
	}


	//getters and setters
	public double getPositionX() {
		return position.x;
	}
	public void setPositionX(double x) {
		this.position.x = x;
	}
	public double getPositionY() {
		return position.y;
	}
	public void setPositionY(double y) {
		this.position.y = y;
	}

	public double getHauteur() {
		return hauteur;
	}

	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}

	public double getLargeur() {
		return largeur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	//combined setter
	public void setPosition(double x, double y) {
		this.position.x = x;
		this.position.y = y;
	}



}
