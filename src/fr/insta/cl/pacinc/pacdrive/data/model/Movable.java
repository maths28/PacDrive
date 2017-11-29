package fr.insta.cl.pacinc.pacdrive.data.model;

import fr.insta.cl.pacinc.pacdrive.specifications.MovableService;
import fr.insta.cl.pacinc.pacdrive.tools.Acceleration;
import fr.insta.cl.pacinc.pacdrive.tools.Position;
import fr.insta.cl.pacinc.pacdrive.tools.Vitesse;

public abstract class Movable extends Positionnable implements MovableService {
	public Movable(Position p) {
		super(p);
	}

	//Vitesse
	
	public Vitesse vitesse = new Vitesse(0,0) ;
	
	//getters and setters
	public double getVitesseX() {
		return vitesse.x;
	}
	public void setVitesseX(double x) {
		this.vitesse.x = x;
	}
	public double getVitesseY() {
		return vitesse.y;
	}
	public void setVitesseY(double y) {
		this.vitesse.y = y;
	}
	
	//combined setter
	public void setVitesse(double x, double y) {
		this.vitesse.x = x;
		this.vitesse.y = y;
	}
	
	
		//Acceleration
	
	public Acceleration acceleration = new Acceleration(0,0) ;
	
	//getters and setters
	public double getAccelerationX() {
		return acceleration.x;
	}
	public void setAccelerationX(double x) {
		this.acceleration.x = x;
	}
	public double getAccelerationY() {
		return acceleration.y;
	}
	public void setAccelerationY(double y) {
		this.acceleration.y = y;
	}
	
	//combined setter
	public void setAcceleration(double x, double y) {
		this.acceleration.x = x;
		this.acceleration.y = y;
	}

	@Override
	public Vitesse getVitesse() {
		return vitesse;
	}

	@Override
	public Acceleration getAcceleration() {
		return acceleration;
	}

	@Override
	public void setVitesse(Vitesse v) {
		this.vitesse = v;
	}

	@Override
	public void setAcceleration(Acceleration a) {
		this.acceleration = a;
	}


	@Override
	public String getDirection() {
		if(Math.abs(vitesse.x) < Math.abs(vitesse.y)){
			if(vitesse.y < 0){
				return "up" ;
			}
			else {
				return "down";
			}
		}
		else {
			if(vitesse.x < 0){
				return "left" ;
			}
			else {
				return "right";
			}
		}
	}

	@Override
	public double getHauteur() {
		if (getDirection() == "right" || getDirection() == "left") return largeur;
		return hauteur;
	}

	@Override
	public double getLargeur() {
		if (getDirection() == "right" || getDirection() == "left") return hauteur;
		return largeur;
	}
}
