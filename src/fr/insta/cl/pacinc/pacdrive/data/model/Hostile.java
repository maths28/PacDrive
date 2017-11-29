package fr.insta.cl.pacinc.pacdrive.data.model;

import fr.insta.cl.pacinc.pacdrive.specifications.HostileService;
import fr.insta.cl.pacinc.pacdrive.tools.Acceleration;
import fr.insta.cl.pacinc.pacdrive.tools.HardCodedParameters;
import fr.insta.cl.pacinc.pacdrive.tools.Position;
import fr.insta.cl.pacinc.pacdrive.tools.Vitesse;

public class Hostile extends Movable implements HostileService {
	
	public String comportement ;
	private int timer;

	public Hostile(Position p, Vitesse v, Acceleration a, String comportement){
		super(p);
		this.vitesse = v;
		this.acceleration = a;
		this.comportement = comportement;
		this.timer = HardCodedParameters.TIMER_HOSTILE;
	}

    public Hostile(Position p) {
        super(p);

    }

    @Override
	public int getTimer() {
		return timer;
	}

	@Override
	public void setTimer(int timer) {
		if (timer <= 0) {
			this.timer = 0;
		} else {
			this.timer = timer;
		}

	}

	@Override
	protected void initSize() {
		hauteur = HardCodedParameters.HOSTILE_SIZE_X;
		largeur = HardCodedParameters.HOSTILE_SIZE_Y;
	}
}
