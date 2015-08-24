package ludum.dare.world;

import com.badlogic.gdx.math.Vector2;
import com.winger.log.HTMLLogger;
import ludum.dare.trait.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jake on 8/21/2015.
 */
public class AIHiveMind {
    private final static HTMLLogger log = HTMLLogger.getLogger(AIHiveMind.class);

    static List<GameObject> Players = new ArrayList<>();
    static List<GameObject> Enemies = new ArrayList<>();

    public static void update() {
        for (GameObject p : Players){
            for (GameObject e : Enemies){
                minionLogic(e, p);
                }
            }
        }

    public static void addPlayer(GameObject player){
        Players.add(player);
    }
    public static void addEnemy(GameObject enemyBasic){
        Enemies.add(enemyBasic);
    }
    private void removeMarkedGameObjects() {
        for(GameObject e : Enemies){
            if(e.shouldBeDeleted()){
                Enemies.remove(e);
            }
        }
        for(GameObject p : Players){
            if(p.shouldBeDeleted()){
                Players.remove(p);
            }
        }
    }
    public static void minionLogic(GameObject e, GameObject p){

        List traits = e.getTraits(AIMovementAggressiveTrait.class, AIMovementRangedTrait.class, AIMovementRetreatTrait.class);
        Vector2 pPos = new Vector2(p.getTrait(PositionTrait.class).x, p.getTrait(PositionTrait.class).y);

        if (e instanceof ShockCan) {
            ((ShockCan) e).target = pPos;
        }

        if(traits.get(0) != null) {
            if (e instanceof EnemyBasic) {
                ((EnemyBasic) e).target = pPos;
            } else if (e instanceof EnemyHeavy) {
                ((EnemyHeavy) e).target = pPos;
            }
        }
        if(traits.get(1) != null){
            e.getTrait(AIMovementRangedTrait.class).updateMovement(pPos);
        }
        if(traits.get(2) != null){
            e.getTrait(AIMovementRetreatTrait.class).updateMovement(pPos);
        }
    }
}
