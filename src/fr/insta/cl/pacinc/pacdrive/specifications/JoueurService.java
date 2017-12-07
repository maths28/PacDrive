package fr.insta.cl.pacinc.pacdrive.specifications;

public interface JoueurService extends MovableService {

    public int getHealth();

    public void setHealth(int health);

    public int getMunition();

    public void setMunition(int munition);
}
