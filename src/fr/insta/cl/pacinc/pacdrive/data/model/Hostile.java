package fr.insta.cl.pacinc.pacdrive.data.model;

import fr.insta.cl.pacinc.pacdrive.specifications.HostileService;
import fr.insta.cl.pacinc.pacdrive.tools.Acceleration;
import fr.insta.cl.pacinc.pacdrive.tools.HardCodedParameters;
import fr.insta.cl.pacinc.pacdrive.tools.Position;
import fr.insta.cl.pacinc.pacdrive.tools.Vitesse;

public class Hostile extends Movable implements HostileService {


    private String comportement;
    private int timer_changt_dir;
    private int timer_detect_vir;
    private boolean freeToMove;

    public Hostile(Position p, Vitesse v, Acceleration a, String comportement) {
        super(p);
        this.vitesse = v;
        this.acceleration = a;
        this.comportement = comportement;
        //this.timer = HardCodedParameters.TIMER_HOSTILE;
        this.timer_changt_dir = 0;
        this.timer_detect_vir = 0;
        this.freeToMove = false;
    }

    public Hostile(Position p) {
        super(p);

    }

    @Override
    protected void initSize() {
        hauteur = HardCodedParameters.HOSTILE_SIZE_X;
        largeur = HardCodedParameters.HOSTILE_SIZE_Y;
    }

    public String getComportement() {
        return comportement;
    }

    public void setComportement(String comportement) {
        this.comportement = comportement;
    }

    @Override
    public int getTimerChangementDirection() {
        return timer_changt_dir;
    }

    @Override
    public void setTimerChangementDirection(int timer_changt_dir) {
        if (timer_changt_dir <= 0) {
            this.timer_changt_dir = 0;
        } else {
            this.timer_changt_dir = timer_changt_dir;
        }
    }

    @Override
    public int getTimerDetectionVirage() {
        return timer_detect_vir;
    }

    @Override
    public void setTimerDetectionVirage(int timer_detect_vir) {
        if (timer_detect_vir <= 0) {
            this.timer_detect_vir = 0;
        } else {
            this.timer_detect_vir = timer_detect_vir;
        }
    }

    @Override
    public boolean getFreeToMove() {
        return freeToMove;
    }

    @Override
    public void setFreeToMove(boolean freeToMove) {
        this.freeToMove = freeToMove;
    }
}
