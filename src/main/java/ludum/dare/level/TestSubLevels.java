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
    public GameScreen gameScreen;

    List<GameObject> objs;

    private Player player = null;

    @Override
    public String name(){
        return this.getClass().getSimpleName();
    }

    private int index = -1;

    @Override
    public List<GameObject> loadLevel(){
//        AtlasManager.instance.loadAtlas("packed/game.atlas");
//        AtlasManager.instance.loadAtlas("packed/game_n.atlas");
//        AtlasManager.instance.finishLoading();
        return nextSection();
    }

    public List<GameObject> nextSection(){
        index ++;
        if (index == 0){
            return section0(index);
        } else if (index == 1){
            return section1(index);
        } else if (index == 2){
            return section2(index);
        } else if (index == 3){
            return section3(index);
        } else if (index == 4){
            return section4(index);
        } else if (index == 5){
            return section5(index);
        } else if (index == 6){
            return section6(index);
        } else {
            throw new RuntimeException("No more sections in this level");
        }
    }


    private List<GameObject> section0(int section){
        objs = new ArrayList<>();

        sectionSetup(section);

        path(section, new Vector2(0, -3), new Vector2(screenWidth, -1));
        light(new Color(0.5f, 0.5f, 1.0f, 1), 10, 1000, new Vector2(-7, 13), new Vector2(20, 13));

        float numOfSections = 8;
        light(Color.RED.cpy(), 15, 1500 * numOfSections, new Vector2(-48, -13), new Vector2(48 * numOfSections, -13));
        objs.add(new Chicken(0, -5, 0, 6));

        return objs;
    }

    private List<GameObject> section1(int section){
        objs = new ArrayList<>();

        sectionSetup(section);

        path(section, new Vector2(0, -1), new Vector2(screenWidth, -1));

        light(Color.BLUE.cpy(), 20, 0, new Vector2(xOffset(section) + 13, 5));

        final Boundary rightSideBoundary = newRightSideBoundary(section);
        enemyWaves(section, 2, EnemyWaveType.EASY, rightSideBoundary);

        return objs;
    }

    private List<GameObject> section2(int section){
        objs = new ArrayList<>();

        sectionSetup(section);

        path(section, new Vector2(0, -1), new Vector2(screenWidth - 5, -1), new Vector2(screenWidth, -8));

        float left = xOffset(section);
        light(new Color(0.5f, 0.5f, 1.0f, 1), 10, 1000, new Vector2(left + 3, 13), new Vector2(left + halfScreenWidth + 3, 13));

        final Boundary rightSideBoundary = newRightSideBoundary(section);
        enemyWaves(section, 2, EnemyWaveType.MEDIUM, rightSideBoundary);
        objs.add(new Chicken(xOffset(section) + halfScreenWidth + 10, -3, 0, 6));

        return objs;
    }

    private List<GameObject> section3(int section){
        objs = new ArrayList<>();

        sectionSetup(section);

        path(section, new Vector2(0, -8), new Vector2(screenWidth, -8));

        float left = xOffset(section);
        light(Color.YELLOW, 25, 1000, new Vector2(left + 11.8f, 6));
        light(Color.YELLOW, 25, 1000, new Vector2(left + halfScreenWidth + 9.5f, 6));

        final Boundary rightSideBoundary = newRightSideBoundary(section);
        enemyWaves(section, 2, EnemyWaveType.MEDIUM, rightSideBoundary);

        return objs;
    }

    private List<GameObject> section4(int section){
        objs = new ArrayList<>();

        sectionSetup(section);

        path(section, new Vector2(0, -1), new Vector2(screenWidth, -1));

        light(Color.BLUE.cpy(), 20, 0, new Vector2(xOffset(section) + halfScreenWidth + 8, 6));

        final Boundary rightSideBoundary = newRightSideBoundary(section);
        enemyWaves(section, 3, EnemyWaveType.MEDIUM, rightSideBoundary);
        objs.add(new Chicken(xOffset(section) + halfScreenWidth + 10, -3, 0, 6));
        return objs;
    }

    private List<GameObject> section5(int section){
        objs = new ArrayList<>();

        sectionSetup(section);

        path(section, new Vector2(0, -1), new Vector2(screenWidth, -1));

        light(Color.YELLOW.cpy(), 40, 0, new Vector2(xOffset(section) + 15.3f, 11));

        final Boundary rightSideBoundary = newRightSideBoundary(section);
        enemyWaves(section, 2, EnemyWaveType.HARD, rightSideBoundary);
        objs.add(new Chicken(xOffset(section) + halfScreenWidth + 10, -3, 0, 6));
        return objs;
    }


    private List<GameObject> section6(final int section){
        objs = new ArrayList<>();

        sectionSetup(section);

        path(section,
                new Vector2(0, -1),
                new Vector2(screenWidth - 16, -1),
                new Vector2(screenWidth - 0, -6),
                new Vector2(screenWidth - 6, -13));

        float left = xOffset(section);
        light(Color.YELLOW.cpy(), 40, 0, new Vector2(xOffset(section) + 9.5f, 13));

        final Boundary rightSideBoundary = path(section,
                new Vector2(screenWidth - 6, halfScreenHeight),
                new Vector2(screenWidth - 6, -halfScreenHeight));

        enemyWaves(section, 1, EnemyWaveType.BOSS, rightSideBoundary);


        final BlankObject trigger = new BlankObject();
        trigger.addAndInitializeTrait(new UpdatableTrait(trigger, new Runnable() {
            @Override
            public void run() {
                if (player.getTrait(PositionTrait.class).x > (xOffset(section) + screenWidth - 11)) {
                    gameScreen.endGame();
                }
            }
        }));
        objs.add(trigger);

        return objs;
    }

    // //////////////////////////////////////////
    // HELPER METHODS FOR CREATING LEVELS
    // //////////////////////////////////////////
    private enum EnemyWaveType{
        EASY,
        MEDIUM,
        HARD,
        BOSS
    }


    private void enemyWaves(int section, int number, EnemyWaveType type, final Boundary rightBoundary){
        if (number <= 0){
            throw new RuntimeException("Enemy waves has to be greater than 0");
        }
        List<List<GameObject>> enemyWaves = new ArrayList<>();
        for (int i = 0; i < number; i++){
            List<GameObject> enemies = new ArrayList<>();
            switch (type){
                case EASY:
                    enemies.add(new EnemyBasic(xOffset(section) + screenWidth, -(halfScreenHeight - 3), 0));
                    break;
                case MEDIUM:
                    enemies.add(new EnemyBasic(xOffset(section) + screenWidth, -(halfScreenHeight - 3), 0));
                    enemies.add(new EnemyThrower(xOffset(section) + screenWidth, -(halfScreenHeight - 5), 0));
                    break;
                case HARD:
                    enemies.add(new EnemyBasic(xOffset(section) + screenWidth, -(halfScreenHeight - 3), 0));
                    enemies.add(new EnemyThrower(xOffset(section) + screenWidth, -(halfScreenHeight - 4), 0));
                    enemies.add(new EnemyHeavy(xOffset(section) + screenWidth, -(halfScreenHeight - 5), 0));
                    break;
                case BOSS:
                    ShockCan can1 = new ShockCan(xOffset(section) + 10 + halfScreenWidth, -2, 0);
                    ShockCan can2 = new ShockCan(xOffset(section) + 18 + halfScreenWidth, -11, 0);
                    SpawnerDoor door = new SpawnerDoor(xOffset(section) + -14.5f + halfScreenWidth, 1, 0);
                    enemies.add(can1);
                    enemies.add(can2);
                    GameScreen.addObject(door);

                    GameScreen.addObject(new Boss(can1, can2, door));
                    break;
            }

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
                            if (obj.shouldBeDeleted() || obj.getTrait(HealthTrait.class).health <= 0){
                                continue;
                            }
                            allDead = false;
                            break;
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
                            if (obj.shouldBeDeleted() || obj.getTrait(HealthTrait.class).health <= 0){
                                continue;
                            }
                            allDead = false;
                            break;
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


    private void sectionSetup(final int section){
        objs.add(new Background(section, 0.01f, "environment/background"));
        objs.add(new Background(section, "environment/" + section + "frame"));
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

        if (player == null){
            player = new Player(xOffset(section), -(halfScreenHeight - 1), 0, CMouse.instance, CKeyboard.instance, null);
            player.getTrait(HealthTrait.class).health = 60;
            objs.add(player);
        }
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
