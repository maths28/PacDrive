package fr.insta.cl.pacinc.pacdrive.data.model;

import fr.insta.cl.pacinc.pacdrive.specifications.KitService;
import fr.insta.cl.pacinc.pacdrive.tools.Position;

public class Kit extends Positionnable implements KitService {

    public Kit(Position p) {
        super(p);
    }
}
