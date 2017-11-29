package fr.insta.cl.pacinc.pacdrive.data;

import java.util.ArrayList;
import java.util.List;

import fr.insta.cl.pacinc.pacdrive.data.model.Batiment;
import fr.insta.cl.pacinc.pacdrive.data.model.Hostile;
import fr.insta.cl.pacinc.pacdrive.data.model.Joueur;
import fr.insta.cl.pacinc.pacdrive.data.model.Kit;
import fr.insta.cl.pacinc.pacdrive.data.model.Mine;
import fr.insta.cl.pacinc.pacdrive.data.model.Piece;
import fr.insta.cl.pacinc.pacdrive.data.model.Terrain;
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
	private Joueur joueur ;
	private Terrain terrain ;
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
		terrain = new Terrain() ;

		log = new String[HardCodedParameters.LOG_MESSAGES_MAX];
		for (int i=0; i < HardCodedParameters.LOG_MESSAGES_MAX; i++) {
			log[i] = "";
		}

		for (double i = 0; i < (6 + 0.95)*HardCodedParameters.BATIMENT_SIZE_X; i += HardCodedParameters.BATIMENT_SIZE_X) {
			for (double j = 0; j < (3 + 0.95)*HardCodedParameters.BATIMENT_SIZE_X; j += HardCodedParameters.BATIMENT_SIZE_X) {
				if (i == 3*HardCodedParameters.BATIMENT_SIZE_X || j == 2*HardCodedParameters.BATIMENT_SIZE_X) {
					;
				} else {
					batiments.add(new Batiment(new Position(150 + i, 150 + j)));
				}
			}
		}

		double x = 90;
		double y = 90;
		for (double i = 0; i < (10 + 0.95)*HardCodedParameters.BATIMENT_SIZE_X; i += HardCodedParameters.BATIMENT_SIZE_X) {
			for (double j = 0; j < (7 + 0.95) * HardCodedParameters.BATIMENT_SIZE_X; j += HardCodedParameters.BATIMENT_SIZE_X) {
				if (i == 0 /*|| i == 5*HardCodedParameters.BATIMENT_SIZE_X */|| i == 10*HardCodedParameters.BATIMENT_SIZE_X) {
					batiments.add(new Batiment(new Position(x + i, y + j)));
				} else if (j == 0 || j == 7*HardCodedParameters.BATIMENT_SIZE_X) {
					batiments.add(new Batiment(new Position(x + i, y + j)));
				}
			}
		}

		hostiles.add(new Hostile(new Position(215,215), new Vitesse(-10, 0), new Acceleration(0,0) , "IA" ));
		hostiles.add(new Hostile(new Position(285,215), new Vitesse(10, 0), new Acceleration(0,0) , "IA" ));

		hostiles.add(new Hostile(new Position(245,145), new Vitesse(0, -10), new Acceleration(0,0) , "IA" ));
		hostiles.add(new Hostile(new Position(245,175), new Vitesse(0, 10), new Acceleration(0,0) , "IA" ));
//		kits.add(new Kit(new Position(12, 58)));
//		mines.add(new Mine(new Position(37, 125)));
//		pieces.add(new Piece(new Position(127, 10)));

		//A AJOUTER
//		for(double j = 10; j < HardCodedParameters.defaultHeight; j+=70) {
//			for (double i = 0; i < HardCodedParameters.defaultWidth; i += 30) {
//				if (i % 90 == 0) {
//					batiments.add(new Batiment(new Position(i, j)));
//					batiments.add(new Batiment(new Position(i, j+10)));
//					if(i + 20 < HardCodedParameters.defaultWidth){
//						batiments.add(new Batiment(new Position(i + 10, j)));
//						batiments.add(new Batiment(new Position(i + 10, j+10)));
//					}
//				}
//			}
//		}
		//FIN AJOUT
/*		joueur.setHealth(health);
		joueur.setMunition(munition);*/

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

	public Joueur getJoueur() {
		return joueur;
	}

	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

	public Terrain getTerrain() {
		return terrain;
	}

	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
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
		return joueur.position;
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
	public void setSoundEffect(Sound.SOUND s) { sound=s; }

	@Override
	public String[] getLog() {
		return log;
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
		this.joueur.position = p ;

	}



}
