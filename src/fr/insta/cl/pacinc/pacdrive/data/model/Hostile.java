package fr.insta.cl.pacinc.pacdrive.data.model;

import fr.insta.cl.pacinc.pacdrive.specifications.HostileService;
import fr.insta.cl.pacinc.pacdrive.tools.Acceleration;
import fr.insta.cl.pacinc.pacdrive.tools.Position;
import fr.insta.cl.pacinc.pacdrive.tools.Vitesse;

public class Hostile extends Movable implements HostileService {
	
	public String comportement ;

	public Hostile(Position p, Vitesse v, Acceleration a, String comportement){
		this.position = p;
		this.vitesse = v;
		this.acceleration = a;
		this.comportement = comportement;
	}
	
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
