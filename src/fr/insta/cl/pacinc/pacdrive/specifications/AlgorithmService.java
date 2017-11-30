/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/AlgorithmService.java 2015-03-11 buixuan.
 * ******************************************************/
package fr.insta.cl.pacinc.pacdrive.specifications;


import fr.insta.cl.pacinc.pacdrive.tools.Acceleration;
import fr.insta.cl.pacinc.pacdrive.tools.HardCodedParameters;


import java.util.concurrent.ThreadLocalRandom;

public interface AlgorithmService{
//  public void init();
  public void iaAvancee1(HostileService h) ;

  public void iaPrimitive(HostileService h) ;

  public void iaAvancee2(HostileService h) ;

  public int pulse_nord(HostileService h) ;

  public int pulse_sud(HostileService h) ;

  public int pulse_est(HostileService h) ;

  public int pulse_ouest(HostileService h) ;
}
