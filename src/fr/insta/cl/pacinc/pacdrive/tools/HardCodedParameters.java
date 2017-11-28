/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: tools/HardCodedParameters.java 2015-03-11 buixuan.
 * ******************************************************/
package fr.insta.cl.pacinc.pacdrive.tools;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HardCodedParameters {
  //---HARD-CODED-PARAMETERS---//
  public static String defaultParamFileName = "in.parameters";
  public static final int defaultWidth = 800, defaultHeight = 600,
                          heroesStartX = 80, heroesStartY = 200, heroesWidth=60, heroesHeight=90, heroesStep = 10,
                          phantomWidth = 30, phantomHeight = 30, phantomStep = 10;
  public static final int enginePaceMillis = 100,
                          spriteSlowDownRate = 7;
  public static final double friction = 0.50;
  public static final double resolutionShrinkFactor = 0.95,
                             userBarShrinkFactor = 0.25,
                             menuBarShrinkFactor = 0.5,
                             logBarShrinkFactor = 0.15,
                             logBarCharacterShrinkFactor = 0.1175,
                             logBarCharacterShrinkControlFactor = 0.01275,
                             menuBarCharacterShrinkFactor = 0.175;
  public static final int displayZoneXStep = 5,
                          displayZoneYStep = 5,
                          displayZoneXZoomStep = 5,
                          displayZoneYZoomStep = 5;
  public static final double displayZoneAlphaZoomStep = 0.98;

  public static final int LOG_MESSAGES_MAX = 13;

  //---MISCELLANOUS---//
  public static final Object loadingLock = new Object();
  public static final String greetingsZoneId = String.valueOf(0xED1C7E),
                             simulatorZoneId = String.valueOf(0x51E77E);
  
  public static <T> T instantiate(final String className, final Class<T> type){
    try{
      return type.cast(Class.forName(className).newInstance());
    } catch(final InstantiationException e){
      throw new IllegalStateException(e);
    } catch(final IllegalAccessException e){
      throw new IllegalStateException(e);
    } catch(final ClassNotFoundException e){
      throw new IllegalStateException(e);
    }
  }

  //Positionnables sizes

  //hero
  public static final int HERO_SIZE_X = 80;
  public static final int HERO_SIZE_Y = 80;

  //hostile
  public static final int HOSTILE_SIZE_X = 80;
  public static final int HOSTILE_SIZE_Y = 80;

  //batiment
  public static final int BATIMENT_SIZE_X = 80;
  public static final int BATIMENT_SIZE_Y = 80;

  //mine
  public static final int MINE_SIZE_X = 80;
  public static final int MINE_SIZE_Y = 80;

  //kit
  public static final int KIT_SIZE_X = 80;
  public static final int KIT_SIZE_Y = 80;

  //piece
  public static final int PIECE_SIZE_X = 80;
  public static final int PIECE_SIZE_Y = 80;

  //Limite des zones

  public static final double OFFSET_X = .01*defaultHeight; ;
  public static final double OFFSET_Y = .01*defaultHeight; ;

  public static final double AREA_GAME_X_START = OFFSET_X;
  public static final double AREA_GAME_Y_START = OFFSET_Y;
  public static final double AREA_GAME_X_END = OFFSET_X + 0.68*defaultWidth;
  public static final double AREA_GAME_Y_END = OFFSET_Y + 0.8*defaultHeight;




}
