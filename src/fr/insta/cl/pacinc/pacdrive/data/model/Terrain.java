package fr.insta.cl.pacinc.pacdrive.data.model;

import java.util.ArrayList;
import java.util.List;

public class Terrain {
	
	private int xSize;
	private int ySize;
	
	//Tableau à 2 dimension de Positionnable
	private List<List<Positionnable>> quadrillage ;
	
	public void init() {
		quadrillage = new ArrayList<List<Positionnable>>();
		for(int i = 0; i <= xSize; i++) {
			quadrillage.add(new ArrayList<Positionnable>());
		}
		for(int i = 0; i <= xSize; i++) {
			for(int j = 0; j <= xSize; j++) {
				quadrillage.get(i).set(j, null);
			}
		}
	}

	
	//getters and setters
	public int getxSize() {
		return xSize;
	}

	public void setxSize(int xSize) {
		this.xSize = xSize;
	}

	public int getySize() {
		return ySize;
	}

	public void setySize(int ySize) {
		this.ySize = ySize;
	}
	
	public Positionnable getValue(int x, int y) {
		return quadrillage.get(x).get(y);
	}

}
