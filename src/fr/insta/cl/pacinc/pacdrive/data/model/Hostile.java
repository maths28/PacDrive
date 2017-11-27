package fr.insta.cl.pacinc.pacdrive.data.model;

public class Hostile extends Movable {
	
	public String comportement ;
	
	public Hostile(String comportement) {
		super();
		this.comportement = comportement ;
	}
	
    public Hostile(String comportement, int x, int y) {
		super();
		this.position.x = x;
		this.position.y = y;
		this.comportement = comportement ;
	}
	

}
