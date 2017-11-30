package fr.insta.cl.pacinc.pacdrive.algorithm;

import fr.insta.cl.pacinc.pacdrive.data.model.Hostile;
import fr.insta.cl.pacinc.pacdrive.specifications.*;
import fr.insta.cl.pacinc.pacdrive.tools.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class IaAlgorithm implements AlgorithmService, RequireEngineService, RequireDataService {
    private EngineService engine;
    private DataService data;

    private Random gen= new Random();

    @Override
    public void bindEngineService(EngineService service){
        engine = service;
    }

    @Override
    public void bindDataService(DataService service) {
        this.data = service ;
    }

    public void iaAvancee1(HostileService h) {

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

    public int pulse_nord(HostileService h) {
        int free_space = 0;
        HostileService hostile_translated = new Hostile(new Position(h.getPositionX(),h.getPositionY()),new Vitesse(0,0),new Acceleration(0,0),"");
        for (double i = HardCodedParameters.PULSE_STEP; i < (HardCodedParameters.NB_PULSES + 0.95)*HardCodedParameters.PULSE_STEP; i += HardCodedParameters.PULSE_STEP) {
            hostile_translated.setPosition(hostile_translated.getPositionX(),hostile_translated.getPositionY() - i);
            for (BatimentService b : data.getBatiments()) {
                if (engine.collisionBetweenPositionnables(hostile_translated, b)) {
                    return free_space;
                }
            }
            free_space++;
        }
        return free_space;
    }

    public int pulse_sud(HostileService h) {
        int free_space = 0;
        HostileService hostile_translated = new Hostile(new Position(h.getPositionX(),h.getPositionY()),new Vitesse(0,0),new Acceleration(0,0),"");
        for (double i = HardCodedParameters.PULSE_STEP; i < (HardCodedParameters.NB_PULSES + 0.95)*HardCodedParameters.PULSE_STEP; i += HardCodedParameters.PULSE_STEP) {
            hostile_translated.setPosition(hostile_translated.getPositionX(),hostile_translated.getPositionY() + i);
            for (BatimentService b : data.getBatiments()) {
                if (engine.collisionBetweenPositionnables(hostile_translated, b)) {
                    return free_space;
                }
            }
            free_space++;
        }
        return free_space;
    }

    public int pulse_est(HostileService h) {
        int free_space = 0;
        HostileService hostile_translated = new Hostile(new Position(h.getPositionX(),h.getPositionY()),new Vitesse(0,0),new Acceleration(0,0),"");
        for (double i = HardCodedParameters.PULSE_STEP; i < (HardCodedParameters.NB_PULSES + 0.95)*HardCodedParameters.PULSE_STEP; i += HardCodedParameters.PULSE_STEP) {
            hostile_translated.setPosition(hostile_translated.getPositionX() + i,hostile_translated.getPositionY());
            for (BatimentService b : data.getBatiments()) {
                if (engine.collisionBetweenPositionnables(hostile_translated, b)) {
                    return free_space;
                }
            }
            free_space++;
        }
        return free_space;
    }

    public int pulse_ouest(HostileService h) {
        int free_space = 0;
        HostileService hostile_translated = new Hostile(new Position(h.getPositionX(),h.getPositionY()),new Vitesse(0,0),new Acceleration(0,0),"");
        for (double i = HardCodedParameters.PULSE_STEP; i < (HardCodedParameters.NB_PULSES + 0.95)*HardCodedParameters.PULSE_STEP; i += HardCodedParameters.PULSE_STEP) {
            hostile_translated.setPosition(hostile_translated.getPositionX() - i,hostile_translated.getPositionY());
            for (BatimentService b : data.getBatiments()) {
                if (engine.collisionBetweenPositionnables(hostile_translated, b)) {
                    return free_space;
                }
            }
            free_space++;
        }
        return free_space;
    }


}
