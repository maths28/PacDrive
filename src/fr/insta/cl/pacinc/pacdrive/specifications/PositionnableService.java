package fr.insta.cl.pacinc.pacdrive.specifications;

import fr.insta.cl.pacinc.pacdrive.tools.Position;

public interface PositionnableService {

    //getters and setters
    public double getPositionX();

    public void setPositionX(double x);

    public double getPositionY();

    public void setPositionY(double y);

    public double getHauteur();

    public void setHauteur(int hauteur);

    public double getLargeur();

    public void setLargeur(int largeur);

    //combined setter
    public void setPosition(double x, double y);

    public Position getPosition();

    public void setPosition(Position position);


}
