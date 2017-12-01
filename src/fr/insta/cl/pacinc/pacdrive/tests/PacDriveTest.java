package fr.insta.cl.pacinc.pacdrive.tests;

import fr.insta.cl.pacinc.pacdrive.data.model.Batiment;
import fr.insta.cl.pacinc.pacdrive.data.model.Joueur;
import fr.insta.cl.pacinc.pacdrive.engine.Engine;
import fr.insta.cl.pacinc.pacdrive.tools.HardCodedParameters;
import fr.insta.cl.pacinc.pacdrive.tools.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PacDriveTest {

    private Joueur joueur;
    private List<Batiment> batimentsAucuneCollision;
    private List<Batiment> batimentsAucuneCollisionLimite;
    private List<Batiment> batimentsCollision;
    private Engine engine;

    @Before
    public void init(){

        this.engine = new Engine();

        this.joueur = new Joueur(new Position(200, 300));

        this.batimentsAucuneCollision = Arrays.asList(
                new Batiment(new Position(190 - HardCodedParameters.BATIMENT_SIZE_X , 300)),
                new Batiment(new Position(200, 290 - HardCodedParameters.BATIMENT_SIZE_Y)),
                new Batiment(new Position(210 + HardCodedParameters.HERO_SIZE_X, 300)),
                new Batiment(new Position(200 , 310 + HardCodedParameters.HERO_SIZE_Y ))
        );

        this.batimentsAucuneCollisionLimite = Arrays.asList(
                new Batiment(new Position(200 - HardCodedParameters.BATIMENT_SIZE_X , 300)),
                new Batiment(new Position(200, 300 - HardCodedParameters.BATIMENT_SIZE_Y)),
                new Batiment(new Position(200 + HardCodedParameters.HERO_SIZE_X, 300)),
                new Batiment(new Position(200 , 300 + HardCodedParameters.HERO_SIZE_Y ))
        );

        this.batimentsCollision = Arrays.asList(
                new Batiment(new Position(220 , 310)),
                new Batiment(new Position(200 + HardCodedParameters.HERO_SIZE_X - 10, 320)),
                new Batiment(new Position(220, 300 + HardCodedParameters.HERO_SIZE_Y - 10 ))
        );
    }

    @Test
    public void testAucuneCollision(){
        for (Batiment batiment : this.batimentsAucuneCollision){
            assertFalse(this.engine.collisionBetweenPositionnables(this.joueur, batiment));
        }
    }

    @Test
    public void testAucuneCollisionLimite(){
        for (Batiment batiment : this.batimentsAucuneCollisionLimite){
            assertFalse(this.engine.collisionBetweenPositionnables(this.joueur, batiment));
        }
    }

    @Test
    public void testCollision(){
        for (Batiment batiment : this.batimentsCollision){
            assertTrue(this.engine.collisionBetweenPositionnables(this.joueur, batiment));
        }
    }
}
