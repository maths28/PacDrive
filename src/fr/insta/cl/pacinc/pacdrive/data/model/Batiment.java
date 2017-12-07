package fr.insta.cl.pacinc.pacdrive.data.model;

import fr.insta.cl.pacinc.pacdrive.specifications.BatimentService;
import fr.insta.cl.pacinc.pacdrive.tools.HardCodedParameters;
import fr.insta.cl.pacinc.pacdrive.tools.Position;

public class Batiment extends Positionnable implements BatimentService {

    private int variation = 1;

    public Batiment(Position p) {
        super(p);
    }

    public Batiment(Position p, int variation) {
        super(p);
        this.variation = variation;
    }

    @Override
    protected void initSize() {
        hauteur = HardCodedParameters.BATIMENT_SIZE_X;
        largeur = HardCodedParameters.BATIMENT_SIZE_Y;
    }

    @Override
    public int getVariation() {
        return variation;
    }

    @Override
    public void setVariation(int variation) {
        this.variation = variation;
    }
}
