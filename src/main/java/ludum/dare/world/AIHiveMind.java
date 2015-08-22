package ludum.dare.world;

import com.badlogic.gdx.math.Vector2;
import com.winger.log.HTMLLogger;
import ludum.dare.Conf;
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
        if(enemyBasic.getTrait(EnemyBasicBehaviorTrait.class) != null){
            enemyBasic.getTrait(HealthTrait.class).setHealth(100);
        }
        if(enemyBasic.getTrait(EnemyThrowerBehaviorTrait.class) != null){
            enemyBasic.getTrait(HealthTrait.class).setHealth(80);
        }
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
        if(e.getTrait(EnemyBasicBehaviorTrait.class) != null){
            enemyBasicBehaviorLogic(e, p);
        }
        if(e.getTrait(EnemyThrowerBehaviorTrait.class) != null){
            enemyThrowerBehaviorLogic(e, p);
        }
    }

    private static void enemyThrowerBehaviorLogic(GameObject e, GameObject p) {
        Vector2 vel = new Vector2(0,0);
        if((p.getTrait(PositionTrait.class).x < e.getTrait(PositionTrait.class).x)
                && (e.getTrait(PositionTrait.class).x - p.getTrait(PositionTrait.class).x)>20){
            vel.x -= 10.0;
        }
        if((p.getTrait(PositionTrait.class).x >= e.getTrait(PositionTrait.class).x)
                && (p.getTrait(PositionTrait.class).x - e.getTrait(PositionTrait.class).x)>20){
            vel.x += 10.0;
        }
        if((p.getTrait(PositionTrait.class).y < e.getTrait(PositionTrait.class).y)
                && (e.getTrait(PositionTrait.class).y - p.getTrait(PositionTrait.class).y)>20){
            vel.y -= 10.0;
        }
        if((p.getTrait(PositionTrait.class).y >= e.getTrait(PositionTrait.class).y)
                && (p.getTrait(PositionTrait.class).y - e.getTrait(PositionTrait.class).y)>20){
            vel.y += 10.0;
        }
        if((p.getTrait(PositionTrait.class).x < e.getTrait(PositionTrait.class).x)
                && (e.getTrait(PositionTrait.class).x - p.getTrait(PositionTrait.class).x)<15){
            vel.x += 7.0;
        }
        if((p.getTrait(PositionTrait.class).x >= e.getTrait(PositionTrait.class).x)
                && (p.getTrait(PositionTrait.class).x - e.getTrait(PositionTrait.class).x)<15){
            vel.x -= 7.0;
        }
        if((p.getTrait(PositionTrait.class).y < e.getTrait(PositionTrait.class).y)
                && (e.getTrait(PositionTrait.class).y - p.getTrait(PositionTrait.class).y)<15){
            vel.y += 7.0;
        }
        if((p.getTrait(PositionTrait.class).y >= e.getTrait(PositionTrait.class).y)
                && (p.getTrait(PositionTrait.class).y - e.getTrait(PositionTrait.class).y)<15){
            vel.y -= 7.0;
        }
        e.getTrait(PhysicalTrait.class).body.body.setLinearVelocity(vel);
    }

    public static void enemyBasicBehaviorLogic(GameObject e, GameObject p){
        Vector2 vel = new Vector2(0, 0);
        if((p.getTrait(PositionTrait.class).x < e.getTrait(PositionTrait.class).x)
            && (e.getTrait(PositionTrait.class).x - p.getTrait(PositionTrait.class).x)>2){
            vel.x -= 10.0;
            log.debug("Enemy: Want to move left");
        }
        if((p.getTrait(PositionTrait.class).x >= e.getTrait(PositionTrait.class).x)
            && (p.getTrait(PositionTrait.class).x - e.getTrait(PositionTrait.class).x)>2){
            vel.x += 10.0;
            log.debug("Enemy: Want to move right");
        }
        if((p.getTrait(PositionTrait.class).y < e.getTrait(PositionTrait.class).y)
            && (e.getTrait(PositionTrait.class).y - p.getTrait(PositionTrait.class).y)>2){
            vel.y -= 10.0;
            log.debug("Enemy: Want to move down");
        }
        if((p.getTrait(PositionTrait.class).y >= e.getTrait(PositionTrait.class).y)
            && (p.getTrait(PositionTrait.class).y - e.getTrait(PositionTrait.class).y)>2){
            vel.y += 10.0;
            log.debug("Enemy: Want to move up");
        }
        e.getTrait(PhysicalTrait.class).body.body.setLinearVelocity(vel);
    }
}
