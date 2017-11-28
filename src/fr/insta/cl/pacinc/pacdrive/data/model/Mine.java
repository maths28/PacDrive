package fr.insta.cl.pacinc.pacdrive.data.model;

import fr.insta.cl.pacinc.pacdrive.specifications.MineService;
import fr.insta.cl.pacinc.pacdrive.tools.Position;

public class Mine extends Positionnable implements MineService {

    public Mine(Position p) {
        super(p);
        this.hauteur = 30;
        this.largeur = 30;
    }
}
