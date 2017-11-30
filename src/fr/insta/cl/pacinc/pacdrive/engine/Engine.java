/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: engine/Engine.java 2015-03-11 buixuan.
 * ******************************************************/
package fr.insta.cl.pacinc.pacdrive.engine;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import fr.insta.cl.pacinc.pacdrive.data.model.Hostile;
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

        data.setSoundEffect(Sound.SOUND.None);
          if (data.gameIsOver()){
            return ;
          }
        
//        if (gen.nextInt(10)<3) {
//          spawnHostiles();
//          data.setMessageForLog("Nouvel ennemi sur la carte !");
//        }

        updateSpeedHeroes();
        updateCommandHeroes();
        updatePositionHeroes();

        for(BatimentService b : data.getBatiments()){
          if(collisionBetweenPositionnables(data.getJoueur(),b)){
            collisionWithObstacle(data.getJoueur());
          }
        }

        List<HostileService> hostiles = new ArrayList<>();
        List<KitService> kits = new ArrayList<>();
        List<PieceService> pieces = new ArrayList<>();
        List<MineService> mines = new ArrayList<>();

        int score=0;



        for (HostileService h : data.getHostiles()){

switch(h.getComportement()){
            case "avancee1":
              h.setTimerDetectionVirage(h.getTimerDetectionVirage() - 1);
              h.setTimerChangementDirection(h.getTimerChangementDirection() - 1);
              iaAvancee1(h);
              break ;
            case "avancee2":
              iaAvancee2(h);
              break;
            default :
              iaPrimitive(h);
          }

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

  private void iaAvancee1(HostileService h) {

    if (h.getTimerDetectionVirage() != 0) { return; }

    int n = pulse_nord(h);
    int s = pulse_sud(h);
    int e = pulse_est(h);
    int o = pulse_ouest(h);

    // si dans une rue horizontale et déplacement horizontal
    if (n < HardCodedParameters.NB_PULSES && s < HardCodedParameters.NB_PULSES && (int) h.getVitesseY() == 0) {
      h.setFreeToMove(false);
      if (e <= 3) {
        h.setVitesse(-HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
        return;
      } else if (o <= 3) {
        h.setVitesse(HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
        return;
      } else {
        return;
      }
    } else
      // si dans une rue verticale et déplacement vertical
      if (e < HardCodedParameters.NB_PULSES && o < HardCodedParameters.NB_PULSES && (int) h.getVitesseX() == 0) {
        h.setFreeToMove(false);
        if (n <= 3) {
          h.setVitesse(0, HardCodedParameters.VITESSE_MAX_HOSTILE);
          return;
        } else if (s <= 3) {
          h.setVitesse(0, -HardCodedParameters.VITESSE_MAX_HOSTILE);
          return;
        } else {
          return;
        }
      } else {
        // si dans un virage ou embranchement en T ou en X
        if (!h.getFreeToMove()) {
          h.setFreeToMove(true);
          h.setTimerDetectionVirage(HardCodedParameters.TIMER_HOSTILE_DETECTION_VIRAGE);
          return;
        }

        if (h.getTimerChangementDirection() != 0) { return; }
        h.setTimerChangementDirection(HardCodedParameters.TIMER_HOSTILE_CHANGEMENT_DIRECTION);

        if (h.getVitesseX() > 0 && (int) h.getVitesseY() == 0) {
          if (n == HardCodedParameters.NB_PULSES) {
            if (e == HardCodedParameters.NB_PULSES) {
              if (s == HardCodedParameters.NB_PULSES) {
                // n , e et s disponibles
                int dice = gen.nextInt(3);
                switch (dice) {
                  case 0:
                    h.setVitesse(0, -HardCodedParameters.VITESSE_MAX_HOSTILE);
                    return;
                  case 1:
                    h.setVitesse(HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
                    return;
                  case 2:
                    h.setVitesse(0, HardCodedParameters.VITESSE_MAX_HOSTILE);
                    return;
                }
              } else {
                // n et e disponibles ; s indisponible
                int dice = gen.nextInt(2);
                switch (dice) {
                  case 0:
                    h.setVitesse(0, -HardCodedParameters.VITESSE_MAX_HOSTILE);
                    return;
                  case 1:
                    h.setVitesse(HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
                    return;
                }
              }
            } else {
              if (s == HardCodedParameters.NB_PULSES) {
                // n et s disponibles ; e indisponible
                int dice = gen.nextInt(2);
                switch (dice) {
                  case 0:
                    h.setVitesse(0, -HardCodedParameters.VITESSE_MAX_HOSTILE);
                    return;
                  case 1:
                    h.setVitesse(0, HardCodedParameters.VITESSE_MAX_HOSTILE);
                    return;
                }
              } else {
                // n disponible ; s et e indisponibles
                h.setVitesse(0, -HardCodedParameters.VITESSE_MAX_HOSTILE);
                return;
              }
            }
          } else {
            if (e == HardCodedParameters.NB_PULSES) {
              if (s == HardCodedParameters.NB_PULSES) {
                // e et s disponibles ; n indisponible
                int dice = gen.nextInt(2);
                switch (dice) {
                  case 0:
                    h.setVitesse(HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
                    return;
                  case 1:
                    h.setVitesse(0, HardCodedParameters.VITESSE_MAX_HOSTILE);
                    return;
                }
              } else {
                // e disponible ; n et s indisponibles
                // cas jamais atteint
                return;
              }
            } else {
              if (s == HardCodedParameters.NB_PULSES) {
                // s disponible ; n et e indisponibles
                h.setVitesse(0, HardCodedParameters.VITESSE_MAX_HOSTILE);
                return;
              } else {
                // n, e et s indisponibles
                // cas jamais atteint
                return;
              }
            }
          }
        }

        if (h.getVitesseX() < 0 && (int) h.getVitesseY() == 0) {
          if (n == HardCodedParameters.NB_PULSES) {
            if (o == HardCodedParameters.NB_PULSES) {
              if (s == HardCodedParameters.NB_PULSES) {
                // n , o et s disponibles
                int dice = gen.nextInt(3);
                switch (dice) {
                  case 0:
                    h.setVitesse(0, -HardCodedParameters.VITESSE_MAX_HOSTILE);
                    return;
                  case 1:
                    h.setVitesse(-HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
                    return;
                  case 2:
                    h.setVitesse(0, HardCodedParameters.VITESSE_MAX_HOSTILE);
                    return;
                }
              } else {
                // n et o disponibles ; s indisponible
                int dice = gen.nextInt(2);
                switch (dice) {
                  case 0:
                    h.setVitesse(0, -HardCodedParameters.VITESSE_MAX_HOSTILE);
                    return;
                  case 1:
                    h.setVitesse(-HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
                    return;
                }
              }
            } else {
              if (s == HardCodedParameters.NB_PULSES) {
                // n et s disponibles ; o indisponible
                int dice = gen.nextInt(2);
                switch (dice) {
                  case 0:
                    h.setVitesse(0, -HardCodedParameters.VITESSE_MAX_HOSTILE);
                    return;
                  case 1:
                    h.setVitesse(0, HardCodedParameters.VITESSE_MAX_HOSTILE);
                    return;
                }
              } else {
                // n disponible ; s et o indisponibles
                h.setVitesse(0, -HardCodedParameters.VITESSE_MAX_HOSTILE);
                return;
              }
            }
          } else {
            if (o == HardCodedParameters.NB_PULSES) {
              if (s == HardCodedParameters.NB_PULSES) {
                // o et s disponibles ; n indisponible
                int dice = gen.nextInt(2);
                switch (dice) {
                  case 0:
                    h.setVitesse(-HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
                    return;
                  case 1:
                    h.setVitesse(0, HardCodedParameters.VITESSE_MAX_HOSTILE);
                    return;
                }
              } else {
                // o disponible ; n et s indisponibles
                // cas jamais atteint
                return;
              }
            } else {
              if (s == HardCodedParameters.NB_PULSES) {
                // s disponible ; n et o indisponibles
                h.setVitesse(0, HardCodedParameters.VITESSE_MAX_HOSTILE);
                return;
              } else {
                // n, o et s indisponibles
                // cas jamais atteint
                return;
              }
            }
          }
        }

        if (h.getVitesseY() < 0 && (int) h.getVitesseX() == 0) {
          if (e == HardCodedParameters.NB_PULSES) {
            if (n == HardCodedParameters.NB_PULSES) {
              if (o == HardCodedParameters.NB_PULSES) {
                // e , n et o disponibles
                int dice = gen.nextInt(3);
                switch (dice) {
                  case 0:
                    h.setVitesse(HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
                    return;
                  case 1:
                    h.setVitesse(0, -HardCodedParameters.VITESSE_MAX_HOSTILE);
                    return;
                  case 2:
                    h.setVitesse(-HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
                    return;
                }
              } else {
                // e et n disponibles ; o indisponible
                int dice = gen.nextInt(2);
                switch (dice) {
                  case 0:
                    h.setVitesse(HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
                    return;
                  case 1:
                    h.setVitesse(0, -HardCodedParameters.VITESSE_MAX_HOSTILE);
                    return;
                }
              }
            } else {
              if (o == HardCodedParameters.NB_PULSES) {
                // e et o disponibles ; n indisponible
                int dice = gen.nextInt(2);
                switch (dice) {
                  case 0:
                    h.setVitesse(HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
                    return;
                  case 1:
                    h.setVitesse(-HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
                    return;
                }
              } else {
                // e disponible ; o et n indisponibles
                h.setVitesse(HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
                return;
              }
            }
          } else {
            if (n == HardCodedParameters.NB_PULSES) {
              if (o == HardCodedParameters.NB_PULSES) {
                // n et o disponibles ; e indisponible
                int dice = gen.nextInt(2);
                switch (dice) {
                  case 0:
                    h.setVitesse(0, -HardCodedParameters.VITESSE_MAX_HOSTILE);
                    return;
                  case 1:
                    h.setVitesse(-HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
                    return;
                }
              } else {
                // n disponible ; e et o indisponibles
                // cas jamais atteint
                return;
              }
            } else {
              if (o == HardCodedParameters.NB_PULSES) {
                // o disponible ; e et n indisponibles
                h.setVitesse(-HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
                return;
              } else {
                // e, n et o indisponibles
                // cas jamais atteint
                return;
              }
            }
          }
        }

        if (h.getVitesseY() > 0 && (int) h.getVitesseX() == 0) {
          if (e == HardCodedParameters.NB_PULSES) {
            if (s == HardCodedParameters.NB_PULSES) {
              if (o == HardCodedParameters.NB_PULSES) {
                // e , s et o disponibles
                int dice = gen.nextInt(3);
                switch (dice) {
                  case 0:
                    h.setVitesse(HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
                    return;
                  case 1:
                    h.setVitesse(0, HardCodedParameters.VITESSE_MAX_HOSTILE);
                    return;
                  case 2:
                    h.setVitesse(-HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
                    return;
                }
              } else {
                // e et s disponibles ; o indisponible
                int dice = gen.nextInt(2);
                switch (dice) {
                  case 0:
                    h.setVitesse(HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
                    return;
                  case 1:
                    h.setVitesse(0, HardCodedParameters.VITESSE_MAX_HOSTILE);
                    return;
                }
              }
            } else {
              if (o == HardCodedParameters.NB_PULSES) {
                // e et o disponibles ; s indisponible
                int dice = gen.nextInt(2);
                switch (dice) {
                  case 0:
                    h.setVitesse(HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
                    return;
                  case 1:
                    h.setVitesse(-HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
                    return;
                }
              } else {
                // e disponible ; o et s indisponibles
                h.setVitesse(HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
                return;
              }
            }
          } else {
            if (s == HardCodedParameters.NB_PULSES) {
              if (o == HardCodedParameters.NB_PULSES) {
                // s et o disponibles ; e indisponible
                int dice = gen.nextInt(2);
                switch (dice) {
                  case 0:
                    h.setVitesse(0, HardCodedParameters.VITESSE_MAX_HOSTILE);
                    return;
                  case 1:
                    h.setVitesse(-HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
                    return;
                }
              } else {
                // s disponible ; e et o indisponibles
                // cas jamais atteint
                return;
              }
            } else {
              if (o == HardCodedParameters.NB_PULSES) {
                // o disponible ; e et s indisponibles
                h.setVitesse(-HardCodedParameters.VITESSE_MAX_HOSTILE, 0);
                return;
              } else {
                // e, s et o indisponibles
                // cas jamais atteint
                return;
              }
            }
          }
        }
      }
  }



  public void iaPrimitive(HostileService h) {

    if (h.getVitesse().x == 0 && h.getVitesse().y == 0)
    {

      int i = ThreadLocalRandom.current().nextInt(1, 5);
      switch (i) {
        case 1:
          h.setAcceleration(0, 1);
          h.setVitesse(0, 1);
          break;
        case 2:
          h.setAcceleration(0, -1);
          h.setVitesse(0, -1);
          break;
        case 3:
          h.setAcceleration(1, 0);
          h.setVitesse(1, 0);
          break;
        case 4:
          h.setAcceleration(-1, 0);
          h.setVitesse(-1, 0);
          break;

      }
    }

  }

 public void iaAvancee2(HostileService h) {
    boolean n = pulse_nord(h) < 2;
    boolean s = pulse_sud(h) < 2;
    boolean e = pulse_est(h) < 2;
    boolean o = pulse_ouest(h) < 2;

    //System.out.println("n=" + n + " s=" + s + " e=" + e + " o=" + o);

    //h.setVitesse(0,0);
    if (h.getAcceleration().x == 0 && h.getAcceleration().y == -1 && n) {
      if (!s) h.setAcceleration(0, 1);
      else if (!e) h.setAcceleration(1, 0);
      else if (!o) h.setAcceleration(-1, 0);
    }

    if (h.getAcceleration().x == 0 && h.getAcceleration().y == 1 && s) {
      if (!n) h.setAcceleration(0, -1);
      else if (!e) h.setAcceleration(1, 0);
      else if (!o) h.setAcceleration(-1, 0);
    }

    if (h.getAcceleration().x == 1 && h.getAcceleration().y == 0 && e) {
      if (!s) h.setAcceleration(0, 1);
      else if (!n) h.setAcceleration(0, -1);
      else if (!o) h.setAcceleration(-1, 0);
    }

    if (h.getAcceleration().x == -1 && h.getAcceleration().y == 0 && n) {
      if (!s) h.setAcceleration(0, 1);
      else if (!e) h.setAcceleration(1, 0);
      else if (!n) h.setAcceleration(0, -1);
    }
  }

  private int pulse_nord(HostileService h) {
    int free_space = 0;
    HostileService hostile_translated = new Hostile(new Position(h.getPositionX(),h.getPositionY()),new Vitesse(0,0),new Acceleration(0,0),"");
    for (double i = HardCodedParameters.PULSE_STEP; i < (HardCodedParameters.NB_PULSES + 0.95)*HardCodedParameters.PULSE_STEP; i += HardCodedParameters.PULSE_STEP) {
      hostile_translated.setPosition(hostile_translated.getPositionX(),hostile_translated.getPositionY() - i);
      for (BatimentService b : data.getBatiments()) {
        if (collisionBetweenPositionnables(hostile_translated, b)) {
          return free_space;
        }
      }
      free_space++;
    }
    return free_space;
  }

  private int pulse_sud(HostileService h) {
    int free_space = 0;
    HostileService hostile_translated = new Hostile(new Position(h.getPositionX(),h.getPositionY()),new Vitesse(0,0),new Acceleration(0,0),"");
    for (double i = HardCodedParameters.PULSE_STEP; i < (HardCodedParameters.NB_PULSES + 0.95)*HardCodedParameters.PULSE_STEP; i += HardCodedParameters.PULSE_STEP) {
      hostile_translated.setPosition(hostile_translated.getPositionX(),hostile_translated.getPositionY() + i);
      for (BatimentService b : data.getBatiments()) {
        if (collisionBetweenPositionnables(hostile_translated, b)) {
          return free_space;
        }
      }
      free_space++;
    }
    return free_space;
  }

  private int pulse_est(HostileService h) {
    int free_space = 0;
    HostileService hostile_translated = new Hostile(new Position(h.getPositionX(),h.getPositionY()),new Vitesse(0,0),new Acceleration(0,0),"");
    for (double i = HardCodedParameters.PULSE_STEP; i < (HardCodedParameters.NB_PULSES + 0.95)*HardCodedParameters.PULSE_STEP; i += HardCodedParameters.PULSE_STEP) {
      hostile_translated.setPosition(hostile_translated.getPositionX() + i,hostile_translated.getPositionY());
      for (BatimentService b : data.getBatiments()) {
        if (collisionBetweenPositionnables(hostile_translated, b)) {
          return free_space;
        }
      }
      free_space++;
    }
    return free_space;
  }

  private int pulse_ouest(HostileService h) {
    int free_space = 0;
    HostileService hostile_translated = new Hostile(new Position(h.getPositionX(),h.getPositionY()),new Vitesse(0,0),new Acceleration(0,0),"");
    for (double i = HardCodedParameters.PULSE_STEP; i < (HardCodedParameters.NB_PULSES + 0.95)*HardCodedParameters.PULSE_STEP; i += HardCodedParameters.PULSE_STEP) {
      hostile_translated.setPosition(hostile_translated.getPositionX() - i,hostile_translated.getPositionY());
      for (BatimentService b : data.getBatiments()) {
        if (collisionBetweenPositionnables(hostile_translated, b)) {
          return free_space;
        }
      }
      free_space++;
    }
    return free_space;
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
