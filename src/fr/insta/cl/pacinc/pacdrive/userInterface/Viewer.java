/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package fr.insta.cl.pacinc.pacdrive.userInterface;

import fr.insta.cl.pacinc.pacdrive.specifications.*;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import fr.insta.cl.pacinc.pacdrive.tools.HardCodedParameters;

public class Viewer implements ViewerService, RequireReadService{
  private static final int spriteSlowDownRate=HardCodedParameters.spriteSlowDownRate;
  private static final double defaultMainWidth=HardCodedParameters.defaultWidth,
                              defaultMainHeight=HardCodedParameters.defaultHeight;
  private ReadService data;
  private ImageView heroesAvatar;
  private Image heroesSpriteSheet;
  private ArrayList<Rectangle2D> heroesAvatarViewports;
  private ArrayList<Integer> heroesAvatarXModifiers;
  private ArrayList<Integer> heroesAvatarYModifiers;
  private int heroesAvatarViewportIndex;
  private double xShrink,yShrink,shrink,xModifier,yModifier,heroesScale;

  public Viewer(){}
  
  @Override
  public void bindReadService(ReadService service){
    data=service;
  }

  @Override
  public void init(){
    xShrink=1;
    yShrink=1;
    xModifier=0;
    yModifier=0;

    //Yucky hard-conding
    heroesSpriteSheet = new Image("file:src/fr/insta/cl/pacinc/pacdrive/images/modern soldier large.png");
    heroesAvatar = new ImageView(heroesSpriteSheet);
    heroesAvatarViewports = new ArrayList<Rectangle2D>();
    heroesAvatarXModifiers = new ArrayList<Integer>();
    heroesAvatarYModifiers = new ArrayList<Integer>();

    heroesAvatarViewportIndex=0;
    
    //TODO: replace the following with XML loader
    //heroesAvatarViewports.add(new Rectangle2D(301,386,95,192));
    heroesAvatarViewports.add(new Rectangle2D(570,194,115,190));
    heroesAvatarViewports.add(new Rectangle2D(398,386,133,192));
    heroesAvatarViewports.add(new Rectangle2D(155,194,147,190));
    heroesAvatarViewports.add(new Rectangle2D(785,386,127,194));
    heroesAvatarViewports.add(new Rectangle2D(127,582,135,198));
    heroesAvatarViewports.add(new Rectangle2D(264,582,111,200));
    heroesAvatarViewports.add(new Rectangle2D(2,582,123,198));
    heroesAvatarViewports.add(new Rectangle2D(533,386,115,192));
    //heroesAvatarViewports.add(new Rectangle2D(204,386,95,192));

    //heroesAvatarXModifiers.add(10);heroesAvatarYModifiers.add(-7);
    heroesAvatarXModifiers.add(6);heroesAvatarYModifiers.add(-6);
    heroesAvatarXModifiers.add(2);heroesAvatarYModifiers.add(-8);
    heroesAvatarXModifiers.add(1);heroesAvatarYModifiers.add(-10);
    heroesAvatarXModifiers.add(1);heroesAvatarYModifiers.add(-13);
    heroesAvatarXModifiers.add(5);heroesAvatarYModifiers.add(-15);
    heroesAvatarXModifiers.add(5);heroesAvatarYModifiers.add(-13);
    heroesAvatarXModifiers.add(0);heroesAvatarYModifiers.add(-9);
    heroesAvatarXModifiers.add(0);heroesAvatarYModifiers.add(-6);
    //heroesAvatarXModifiers.add(10);heroesAvatarYModifiers.add(-7);
    
  }

