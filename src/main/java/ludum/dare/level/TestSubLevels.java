package ludum.dare.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import ludum.dare.screen.GameScreen;
import ludum.dare.trait.*;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.Sprite;
import ludum.dare.world.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/6/15.
 */
public class TestSubLevels extends Level{
    private float screenWidth = 48;
    private float screenHeight = 27;
    private float halfScreenWidth = 24;
    private float halfScreenHeight = 13.5f;

    List<GameObject> objs;

    private Player player;

    @Override
    public String name(){
        return this.getClass().getSimpleName();
    }

    private int index = -1;

    @Override
    public List<GameObject> loadLevel(){
        return nextSection();
    }

    public List<GameObject> nextSection(){
        index ++;
        if (index == 0){
            return section0();
        } else if (index == 1){
            return section1();
        }else {
            throw new RuntimeException("No more sections in this level");
        }
    }


    private List<GameObject> section0(){
        objs = new ArrayList<>();

        sectionSetup(0, "background_one");
        player = new Player(0, -5, 0, CMouse.instance, CKeyboard.instance, null);
        objs.add(player);
        path(0, new Vector2(0, -3), new Vector2(screenWidth, -1));
        light(new Color(0.5f, 0.5f, 1.0f, 1), 10, 1000, new Vector2(-7, 13), new Vector2(20, 13));

        float numOfSections = 8;
        light(Color.RED.cpy(), 15, 1500 * numOfSections, new Vector2(-48, -13), new Vector2(48 * numOfSections, -13));


        return objs;
    }

    private List<GameObject> section1(){
        objs = new ArrayList<>();

        sectionSetup(1, "background_two");

        path(1, new Vector2(0, -1), new Vector2(screenWidth, -1));

        light(Color.BLUE.cpy(), 20, 0, new Vector2(xOffset(1) + 13, 5));

        final Boundary rightSideBoundary = newRightSideBoundary(1);
        enemyWaves(1, 2, EnemyWaveType.EASY, rightSideBoundary);

        return objs;
    }

    // //////////////////////////////////////////
    // HELPER METHODS FOR CREATING LEVELS
    // //////////////////////////////////////////
    private enum EnemyWaveType{
        EASY,
        MEDIUM,
        HARD
    }


    private void enemyWaves(int section, int number, EnemyWaveType type, final Boundary rightBoundary){
        if (number <= 0){
            throw new RuntimeException("Enemy waves has to be greater than 0");
        }
        List<List<GameObject>> enemyWaves = new ArrayList<>();
        for (int i = 0; i < number; i++){
            List<GameObject> enemies = new ArrayList<>();
            enemies.add(new EnemyThrower(xOffset(section) + halfScreenWidth, -5, 0));
            enemyWaves.add(enemies);
        }
        for (int i = 0; i < number; i++){
            final BlankObject trigger = new BlankObject();
            objs.add(trigger);
            final List<GameObject> curEnemyWave = enemyWaves.get(i);
            if (i + 1 < number){
                final List<GameObject> nextEnemyWave = enemyWaves.get(i + 1);
                // call next wave
                trigger.addAndInitializeTrait(new UpdatableTrait(trigger, new Runnable() {
                    @Override
                    public void run() {
                        boolean allDead = true;
                        for(GameObject obj : curEnemyWave){
                            if (! obj.shouldBeDeleted()){
                                allDead = false;
                                break;
                            }
                        }
                        if (allDead){
                            for(GameObject obj : nextEnemyWave){
                                obj.initializeTraits();
                            }
                            GameScreen.addObjects(nextEnemyWave);
                            trigger.markForDeletion();
                        }
                    }
                }));
            }else {
                // delete boundary when they all die instead of call next wave
                trigger.addAndInitializeTrait(new UpdatableTrait(trigger, new Runnable() {
                    @Override
                    public void run() {
                        boolean allDead = true;
                        for(GameObject obj : curEnemyWave){
                            if (! obj.shouldBeDeleted()){
                                allDead = false;
                                break;
                            }
                        }
                        if (allDead){
                            rightBoundary.markForDeletion();
                            trigger.markForDeletion();
                        }
                    }
                }));
            }
        }
        for(GameObject obj : enemyWaves.get(0)){
            obj.initializeTraits();
        }
        GameScreen.addObjects(enemyWaves.get(0));
    }


    private void sectionSetup(final int section, String background){
        objs.add(new Background(section, 0.01f, "environment/background"));
        objs.add(new Background(section, "environment/" + background));
        newBottomBoundary(section);


        final CameraTarget target = new CameraTarget(xOffset(section) + halfScreenWidth, 0);
        objs.add(target);

        final BlankObject trigger = new BlankObject();
        trigger.addAndInitializeTrait(new UpdatableTrait(trigger, new Runnable() {
            @Override
            public void run() {
                if (player.getTrait(PositionTrait.class).x > (xOffset(section + 1) + 3)) {
                    trigger.markForDeletion();
                    target.markForDeletion();
                    GameScreen.addObjects(nextSection());
                }
            }
        }));
        objs.add(trigger);
    }

    private Boundary newBottomBoundary(int section){
        float left = xOffset(section);
        float right = left + screenWidth;
        Boundary b = new Boundary(new Vector2(left - 5, halfScreenHeight), new Vector2(left - 5, -halfScreenHeight), new Vector2(right, -halfScreenHeight));
        objs.add(b);
        return b;
    }

    private Boundary newRightSideBoundary(int section){
        float left = xOffset(section);
        float right = left + screenWidth;
        Boundary b = new Boundary(new Vector2(right, halfScreenHeight), new Vector2(right, -halfScreenHeight));
        objs.add(b);
        return b;
    }

    private Boundary path(int section, Vector2... path){
        float xOffset = xOffset(section);
        Vector2[] vs = new Vector2[path.length];
        for (int i = 0; i < path.length; i++){
            vs[i] = new Vector2(path[i].x + xOffset, path[i].y);
        }
        Boundary b = new Boundary(vs);
        objs.add(b);
        return b;
    }

    private void light(Color color, int weakness, float travelTime, Vector2... path){
        if (path.length == 1){
            Vector2 tmp = new Vector2(path[0].x, path[0].y);
            path = new Vector2[2];
            path[0] = tmp.cpy();
            path[1] = tmp.cpy();
        }

        BlankObject lp = new BlankObject();
        lp.traits.add(new PositionTrait(lp, path[0].x, path[0].y, 0));
        LightTrait lt = new LightTrait(lp, color);
        lt.z = 0.01f;
        lt.attenuation.z = weakness;
        lp.traits.add(lt);
        PathFollowerTrait pft = new PathFollowerTrait(lp, PathFollowerTrait.PathFollowStyle.LOOP, 0, path);
        pft.travelTimeInMilliseconds = travelTime;
        lp.traits.add(pft);
        lp.initializeTraits();
        objs.add(lp);
    }

    private float xOffset(int section){
        return (section * screenWidth) - halfScreenWidth;
    }
}
