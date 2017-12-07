package fr.insta.cl.pacinc.pacdrive.data.model;

import fr.insta.cl.pacinc.pacdrive.specifications.PieceService;
import fr.insta.cl.pacinc.pacdrive.tools.HardCodedParameters;
import fr.insta.cl.pacinc.pacdrive.tools.Position;

public class Piece extends Positionnable implements PieceService {

    public Piece(Position p) {
        super(p);
    }

    @Override
    protected void initSize() {
        hauteur = HardCodedParameters.PIECE_SIZE_X;
        largeur = HardCodedParameters.PIECE_SIZE_Y;
    }
}
