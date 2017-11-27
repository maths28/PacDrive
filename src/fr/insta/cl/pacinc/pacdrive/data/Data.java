package fr.insta.cl.pacinc.pacdrive.data;

import java.util.ArrayList;
import java.util.List;

import fr.insta.cl.pacinc.pacdrive.data.ia.MoveLeftPhantom;
import fr.insta.cl.pacinc.pacdrive.data.model.Batiment;
import fr.insta.cl.pacinc.pacdrive.data.model.Hostile;
import fr.insta.cl.pacinc.pacdrive.data.model.Joueur;
import fr.insta.cl.pacinc.pacdrive.data.model.Kit;
import fr.insta.cl.pacinc.pacdrive.data.model.Mine;
import fr.insta.cl.pacinc.pacdrive.data.model.Piece;
import fr.insta.cl.pacinc.pacdrive.data.model.Terrain;
import fr.insta.cl.pacinc.pacdrive.specifications.DataService;
import fr.insta.cl.pacinc.pacdrive.specifications.PhantomService;
import fr.insta.cl.pacinc.pacdrive.tools.HardCodedParameters;
import fr.insta.cl.pacinc.pacdrive.tools.Position;
import fr.insta.cl.pacinc.pacdrive.tools.Sound;
import fr.insta.cl.pacinc.pacdrive.tools.Sound.SOUND;

public class Data implements DataService {
	
	//models
	private List<Batiment> batiments ;
	private List<Hostile> hostiles ;
	private List<Kit> kits;
	private List<Mine> mines ;
	private List<Piece> pieces ;
	private Joueur joueur ;
	private Terrain terrain ;
		  private ArrayList<PhantomService> phantoms;
	
	private Sound.SOUND sound;
	
	//attributs divers
	private int stepNumber;
	private int score ;
	
	public void init() {
		//creation
		batiments = new ArrayList<Batiment>();
		hostiles = new ArrayList<Hostile>();
		kits = new ArrayList<Kit>();
		mines = new ArrayList<Mine>();
		pieces = new ArrayList<Piece>() ;
		joueur = new Joueur();
		terrain = new Terrain() ;
		
		//joueur
		joueur.setPosition(HardCodedParameters.heroesStartX, HardCodedParameters.heroesStartY);
//		joueur.setHealth(health);
//		joueur.setMunition(munition);

		phantoms = new ArrayList<>();
	}

	
	//getters and setters
	public List<Batiment> getBatiments() {
		return batiments;
	}

	public void setBatiments(List<Batiment> batiments) {
		this.batiments = batiments;
	}

	public List<Hostile> getHostiles() {
		return hostiles;
	}

	public void setHostiles(List<Hostile> hostiles) {
		this.hostiles = hostiles;
	}

	public List<Kit> getKits() {
		return kits;
	}

	public void setKits(List<Kit> kits) {
		this.kits = kits;
	}

	public List<Mine> getMines() {
		return mines;
	}

	public void setMines(List<Mine> mines) {
		this.mines = mines;
	}

	public List<Piece> getPieces() {
		return pieces;
	}

	public void setPieces(List<Piece> pieces) {
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
	  public ArrayList<PhantomService> getPhantoms(){ return phantoms; }
	  
	  @Override
	  public Sound.SOUND getSoundEffect() { return sound; }

	   
	  @Override
	  public void addScore(int score){ this.score+=score; }

	  @Override
	  public void addPhantom(Position p) { phantoms.add(new MoveLeftPhantom(p)); }
	  
	  @Override
	  public void setPhantoms(ArrayList<PhantomService> phantoms) { this.phantoms=phantoms; }
	  
	  @Override
	  public void setSoundEffect(Sound.SOUND s) { sound=s; }


	@Override
	public void setHeroesPosition(Position p) {
		this.joueur.position = p ;
		
	}

}
