package fr.insta.cl.pacinc.pacdrive.data.model;

import fr.insta.cl.pacinc.pacdrive.specifications.MineService;
import fr.insta.cl.pacinc.pacdrive.tools.HardCodedParameters;
import fr.insta.cl.pacinc.pacdrive.tools.Position;

public class Mine extends Positionnable implements MineService {

    public Mine(Position p) {
        super(p);
    }


    protected void initSize() {
        hauteur = HardCodedParameters.MINE_SIZE_X;
        largeur = HardCodedParameters.MINE_SIZE_Y;

    }
}
