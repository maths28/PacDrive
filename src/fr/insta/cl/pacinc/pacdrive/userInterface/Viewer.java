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

  //Sprites
        //UI
  private ImagePattern FondConsole = new ImagePattern(new Image("file:src/fr/insta/cl/pacinc/pacdrive/images/FondConsole.jpg"));

        //positionnables
    private ImagePattern mine = new ImagePattern(new Image("file:src/fr/insta/cl/pacinc/pacdrive/images/positionnables/mine.png"));
    private ImagePattern kit_reparation = new ImagePattern(new Image("file:src/fr/insta/cl/pacinc/pacdrive/images/positionnables/kit_reparation.png"));

        //hostile
    private ImagePattern hostile_down = new ImagePattern(new Image("file:src/fr/insta/cl/pacinc/pacdrive/images/hostile/voiture_rouge_down.png"));
    private ImagePattern hostile_up = new ImagePattern(new Image("file:src/fr/insta/cl/pacinc/pacdrive/images/hostile/voiture_rouge_up.png"));
    private ImagePattern hostile_left = new ImagePattern(new Image("file:src/fr/insta/cl/pacinc/pacdrive/images/hostile/voiture_rouge_left.png"));
    private ImagePattern hostile_right = new ImagePattern(new Image("file:src/fr/insta/cl/pacinc/pacdrive/images/hostile/voiture_rouge_right.png"));

        //joueur
    private ImagePattern joueur_down = new ImagePattern(new Image("file:src/fr/insta/cl/pacinc/pacdrive/images/joueur/voiture_verte_down.png"));
    private ImagePattern joueur_up = new ImagePattern(new Image("file:src/fr/insta/cl/pacinc/pacdrive/images/joueur/voiture_verte_up.png"));
    private ImagePattern joueur_left = new ImagePattern(new Image("file:src/fr/insta/cl/pacinc/pacdrive/images/joueur/voiture_verte_left.png"));
    private ImagePattern joueur_right = new ImagePattern(new Image("file:src/fr/insta/cl/pacinc/pacdrive/images/joueur/voiture_verte_right.png"));



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
      
    
  }

  @Override
  public Parent getPanel(){
    shrink=Math.min(xShrink,yShrink);
    xModifier=shrink*HardCodedParameters.OFFSET_X;
    yModifier=shrink*HardCodedParameters.OFFSET_Y;

  Group panel = new Group();
    if(data.gameIsOver()){
        generateGameOver(panel);
    }
    else {
        generateGamePanel(panel);
        generateStatPanel(panel);
        generateConsolePanel(panel) ;
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

  private void generateGamePanel(Group panel){

      //map
      Rectangle map = new Rectangle(shrink*(HardCodedParameters.AREA_GAME_X_END-HardCodedParameters.AREA_GAME_X_START),
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

      joueurAvatar.setTranslateX(shrink*j.getPosition().x+shrink*xModifier);
      joueurAvatar.setTranslateY(shrink*j.getPosition().y+shrink*yModifier);

      switch (j.getDirection()){
          case "rigth" :
              joueurAvatar.setFill(joueur_right);
              break ;
          case "left" :
              joueurAvatar.setFill(joueur_left);
              break ;
          case "up" :
              joueurAvatar.setFill(joueur_up);
              break ;
          default : //down
              joueurAvatar.setFill(joueur_down);
              break ;
      }

      panel.getChildren().add(joueurAvatar);


      //hostile
      List<HostileService> hostiles = data.getHostiles();
      HostileService h;

      for (int i=0; i<hostiles.size();i++){
          h=hostiles.get(i);
          Rectangle hostileAvatar = new Rectangle(h.getLargeur()*shrink, h.getHauteur()*shrink);


          switch (h.getDirection()){
              case "rigth" :
                  hostileAvatar.setFill(hostile_right);
                  break ;
              case "left" :
                  hostileAvatar.setFill(hostile_left);
                  break ;
              case "up" :
                  hostileAvatar.setFill(hostile_up);
                  break ;
              default : //down
                  hostileAvatar.setFill(hostile_down);
                  break ;
          }


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
          kitAvatar.setFill(kit_reparation);
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
          mineAvatar.setFill(mine);
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

    private Parent generateGameOver(Group panel){

        DecimalFormat df = new DecimalFormat() ;
        df.setMaximumFractionDigits (0);

        Text greets = new Text(HardCodedParameters.defaultWidth*shrink/HardCodedParameters.GAME_OVER_TEXT_XPART,
                HardCodedParameters.defaultHeight*shrink/HardCodedParameters.GAME_OVER_TEXT_YPART,
                "Game Over");
        greets.setFont(new Font(HardCodedParameters.COEF_GAME_OVER_FONT_SIZE *shrink*defaultMainHeight));

        Text score = new Text(-0.1*shrink*defaultMainHeight+.5*shrink*defaultMainWidth,
                HardCodedParameters.defaultHeight*shrink/HardCodedParameters.GAME_OVER_TEXT_YPART+ HardCodedParameters.STAT_GAME_OVER_OFFSET_Y*shrink,
                "Score: "+data.getScore());
        score.setFont(new Font(HardCodedParameters.COEF_FONT_SIZE *shrink*defaultMainHeight));

        Text textPositionJoueur = new Text(0.5*shrink*defaultMainHeight, HardCodedParameters.defaultHeight*shrink/HardCodedParameters.GAME_OVER_TEXT_YPART+ HardCodedParameters.STAT_GAME_OVER_OFFSET_Y_2*shrink, "Position : " + df.format(data.getJoueur().getPosition().x) + " : " + df.format(data.getJoueur().getPosition().y));
        textPositionJoueur.setFont(new Font(HardCodedParameters.COEF_FONT_SIZE *shrink*defaultMainHeight));

        Text round = new Text(0.4*shrink*defaultMainHeight+.5*shrink*defaultMainWidth,
                HardCodedParameters.defaultHeight*shrink/HardCodedParameters.GAME_OVER_TEXT_YPART+ HardCodedParameters.STAT_GAME_OVER_OFFSET_Y*shrink,
                "Round 1");
        round.setFont(new Font(HardCodedParameters.COEF_FONT_SIZE *shrink*defaultMainHeight));

        Text textLife = new Text(0.2*shrink*defaultMainHeight, HardCodedParameters.defaultHeight*shrink/HardCodedParameters.GAME_OVER_TEXT_YPART+ HardCodedParameters.STAT_GAME_OVER_OFFSET_Y*shrink, "Vie : " + data.getJoueur().getHealth());
        textLife.setFont(new Font(HardCodedParameters.COEF_FONT_SIZE *shrink*defaultMainHeight));

        panel.getChildren().addAll(greets, score, textPositionJoueur, round, textLife);
        return panel;
    }


}
