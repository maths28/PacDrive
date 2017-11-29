package fr.insta.cl.pacinc.pacdrive.specifications;

public interface HostileService extends MovableService{
    public int getTimer();
    public void setTimer(int timer);

    public String getComportement();
    public void setComportement(String comportement);
}
