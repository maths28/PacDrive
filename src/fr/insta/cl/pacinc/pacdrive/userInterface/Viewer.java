/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package fr.insta.cl.pacinc.pacdrive.userInterface;

import fr.insta.cl.pacinc.pacdrive.data.model.Joueur;
import fr.insta.cl.pacinc.pacdrive.specifications.*;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
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
    xModifier=shrink*HardCodedParameters.OFFSET_X;
    yModifier=shrink*HardCodedParameters.OFFSET_Y;


    /*int index=heroesAvatarViewportIndex/spriteSlowDownRate;
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
    heroesAvatarViewportIndex=(heroesAvatarViewportIndex+1)%(heroesAvatarViewports.size()*spriteSlowDownRate);*/



    Group panel = new Group();


      generateGamePanel(panel);
      generateStatPanel(panel);
      generateConsolePanel(panel) ;


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

  private void generateGamePanel(Group panel){

      //map
      Rectangle map = new Rectangle(shrink*(HardCodedParameters.AREA_GAME_X_END-HardCodedParameters.AREA_GAME_X_START),
              shrink*(HardCodedParameters.AREA_GAME_Y_END-HardCodedParameters.AREA_GAME_Y_START));

      System.out.println(shrink*(HardCodedParameters.AREA_GAME_X_END-HardCodedParameters.AREA_GAME_X_START)+
              shrink*(HardCodedParameters.AREA_GAME_Y_END-HardCodedParameters.AREA_GAME_Y_START));
      map.setFill(Color.WHITE);
      map.setStroke(Color.DIMGRAY);
      map.setStrokeWidth(.01*shrink*defaultMainHeight);
      map.setArcWidth(.04*shrink*defaultMainHeight);
      map.setArcHeight(.04*shrink*defaultMainHeight);
      map.setTranslateX(HardCodedParameters.AREA_GAME_X_START*shrink);
      map.setTranslateY(HardCodedParameters.AREA_GAME_Y_START*shrink);

      panel.getChildren().add(map);

      //Joueur
      Joueur j=data.getJoueur();
      Rectangle joueurAvatar = new Rectangle(j.getLargeur()*shrink, j.getHauteur()*shrink);
      joueurAvatar.setFill(Color.BLUE);
      joueurAvatar.setTranslateX(shrink*j.getPosition().x+shrink*xModifier);
      joueurAvatar.setTranslateY(shrink*j.getPosition().y+shrink*yModifier);
      panel.getChildren().add(joueurAvatar);


      //hostile
      List<HostileService> hostiles = data.getHostiles();
      HostileService h;

      for (int i=0; i<hostiles.size();i++){
          h=hostiles.get(i);
          Rectangle hostileAvatar = new Rectangle(h.getLargeur()*shrink, h.getHauteur()*shrink);
          hostileAvatar.setFill(Color.RED);
          hostileAvatar.setTranslateX(shrink*h.getPosition().x+shrink*xModifier);
          hostileAvatar.setTranslateY(shrink*h.getPosition().y+shrink*yModifier);
          panel.getChildren().add(hostileAvatar);
      }

      //batiment
      List<BatimentService> batiments = data.getBatiments();
      BatimentService b;

      for (int i=0; i<batiments.size();i++){
          b=batiments.get(i);

          Rectangle batimentAvatar = new Rectangle(b.getLargeur()*shrink, b.getHauteur()*shrink);
          batimentAvatar.setFill(Color.GREY);
          batimentAvatar.setTranslateX(shrink*b.getPosition().x+shrink*xModifier);
          batimentAvatar.setTranslateY(shrink*b.getPosition().y+shrink*yModifier);
          panel.getChildren().add(batimentAvatar);
      }

      //piece
      List<PieceService> pieces = data.getPieces();
      PieceService p;

      for (int i=0; i<pieces.size();i++){
          p=pieces.get(i);
          Rectangle pieceAvatar = new Rectangle(p.getLargeur()*shrink, p.getHauteur()*shrink);
          pieceAvatar.setFill(Color.GOLD);
          pieceAvatar.setTranslateX(shrink*p.getPosition().x+shrink*xModifier);
          pieceAvatar.setTranslateY(shrink*p.getPosition().y+shrink*yModifier);
          panel.getChildren().add(pieceAvatar);
      }

      //kit
      List<KitService> kits = data.getKits();
      KitService k;

      for (int i=0; i<kits.size();i++){
          k=kits.get(i);
          Rectangle kitAvatar = new Rectangle(k.getLargeur()*shrink, k.getHauteur()*shrink);
          kitAvatar.setFill(Color.GREEN);
          kitAvatar.setTranslateX(shrink*k.getPosition().x+shrink*xModifier);
          kitAvatar.setTranslateY(shrink*k.getPosition().y+shrink*yModifier);
          panel.getChildren().add(kitAvatar);
      }

      //mine
      List<MineService> mines = data.getMines();
      MineService m;

      for (int i=0; i<mines.size();i++){
          m=mines.get(i);
          Rectangle mineAvatar = new Rectangle(m.getLargeur()*shrink, m.getHauteur()*shrink);
          mineAvatar.setFill(Color.BLACK);
          mineAvatar.setTranslateX(shrink*m.getPosition().x+shrink*xModifier);
          mineAvatar.setTranslateY(shrink*m.getPosition().y+shrink*yModifier);
          panel.getChildren().add(mineAvatar);
      }

  }

    private void generateConsolePanel(Group panel){

/*        Rectangle console = new Rectangle(0.30*shrink*defaultMainWidth,
                0.80*shrink*defaultMainHeight);*/

        Rectangle console = new Rectangle(shrink*(HardCodedParameters.CONSOLE_X_END-HardCodedParameters.CONSOLE_X_START),
                shrink*(HardCodedParameters.CONSOLE_Y_END-HardCodedParameters.CONSOLE_Y_START));


        ImagePattern FondConsole = new ImagePattern(new Image("file:src/fr/insta/cl/pacinc/pacdrive/images/FondConsole.jpg"));

        console.setFill(Color.rgb(167,167,167));
        console.setStroke(Color.DIMGRAY);
        console.setStrokeWidth(.01*shrink*defaultMainHeight);
        console.setArcWidth(.04*shrink*defaultMainHeight);
        console.setArcHeight(.04*shrink*defaultMainHeight);
        console.setTranslateX(HardCodedParameters.CONSOLE_X_START*shrink);
        console.setTranslateY(HardCodedParameters.CONSOLE_Y_START*shrink);
        console.setFill(FondConsole);
        panel.getChildren().add(console);



        Text textConsole = new Text((HardCodedParameters.CONSOLE_X_START+HardCodedParameters.CONSOLE_X_END*0.05)*shrink, (HardCodedParameters.CONSOLE_Y_START+HardCodedParameters.CONSOLE_Y_END*0.10)*shrink, "Console");
        textConsole.setFont(new Font(HardCodedParameters.SIZE_TITRE_CONSOLE * shrink));
        textConsole.setFill(Color.WHITE);
        panel.getChildren().add(textConsole);

        Double positionY = 0.20;

        for(int i = 0; i<HardCodedParameters.LOG_MESSAGES_MAX; i++){
            Text textDonneesConsole = new Text((HardCodedParameters.CONSOLE_X_START + HardCodedParameters.CONSOLE_X_END*0.01)*shrink, (HardCodedParameters.CONSOLE_Y_START+HardCodedParameters.CONSOLE_Y_END*positionY)*shrink, data.getLog()[i]);
            textDonneesConsole.setFont(new Font(HardCodedParameters.SIZE_TEXT_CONSOLE * shrink));
            textDonneesConsole.setFill(Color.WHITE);
            panel.getChildren().add(textDonneesConsole);
            positionY = positionY + 0.05;
        }

    }

    private void generateStatPanel(Group panel){

        Rectangle Stats = new Rectangle(shrink*(HardCodedParameters.STATS_X_END-HardCodedParameters.STATS_X_START),
                shrink*(HardCodedParameters.STATS_Y_END-HardCodedParameters.STATS_Y_START));

        Stats.setFill(Color.rgb(105, 114, 167));
        Stats.setStroke(Color.DIMGRAY);
        Stats.setStrokeWidth(.01*shrink*defaultMainHeight);
        Stats.setArcWidth(.04*shrink*defaultMainHeight);
        Stats.setArcHeight(.04*shrink*defaultMainHeight);
        Stats.setTranslateX(HardCodedParameters.STATS_X_START*shrink);
        Stats.setTranslateY(HardCodedParameters.STATS_Y_START*shrink);
        panel.getChildren().add(Stats);

        Text greets = new Text((HardCodedParameters.STATS_X_START+HardCodedParameters.STATS_X_END*0.80)*shrink, ((HardCodedParameters.STATS_Y_START+HardCodedParameters.STATS_Y_END*0.10)*shrink),
                "Round : 1");
        greets.setFont(new Font(HardCodedParameters.SIZE_TEXT_STATS*shrink));
        panel.getChildren().add(greets);
        Text score = new Text((HardCodedParameters.STATS_X_START+HardCodedParameters.STATS_X_END*0.45)*shrink, ((HardCodedParameters.STATS_Y_START+HardCodedParameters.STATS_Y_END*0.06)*shrink),
                "Score: "+data.getScore());
        score.setFont(new Font(HardCodedParameters.SIZE_TEXT_STATS*shrink));
        panel.getChildren().add(score);

        Text textLife = new Text((HardCodedParameters.STATS_X_START+HardCodedParameters.STATS_X_END*0.05)*shrink, ((HardCodedParameters.STATS_Y_START+HardCodedParameters.STATS_Y_END*0.10)*shrink), "Vie : " + data.getJoueur().getHealth());
        textLife.setFont(new Font(HardCodedParameters.SIZE_TEXT_STATS*shrink));
        panel.getChildren().add(textLife);

        DecimalFormat df = new DecimalFormat() ;
        df.setMaximumFractionDigits (0);

        Text textPositionJoueur = new Text((HardCodedParameters.STATS_X_START+HardCodedParameters.STATS_X_END*0.35)*shrink, ((HardCodedParameters.STATS_Y_START+HardCodedParameters.STATS_Y_END*0.13)*shrink), "Position : " + df.format(data.getJoueur().getPosition().x) + " : " + df.format(data.getJoueur().getPosition().y));
        textPositionJoueur.setFont(new Font(HardCodedParameters.SIZE_TEXT_STATS*shrink));
        panel.getChildren().add(textPositionJoueur);
    }

}
