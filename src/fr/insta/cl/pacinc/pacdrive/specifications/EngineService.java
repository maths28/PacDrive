/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/EngineService.java 2015-03-11 buixuan.
 * ******************************************************/
package fr.insta.cl.pacinc.pacdrive.specifications;

import fr.insta.cl.pacinc.pacdrive.tools.User;

public interface EngineService{
  public void init();
  public void start();
  public void stop();
  public void setHeroesCommand(User.COMMAND c);
  public void releaseHeroesCommand(User.COMMAND c);
  public boolean collisionBetweenPositionnables(PositionnableService object1, PositionnableService object2);
}
