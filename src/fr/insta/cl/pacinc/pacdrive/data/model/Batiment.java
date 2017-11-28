package fr.insta.cl.pacinc.pacdrive.data.model;

import fr.insta.cl.pacinc.pacdrive.specifications.BatimentService;
import fr.insta.cl.pacinc.pacdrive.tools.HardCodedParameters;
import fr.insta.cl.pacinc.pacdrive.tools.Position;

public class Batiment extends Positionnable implements BatimentService{

    public Batiment(Position p) {
        super(p);
    }

    @Override
    protected void initSize() {
        hauteur = HardCodedParameters.BATIMENT_SIZE_X;
        largeur = HardCodedParameters.BATIMENT_SIZE_Y;
    }
}
