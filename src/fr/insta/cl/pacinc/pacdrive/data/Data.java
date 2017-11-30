package fr.insta.cl.pacinc.pacdrive.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import fr.insta.cl.pacinc.pacdrive.data.model.Batiment;
import fr.insta.cl.pacinc.pacdrive.data.model.Hostile;
import fr.insta.cl.pacinc.pacdrive.data.model.Joueur;
import fr.insta.cl.pacinc.pacdrive.data.model.Kit;
import fr.insta.cl.pacinc.pacdrive.data.model.Piece;
import fr.insta.cl.pacinc.pacdrive.specifications.*;
import fr.insta.cl.pacinc.pacdrive.tools.*;

public class Data implements DataService {
	
	//models
	private List<BatimentService> batiments ;
	private List<HostileService> hostiles ;
	private List<KitService> kits;
	private List<MineService> mines ;
	private List<PieceService> pieces ;
	private String[] log;
	private JoueurService joueur ;
	private int life ;
	
	private Sound.SOUND sound;
	
	//attributs divers
	private int stepNumber;
	private int score ;
	
	public void init() {
		//creation
		batiments = new ArrayList<BatimentService>();
		hostiles = new ArrayList<HostileService>();
		kits = new ArrayList<KitService>();
		mines = new ArrayList<MineService>();
		pieces = new ArrayList<PieceService>() ;
		joueur = new Joueur(new Position(HardCodedParameters.heroesStartX, HardCodedParameters.heroesStartY));
		joueur.setHealth(HardCodedParameters.INIT_STARTING_HP);

		log = new String[HardCodedParameters.LOG_MESSAGES_MAX];
		for (int i=0; i < HardCodedParameters.LOG_MESSAGES_MAX; i++) {
			log[i] = "";
		}

		double x = -1;
		double y = -3;
		for (double i = 0; i < (17 + 0.95)*HardCodedParameters.BATIMENT_SIZE_X; i += HardCodedParameters.BATIMENT_SIZE_X) {
			for (double j = 0; j < (15 + 0.95) * HardCodedParameters.BATIMENT_SIZE_X; j += HardCodedParameters.BATIMENT_SIZE_X) {
				if (i == 0 || i == 17*HardCodedParameters.BATIMENT_SIZE_X) {
					batiments.add(new Batiment(new Position(x + i, y + j), ThreadLocalRandom.current().nextInt(0,5)));
				} else if (j == 0 || j == 15*HardCodedParameters.BATIMENT_SIZE_X) {
					batiments.add(new Batiment(new Position(x + i, y + j), ThreadLocalRandom.current().nextInt(0,5)));
				}
			}
		}

		double x_abs = HardCodedParameters.BATIMENT_SIZE_X;
		double y_ord =	HardCodedParameters.BATIMENT_SIZE_X;
		batiments.add(new Batiment(new Position(x + 10*x_abs, y + 4*y_ord), ThreadLocalRandom.current().nextInt(0,5)));
		batiments.add(new Batiment(new Position(x + 4*x_abs, y + 7*y_ord), ThreadLocalRandom.current().nextInt(0,5)));
		batiments.add(new Batiment(new Position(x + 13*x_abs, y + 7*y_ord), ThreadLocalRandom.current().nextInt(0,5)));


		double x2 = x + 2*HardCodedParameters.BATIMENT_SIZE_X;
		double y2 = y + 2*HardCodedParameters.BATIMENT_SIZE_X;
		for (double i = 0; i < (13 + 0.95)*HardCodedParameters.BATIMENT_SIZE_X; i += HardCodedParameters.BATIMENT_SIZE_X) {
			for (double j = 0; j < (11 + 0.95) * HardCodedParameters.BATIMENT_SIZE_X; j += HardCodedParameters.BATIMENT_SIZE_X) {
				if (i == 2*HardCodedParameters.BATIMENT_SIZE_X
						|| i == 5*HardCodedParameters.BATIMENT_SIZE_X
						|| i == 11*HardCodedParameters.BATIMENT_SIZE_X
						|| j == 2*HardCodedParameters.BATIMENT_SIZE_X
						|| j == 8*HardCodedParameters.BATIMENT_SIZE_X
						) {
					;
				} else {
					batiments.add(new Batiment(new Position(x2 + i, y2 + j), ThreadLocalRandom.current().nextInt(0,5)));
				}
			}
		}

		hostiles.add(new Hostile(new Position(x + 2*x_abs + x_abs / 2.0, y + y_ord + y_ord / 2.0), new Vitesse(5, 0), new Acceleration(0, 0), "avancee1"));
		hostiles.add(new Hostile(new Position(x + 10*x_abs + x_abs / 2.0, y + y_ord + y_ord / 2.0), new Vitesse(5, 0), new Acceleration(0, 0), "avancee1"));

		hostiles.add(new Hostile(new Position(x + 2*x_abs + x_abs / 2.0, y + 4*y_ord + y_ord / 2.0), new Vitesse(-5, 0), new Acceleration(0, 0), "avancee1"));
		hostiles.add(new Hostile(new Position(x + 10*x_abs + x_abs / 2.0, y + 10*y_ord + y_ord / 2.0), new Vitesse(-5, 0), new Acceleration(0, 0), "avancee1"));

		hostiles.add(new Hostile(new Position(x + x_abs + x_abs / 2.0, y + 2*y_ord + y_ord / 2.0), new Vitesse(0, 5), new Acceleration(0, 0), "avancee1"));
		hostiles.add(new Hostile(new Position(x + x_abs + x_abs / 2.0, y + 7*y_ord + y_ord / 2.0), new Vitesse(0, 5), new Acceleration(0, 0), "avancee1"));

		hostiles.add(new Hostile(new Position(x + 7*x_abs + x_abs / 2.0, y + 8*y_ord + y_ord / 2.0), new Vitesse(0, -5), new Acceleration(0, 0), "avancee1"));
		hostiles.add(new Hostile(new Position(x + 7*x_abs + x_abs / 2.0, y + 12*y_ord + y_ord / 2.0), new Vitesse(0, -5), new Acceleration(0, 0), "avancee1"));

		kits.add(new Kit(new Position(x + x_abs + x_abs / 4.0, y + y_ord + y_ord / 4.0)));
		kits.add(new Kit(new Position(x + 13*x_abs + x_abs / 4.0, y + 4*y_ord + y_ord / 4.0)));
		kits.add(new Kit(new Position(x + 7*x_abs + x_abs / 4.0, y + 10*y_ord + y_ord / 4.0)));

		pieces.add(new Piece(new Position(x + x_abs + x_abs / 4.0, y + 2*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + x_abs + x_abs / 4.0, y + 4*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + x_abs + x_abs / 4.0, y + 6*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + x_abs + x_abs / 4.0, y + 8*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + x_abs + x_abs / 4.0, y + 10*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + x_abs + x_abs / 4.0, y + 12*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + x_abs + x_abs / 4.0, y + 14*y_ord + y_ord / 4.0)));

		pieces.add(new Piece(new Position(x + 3*x_abs + x_abs / 4.0, y + 14*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 5*x_abs + x_abs / 4.0, y + 14*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 7*x_abs + x_abs / 4.0, y + 14*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 9*x_abs + x_abs / 4.0, y + 14*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 11*x_abs + x_abs / 4.0, y + 14*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 13*x_abs + x_abs / 4.0, y + 14*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 15*x_abs + x_abs / 4.0, y + 14*y_ord + y_ord / 4.0)));

		pieces.add(new Piece(new Position(x + 16*x_abs + x_abs / 4.0, y + 13*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 16*x_abs + x_abs / 4.0, y + 11*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 16*x_abs + x_abs / 4.0, y + 9*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 16*x_abs + x_abs / 4.0, y + 7*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 16*x_abs + x_abs / 4.0, y + 5*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 16*x_abs + x_abs / 4.0, y + 3*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 16*x_abs + x_abs / 4.0, y + y_ord + y_ord / 4.0)));

		pieces.add(new Piece(new Position(x + 14*x_abs + x_abs / 4.0, y + y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 12*x_abs + x_abs / 4.0, y + y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 10*x_abs + x_abs / 4.0, y + y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 8*x_abs + x_abs / 4.0, y + y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 6*x_abs + x_abs / 4.0, y + y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 4*x_abs + x_abs / 4.0, y + y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 2*x_abs + x_abs / 4.0, y + y_ord + y_ord / 4.0)));

		pieces.add(new Piece(new Position(x + 4*x_abs + x_abs / 4.0, y + 3*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 4*x_abs + x_abs / 4.0, y + 5*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 4*x_abs + x_abs / 4.0, y + 9*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 4*x_abs + x_abs / 4.0, y + 11*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 4*x_abs + x_abs / 4.0, y + 13*y_ord + y_ord / 4.0)));

		pieces.add(new Piece(new Position(x + 7*x_abs + x_abs / 4.0, y + 2*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 7*x_abs + x_abs / 4.0, y + 4*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 7*x_abs + x_abs / 4.0, y + 6*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 7*x_abs + x_abs / 4.0, y + 8*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 7*x_abs + x_abs / 4.0, y + 12*y_ord + y_ord / 4.0)));

		pieces.add(new Piece(new Position(x + 13*x_abs + x_abs / 4.0, y + 2*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 13*x_abs + x_abs / 4.0, y + 6*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 13*x_abs + x_abs / 4.0, y + 8*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 13*x_abs + x_abs / 4.0, y + 10*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 13*x_abs + x_abs / 4.0, y + 12*y_ord + y_ord / 4.0)));

		pieces.add(new Piece(new Position(x + 3*x_abs + x_abs / 4.0, y + 4*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 5*x_abs + x_abs / 4.0, y + 4*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 9*x_abs + x_abs / 4.0, y + 4*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 11*x_abs + x_abs / 4.0, y + 4*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 15*x_abs + x_abs / 4.0, y + 4*y_ord + y_ord / 4.0)));

		pieces.add(new Piece(new Position(x + 3*x_abs + x_abs / 4.0, y + 10*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 5*x_abs + x_abs / 4.0, y + 10*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 9*x_abs + x_abs / 4.0, y + 10*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 11*x_abs + x_abs / 4.0, y + 10*y_ord + y_ord / 4.0)));
		pieces.add(new Piece(new Position(x + 15*x_abs + x_abs / 4.0, y + 10*y_ord + y_ord / 4.0)));

	}

	
	//getters and setters
	public List<BatimentService> getBatiments() {
		return batiments;
	}

