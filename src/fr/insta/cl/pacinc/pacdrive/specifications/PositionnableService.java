package fr.insta.cl.pacinc.pacdrive.specifications;

import fr.insta.cl.pacinc.pacdrive.tools.Position;

public interface PositionnableService {

    //getters and setters
    public double getPositionX();
    public void setPositionX(double x);
    public double getPositionY();
    public void setPositionY(double y);

    //combined setter
    public void setPosition(double x, double y);

    public Position getPosition();

    public void setPosition(Position position);
}
