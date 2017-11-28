/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: engine/Engine.java 2015-03-11 buixuan.
 * ******************************************************/
package fr.insta.cl.pacinc.pacdrive.engine;

import java.util.*;

import fr.insta.cl.pacinc.pacdrive.data.model.Mine;
import fr.insta.cl.pacinc.pacdrive.data.model.Movable;
import fr.insta.cl.pacinc.pacdrive.data.model.Positionnable;
import fr.insta.cl.pacinc.pacdrive.specifications.*;
import fr.insta.cl.pacinc.pacdrive.tools.*;

public class Engine implements EngineService, RequireDataService{
  private static final double friction=HardCodedParameters.friction,
                              heroesStep=HardCodedParameters.heroesStep,
                              phantomStep=HardCodedParameters.phantomStep;
  private Timer engineClock;
  private DataService data;
  private User.COMMAND command;
  private Random gen;
  private boolean moveLeft,moveRight,moveUp,moveDown,putMine;

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
    putMine= false;
  }

  @Override
  public void start(){
    engineClock.schedule(new TimerTask(){
      public void run() {
        //System.out.println("Game step #"+data.getStepNumber()+": checked.");
        
        if (gen.nextInt(10)<3) {
          spawnHostiles();
          data.setMessageForLog("Nouvel ennemi sur la carte !");
        }

        updateSpeedHeroes();
        updateCommandHeroes();
        updatePositionHeroes();

        List<HostileService> hostiles = new ArrayList<>();
        List<KitService> kits = new ArrayList<>();
        List<PieceService> pieces = new ArrayList<>();
        List<MineService> mines = new ArrayList<>();

        int score=0;

        data.setSoundEffect(Sound.SOUND.None);

        for (HostileService h : data.getHostiles()){

            move(h);
            for(BatimentService b : data.getBatiments()){
              if(collisionBetweenPositionnables(h,b)){
                collisionWithObstacle(h);
              }
            }


            boolean boom = false ;
            mines = new ArrayList<>();

            if (collisionBetweenPositionnables(data.getJoueur(), h)) {
              data.setSoundEffect(Sound.SOUND.HeroesGotHit);
              data.getJoueur().setHealth(data.getJoueur().getHealth() - 1);
              data.setMessageForLog("Joueur touché par ennemi !");
              mines = data.getMines();
            }
            else {
              for (MineService m : data.getMines()) {
                if (collisionBetweenPositionnables(m, h)) {
                  data.setSoundEffect(Sound.SOUND.PhantomDestroyed);
                  data.setMessageForLog("Ennemi détruit par mine !");
                  score++;
                  boom = true;
                }
                else {
                  mines.add(m);
                }
              }
              if (! boom) hostiles.add(h);
            }
            data.setMines(mines);
        }

        for (KitService k : data.getKits()){

          if (collisionBetweenPositionnables(data.getJoueur(), k)){
            data.setSoundEffect(Sound.SOUND.KitTaken);
            data.getJoueur().setHealth(data.getJoueur().getHealth() + 1);
            data.setMessageForLog("Kit ramassé !");
          } else {
            kits.add(k);
          }
        }

        for (PieceService p : data.getPieces()) {

          if (collisionBetweenPositionnables(data.getJoueur(), p)) {
            data.setSoundEffect(Sound.SOUND.PieceTaken);
            data.setMessageForLog("Pièce ramassée !");
            score++;
          } else {
            pieces.add(p);
          }
        }

        data.addScore(score);

        data.setHostiles(hostiles);
        data.setKits(kits);
        data.setPieces(pieces);

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
    if (c==User.COMMAND.SPACE) putMine=true;
  }
  
  @Override
  public void releaseHeroesCommand(User.COMMAND c){
    if (c==User.COMMAND.LEFT) moveLeft=false;
    if (c==User.COMMAND.RIGHT) moveRight=false;
    if (c==User.COMMAND.UP) moveUp=false;
    if (c==User.COMMAND.DOWN) moveDown=false;
    if (c==User.COMMAND.SPACE) putMine=false;
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
    if (putMine) {
        Boolean emplacement_pris = false;
        MineService nouvelle_mine = new Mine(data.getJoueur().getPosition());
        for (MineService m : data.getMines()) {
            if (collisionBetweenPositionnables(m, nouvelle_mine)) {
                emplacement_pris = true;
            }
        }
        if (!emplacement_pris) {
          data.getMines().add(nouvelle_mine);
          data.setMessageForLog("Mine posée !");
        }
    }
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


  public void collisionWithObstacle(MovableService movable){
    Vitesse vit = movable.getVitesse();
    Position pos = movable.getPosition() ;
    movable.setPosition(pos.x - vit.x, pos.y - vit.y);
    movable.setVitesse(0,0);
  }



}
