/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/ReadService.java 2015-03-11 buixuan.
 * ******************************************************/
package fr.insta.cl.pacinc.pacdrive.specifications;


import java.util.ArrayList;

import fr.insta.cl.pacinc.pacdrive.tools.Position;
import fr.insta.cl.pacinc.pacdrive.tools.Sound;

public interface ReadService {
  public Position getHeroesPosition();
  public double getHeroesWidth();
  public double getHeroesHeight();
  public double getPhantomWidth();
  public double getPhantomHeight();
  public int getStepNumber();
  public int getScore();
  public ArrayList<PhantomService> getPhantoms();
  public Sound.SOUND getSoundEffect();
}
