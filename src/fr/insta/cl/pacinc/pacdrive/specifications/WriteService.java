/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/WriteService.java 2015-03-11 buixuan.
 * ******************************************************/
package fr.insta.cl.pacinc.pacdrive.specifications;

import java.util.ArrayList;
import java.util.List;

import fr.insta.cl.pacinc.pacdrive.data.model.Joueur;
import fr.insta.cl.pacinc.pacdrive.tools.Acceleration;
import fr.insta.cl.pacinc.pacdrive.tools.Position;
import fr.insta.cl.pacinc.pacdrive.tools.Sound;
import fr.insta.cl.pacinc.pacdrive.tools.Vitesse;

public interface WriteService {
  public void setHeroesPosition(Position p);
  public void setStepNumber(int n);
  public void addHostile(Position p, Vitesse v, Acceleration a, String comportement);
  public void setHostiles(List<HostileService> hostiles);
  public void setSoundEffect(Sound.SOUND s);
  public void addScore(int score);
  public void setJoueur(Joueur joueur);
}