	public void setBatiments(List<BatimentService> batiments) {
		this.batiments = batiments;
	}

	public List<HostileService> getHostiles() {
		return hostiles;
	}

	public void setHostiles(List<HostileService> hostiles) {
		this.hostiles = hostiles;
	}

	public void addHostile(Position p, Vitesse v, Acceleration a, String comportement){
		this.hostiles.add(new Hostile(p, v, a, comportement));
	}

	public List<KitService> getKits() {
		return kits;
	}

	public void setKits(List<KitService> kits) {
		this.kits = kits;
	}

	public List<MineService> getMines() {
		return mines;
	}

	public void setMines(List<MineService> mines) {
		this.mines = mines;
	}

	public List<PieceService> getPieces() {
		return pieces;
	}

	public void setPieces(List<PieceService> pieces) {
		this.pieces = pieces;
	}

	public JoueurService getJoueur() {
		return joueur;
	}

	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

	public int getStepNumber() {
		return stepNumber;
	}

	public void setStepNumber(int stepNumber) {
		this.stepNumber = stepNumber;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}


	@Override
	public Position getHeroesPosition() {
		return joueur.getPosition();
	}


	@Override
	public double getHeroesWidth() {
		// TODO Auto-generated method stub
		return HardCodedParameters.heroesWidth;
	}


	@Override
	public double getHeroesHeight() {
		// TODO Auto-generated method stub
		return HardCodedParameters.heroesHeight;
	}


	@Override
	public double getPhantomWidth() {
		// TODO Auto-generated method stub
		return HardCodedParameters.phantomHeight;
	}


	@Override
	public double getPhantomHeight() {
		// TODO Auto-generated method stub
		return HardCodedParameters.phantomWidth;
	}
	  
	@Override
	public Sound.SOUND getSoundEffect() { return sound; }


	@Override
	public void addScore(int score){ this.score+=score; }

	@Override
	public void setJoueur(JoueurService joueur) {
		this.joueur = joueur ;
	}

	@Override
	public void setSoundEffect(Sound.SOUND s) { sound=s; }

	@Override
	public String[] getLog() {
		return log;
	}

	@Override
	public boolean gameIsOver() {
		return joueur.getHealth() < 1;
	}

	@Override
	public void setMessageForLog(String message) {
		for (int i = HardCodedParameters.LOG_MESSAGES_MAX - 1; i > 0; i--) {
			log[i] = log[i-1];
		}
		log[0] = message;
	}

	@Override
	public void setHeroesPosition(Position p) {
		this.joueur.setPosition(p) ;

	}



}
