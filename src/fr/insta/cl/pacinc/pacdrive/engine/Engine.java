/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: engine/Engine.java 2015-03-11 buixuan.
 * ******************************************************/
package fr.insta.cl.pacinc.pacdrive.engine;

import java.util.Timer;
import java.util.TimerTask;

import fr.insta.cl.pacinc.pacdrive.data.model.Movable;
import fr.insta.cl.pacinc.pacdrive.data.model.Positionnable;
import fr.insta.cl.pacinc.pacdrive.specifications.*;
import fr.insta.cl.pacinc.pacdrive.tools.*;

import java.util.Random;
import java.util.ArrayList;

public class Engine implements EngineService, RequireDataService{
  private static final double friction=HardCodedParameters.friction,
                              heroesStep=HardCodedParameters.heroesStep,
                              phantomStep=HardCodedParameters.phantomStep;
  private Timer engineClock;
  private DataService data;
  private User.COMMAND command;
  private Random gen;
  private boolean moveLeft,moveRight,moveUp,moveDown;

  public Engine(){}

  @Override
  public void bindDataService(DataService service){
    data=service;
  }
  
  @Override
  public void init(){
    engineClock = new Timer();
    command = User.COMMAND.NONE;
    gen = new Random();
    moveLeft = false;
    moveRight = false;
    moveUp = false;
    moveDown = false;
  }

  @Override
  public void start(){
    engineClock.schedule(new TimerTask(){
      public void run() {
        //System.out.println("Game step #"+data.getStepNumber()+": checked.");
        
        if (gen.nextInt(10)<3) spawnHostiles();

        updateSpeedHeroes();
        updateCommandHeroes();
        updatePositionHeroes();

        ArrayList<HostileService> hostiles = new ArrayList<HostileService>();
        int score=0;

        data.setSoundEffect(Sound.SOUND.None);

        for (HostileService h:data.getHostiles()){
          if (true) move(h);

          if (collisionBetweenPositionnables(data.getJoueur(), h)){
            data.setSoundEffect(Sound.SOUND.HeroesGotHit);
            score++;
          } else {
            if (h.getPosition().x>0) hostiles.add(h);
          }
        }

        data.addScore(score);

        data.setHostiles(hostiles);

        data.setStepNumber(data.getStepNumber()+1);
      }
    },0,HardCodedParameters.enginePaceMillis);
  }

  @Override
  public void stop(){
    engineClock.cancel();
  }

  @Override
  public void setHeroesCommand(User.COMMAND c){
    if (c==User.COMMAND.LEFT) moveLeft=true;
    if (c==User.COMMAND.RIGHT) moveRight=true;
    if (c==User.COMMAND.UP) moveUp=true;
    if (c==User.COMMAND.DOWN) moveDown=true;
  }
  
  @Override
  public void releaseHeroesCommand(User.COMMAND c){
    if (c==User.COMMAND.LEFT) moveLeft=false;
    if (c==User.COMMAND.RIGHT) moveRight=false;
    if (c==User.COMMAND.UP) moveUp=false;
    if (c==User.COMMAND.DOWN) moveDown=false;
  }

  private void updateSpeedHeroes(){
    data.getJoueur().vitesse.x*=friction;
    data.getJoueur().vitesse.y*=friction;
  }

  private void updateCommandHeroes(){
    if (moveLeft) data.getJoueur().vitesse.x-=heroesStep;
    if (moveRight) data.getJoueur().vitesse.x+=heroesStep;
    if (moveUp) data.getJoueur().vitesse.y-=heroesStep;
    if (moveDown) data.getJoueur().vitesse.y+=heroesStep;
  }
  
  private void updatePositionHeroes(){
    data.setHeroesPosition(new Position(data.getHeroesPosition().x+data.getJoueur().vitesse.x,data.getHeroesPosition().y+data.getJoueur().vitesse.y));
    //if (data.getHeroesPosition().x<0) data.setHeroesPosition(new Position(0,data.getHeroesPosition().y));
    //etc...
  }

  private void spawnHostiles(){
    int x=(int)(HardCodedParameters.defaultWidth*.9);
    int y=0;
    boolean cont=true;
    while (cont) {
      y=(int)(gen.nextInt((int)(HardCodedParameters.defaultHeight*.6))+HardCodedParameters.defaultHeight*.1);
      cont=false;
      for (HostileService h:data.getHostiles()){
        if (h.getPosition().equals(new Position(x,y))) cont=true;
      }
    }
    data.addHostile(new Position(x,y), new Vitesse(0, 0), new Acceleration(-1, 0), "");
  }

/*  private boolean collisionHeroesHostiles(HostileService h){
    return (
      (data.getHeroesPosition().x-h.getPosition().x)*(data.getHeroesPosition().x-h.getPosition().x)+
      (data.getHeroesPosition().y-h.getPosition().y)*(data.getHeroesPosition().y-h.getPosition().y) <
      0.25*(data.getHeroesWidth()+data.getPhantomWidth())*(data.getHeroesWidth()+data.getPhantomWidth())
    );
  }*/
  
//  private boolean collisionHeroesHostiles(){
//    for (HostileService h:data.getHostiles()){
//      if (collisionHeroesHostiles(h)){
//        return true;
//      }
//    }
//    return false;
//  }

  public void move(MovableService movableService){
    double aX = movableService.getAccelerationX();
    double aY = movableService.getAccelerationY();

    double vX = movableService.getVitesseX() + aX;
    double vY = movableService.getVitesseY() + aY;

    double pX = movableService.getPositionX() + vX;
    double pY = movableService.getPositionY() + vY;

    movableService.setVitesse(new Vitesse(vX, vY));
    movableService.setPosition(new Position(pX, pY));

  }


  private boolean collisionBetweenPositionnables(PositionnableService object1, PositionnableService object2){

    Position p1 = object1.getPosition();
    Position p2 = object2.getPosition();

    double p1Height = object1.getHauteur();
    double p1Width = object1.getLargeur();

    double p2Height  = object2.getHauteur();
    double p2Width  = object2.getLargeur();

    if( (p1.x > p2.x && p1.x < p2.x+p2Width && p1.y > p2.y && p1.y < p2.y+p2Height) ) return true;
    if((p1.x + p1Width > p2.x && p1.x + p1Width < p2.x+p2Width && p1.y > p2.y && p1.y < p2.y+p2Height)  )return true;
    if((p1.x + p1Width> p2.x && p1.x + p1Width < p2.x+p2Width && p1.y + p1Height > p2.y && p1.y  + p1Height < p2.y+p2Height ) )return true;
    if((p1.x > p2.x && p1.x < p2.x+p2Width && p1.y + p1Height > p2.y && p1.y  + p1Height < p2.y+p2Height) )return true;

    if( (p2.x > p1.x && p2.x < p1.x+p1Width && p2.y > p1.y && p2.y < p1.y+p1Height) )return true;
    if( (p2.x + p2Width > p1.x && p2.x + p2Width < p1.x+p1Width && p2.y > p1.y && p2.y < p1.y+p1Height))return true;
    if( (p2.x + p2Width> p1.x && p2.x + p2Width < p1.x+p1Width && p2.y + p2Height > p1.y && p2.y  + p2Height < p1.y+p1Height))return true;
    if( (p2.x > p1.x && p2.x < p1.x+p1Width && p2.y + p2Height > p1.y && p2.y  + p2Height < p1.y+p1Height))return true;




    return false ;
  }


}
