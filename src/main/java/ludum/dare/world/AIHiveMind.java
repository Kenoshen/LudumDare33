package ludum.dare.world;

import ludum.dare.trait.AITrait;
import ludum.dare.trait.GameObject;
import ludum.dare.trait.PositionTrait;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jake on 8/21/2015.
 */
public class AIHiveMind {
    static List<GameObject> Players = new ArrayList<>();
    static List<GameObject> Enemies = new ArrayList<>();

    public static void update() {
        for (GameObject p : Players){
            for (GameObject e : Enemies){
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
}
