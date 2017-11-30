package fr.insta.cl.pacinc.pacdrive.specifications;

public interface HostileService extends MovableService{
    public String getComportement();
    public void setComportement(String comportement);

    public int getTimerChangementDirection();
    public void setTimerChangementDirection(int timer);
    public int getTimerDetectionVirage();
    public void setTimerDetectionVirage(int timer_detect_vir);
    public boolean getFreeToMove();
    public void setFreeToMove(boolean freeToMove);
}
