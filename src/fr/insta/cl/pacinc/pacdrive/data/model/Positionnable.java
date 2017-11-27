package fr.insta.cl.pacinc.pacdrive.data.model;

import fr.insta.cl.pacinc.pacdrive.tools.Position;

public abstract class Positionnable {
	
	public Position position = new Position(0,0) ;
	
	//getters and setters
	public double getX() {
		return position.x;
	}
	public void setX(double x) {
		this.position.x = x;
	}
	public double getY() {
		return position.y;
	}
	public void setY(double y) {
		this.position.y = y;
	}
	
	//combined setter
	public void setPosition(double x, double y) {
		this.position.x = x;
		this.position.y = y;
	}
	

}
