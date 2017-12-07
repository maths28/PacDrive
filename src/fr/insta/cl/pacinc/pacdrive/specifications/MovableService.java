package fr.insta.cl.pacinc.pacdrive.specifications;

import fr.insta.cl.pacinc.pacdrive.tools.Acceleration;
import fr.insta.cl.pacinc.pacdrive.tools.Vitesse;

public interface MovableService extends PositionnableService {

    //getters and setters
    public double getVitesseX();

    public void setVitesseX(double x);

    public double getVitesseY();

    public void setVitesseY(double y);

    //combined setter
    public void setVitesse(double x, double y);

    //getters and setters
    public double getAccelerationX();

    public void setAccelerationX(double x);

    public double getAccelerationY();

    public void setAccelerationY(double y);

    //combined setter
    public void setAcceleration(double x, double y);

    public Vitesse getVitesse();

    public Acceleration getAcceleration();

    public void setVitesse(Vitesse v);

    public void setAcceleration(Acceleration a);

    public String getDirection();

}