  @Override
  public Parent getPanel(){
    shrink=Math.min(xShrink,yShrink);
    xModifier=.01*shrink*defaultMainHeight;
    yModifier=.01*shrink*defaultMainHeight;

    //Yucky hard-conding
    Rectangle map = new Rectangle(0.68*shrink*defaultMainWidth,
            0.8*shrink*defaultMainHeight);

    map.setFill(Color.WHITE);
    map.setStroke(Color.DIMGRAY);
    map.setStrokeWidth(.01*shrink*defaultMainHeight);
    map.setArcWidth(.04*shrink*defaultMainHeight);
    map.setArcHeight(.04*shrink*defaultMainHeight);
    map.setTranslateX(xModifier);
    map.setTranslateY(yModifier);
    
    Text greets = new Text(0.4*shrink*defaultMainHeight+.5*shrink*defaultMainWidth,
            -0.07*shrink*defaultMainWidth+shrink*defaultMainHeight,
                           "Round 1");
    greets.setFont(new Font(.05*shrink*defaultMainHeight));
    
    Text score = new Text(-0.1*shrink*defaultMainHeight+.5*shrink*defaultMainWidth,
                           -0.07*shrink*defaultMainWidth+shrink*defaultMainHeight,
                           "Score: "+data.getScore());
    score.setFont(new Font(.05*shrink*defaultMainHeight));
    
    int index=heroesAvatarViewportIndex/spriteSlowDownRate;
    heroesScale=data.getHeroesHeight()*shrink/heroesAvatarViewports.get(index).getHeight();
    heroesAvatar.setViewport(heroesAvatarViewports.get(index));
    heroesAvatar.setFitHeight(data.getHeroesHeight()*shrink);
    heroesAvatar.setPreserveRatio(true);
    heroesAvatar.setTranslateX(shrink*data.getHeroesPosition().x+
                               shrink*xModifier+
                               -heroesScale*0.5*heroesAvatarViewports.get(index).getWidth()+
                               shrink*heroesScale*heroesAvatarXModifiers.get(index)
                              );
    heroesAvatar.setTranslateY(shrink*data.getHeroesPosition().y+
                               shrink*yModifier+
                               -heroesScale*0.5*heroesAvatarViewports.get(index).getHeight()+
                               shrink*heroesScale*heroesAvatarYModifiers.get(index)
                              );
    heroesAvatarViewportIndex=(heroesAvatarViewportIndex+1)%(heroesAvatarViewports.size()*spriteSlowDownRate);

    Rectangle console = new Rectangle(0.30*shrink*defaultMainWidth,
            0.80*shrink*defaultMainHeight);

    console.setFill(Color.rgb(167,167,167));
    console.setStroke(Color.DIMGRAY);
    console.setStrokeWidth(.01*shrink*defaultMainHeight);
    console.setArcWidth(.04*shrink*defaultMainHeight);
    console.setArcHeight(.04*shrink*defaultMainHeight);
    console.setTranslateX(0.70*shrink*defaultMainWidth);
    console.setTranslateY(5*shrink);

    Group panel = new Group();
    panel.getChildren().addAll(map,greets,score,heroesAvatar, console);

    List<HostileService> hostiles = data.getHostiles();
    HostileService h;

    for (int i=0; i<hostiles.size();i++){
      h=hostiles.get(i);
      double radius=.5*Math.min(shrink*data.getPhantomWidth(),shrink*data.getPhantomHeight());
      Circle phantomAvatar = new Circle(radius,Color.rgb(255,156,156));
      phantomAvatar.setEffect(new Lighting());
      phantomAvatar.setTranslateX(shrink*h.getPosition().x+shrink*xModifier-radius);
      phantomAvatar.setTranslateY(shrink*h.getPosition().y+shrink*yModifier-radius);
      panel.getChildren().add(phantomAvatar);
    }

    List<BatimentService> batiments = data.getBatiments();
    BatimentService b;

    for (int i=0; i<batiments.size();i++){
      b=batiments.get(i);
      double radius=.5*Math.min(shrink*data.getPhantomWidth(),shrink*data.getPhantomHeight());
      Circle phantomAvatar = new Circle(radius,Color.MAROON);
      phantomAvatar.setEffect(new Lighting());
      phantomAvatar.setTranslateX(shrink*b.getPosition().x+shrink*xModifier-radius);
      phantomAvatar.setTranslateY(shrink*b.getPosition().y+shrink*yModifier-radius);
      panel.getChildren().add(phantomAvatar);
    }

    List<PieceService> pieces = data.getPieces();
    PieceService p;

    for (int i=0; i<pieces.size();i++){
      p=pieces.get(i);
      double radius=.5*Math.min(shrink*data.getPhantomWidth(),shrink*data.getPhantomHeight());
      Circle phantomAvatar = new Circle(radius,Color.YELLOW);
      phantomAvatar.setEffect(new Lighting());
      phantomAvatar.setTranslateX(shrink*p.getPosition().x+shrink*xModifier-radius);
      phantomAvatar.setTranslateY(shrink*p.getPosition().y+shrink*yModifier-radius);
      panel.getChildren().add(phantomAvatar);
    }

    List<KitService> kits = data.getKits();
    KitService k;

    for (int i=0; i<kits.size();i++){
      k=kits.get(i);
      double radius=.5*Math.min(shrink*data.getPhantomWidth(),shrink*data.getPhantomHeight());
      Circle phantomAvatar = new Circle(radius,Color.GREEN);
      phantomAvatar.setEffect(new Lighting());
      phantomAvatar.setTranslateX(shrink*k.getPosition().x+shrink*xModifier-radius);
      phantomAvatar.setTranslateY(shrink*k.getPosition().y+shrink*yModifier-radius);
      panel.getChildren().add(phantomAvatar);
    }

    List<MineService> mines = data.getMines();
    MineService m;

    for (int i=0; i<mines.size();i++){
      m=mines.get(i);
      double radius=.5*Math.min(shrink*data.getPhantomWidth(),shrink*data.getPhantomHeight());
      Circle phantomAvatar = new Circle(radius,Color.BLACK);
      phantomAvatar.setEffect(new Lighting());
      phantomAvatar.setTranslateX(shrink*m.getPosition().x+shrink*xModifier-radius);
      phantomAvatar.setTranslateY(shrink*m.getPosition().y+shrink*yModifier-radius);
      panel.getChildren().add(phantomAvatar);
    }

    /*String str = "";
    for (int i = 0; i<HardCodedParameters.LOG_MESSAGES_MAX; i++) {
      str = str + data.getLog()[i] + "\n";
    }
    Text log = new Text(50, 50, str);
    log.setFont(new Font(10));
    panel.getChildren().add(log);*/
    Text textLife = new Text(0.2*shrink*defaultMainHeight, -0.07*shrink*defaultMainWidth+shrink*defaultMainHeight, "Vie : " + data.getJoueur().getHealth());
    textLife.setFont(new Font(.05*shrink*defaultMainHeight));
    panel.getChildren().add(textLife);

    DecimalFormat df = new DecimalFormat() ;
    df.setMaximumFractionDigits (0);

    Text textPositionJoueur = new Text(0.5*shrink*defaultMainHeight, -0.02*shrink*defaultMainWidth+shrink*defaultMainHeight, "Position : " + df.format(data.getJoueur().getPosition().x) + " : " + df.format(data.getJoueur().getPosition().y));
    textPositionJoueur.setFont(new Font(.05*shrink*defaultMainHeight));
    panel.getChildren().add(textPositionJoueur);

    Text textConsole = new Text(1.05*shrink*defaultMainHeight, -0.69*shrink*defaultMainWidth+shrink*defaultMainHeight, "Console");
    textConsole.setFont(new Font(.05*shrink*defaultMainHeight));
    panel.getChildren().add(textConsole);

    Double positionY = 0.15;

    for(int i = 0; i<HardCodedParameters.LOG_MESSAGES_MAX; i++){
      Text textDonneesConsole = new Text(0.95 * shrink * defaultMainHeight, positionY * shrink * defaultMainHeight, data.getLog()[i]);
      textDonneesConsole.setFont(new Font(.03 * shrink * defaultMainHeight));
      panel.getChildren().add(textDonneesConsole);
      positionY = positionY + 0.05;
    }


    return panel;
  }

  @Override
  public void setMainWindowWidth(double width){
    xShrink=width/defaultMainWidth;
  }
  
  @Override
  public void setMainWindowHeight(double height){
    yShrink=height/defaultMainHeight;
  }
}
