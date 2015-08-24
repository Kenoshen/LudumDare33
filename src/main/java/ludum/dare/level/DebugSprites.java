package ludum.dare.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import ludum.dare.trait.GameObject;
import ludum.dare.trait.PositionTrait;
import ludum.dare.trait.UpdatableTrait;
import ludum.dare.world.DebugDrawable;
import ludum.dare.world.Light;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/6/15.
 */
public class DebugSprites extends Level{
    @Override
    public String name(){
        return this.getClass().getSimpleName();
    }

    @Override
    public List<GameObject> loadLevel(){
        List<GameObject> objs = new ArrayList<>();

        float width = 42;
        float height = 22;

        float left = -width / 2f;
        float top = height / 2f;
        float colW = 6;
        float rowH = 6;

        int maxCols = (int)(width / colW);
        int curRow = 0;
        int curCol = 0;

        String[] names = new String[]{
                "bot/die/botDie",
                "bot/hit/botHit",
                "bot/lightPain/botPain",
                "bot/stand/botStand",
                "bot/walk/botWalk",

                "bum/backpain/bumBackPain",
                "bum/cross/bumCross",
                "bum/death/bumDeath",
                "bum/jab/bumJab",
                "bum/jump/bumJump",
                "bum/kick/bumKick",
                "bum/land/bumLand",
                "bum/pain/bumPain",
                "bum/stand/bumStand",
                "bum/walk/bumWalk",

                "collectables/40brown",
                "collectables/40green",
                "collectables/chicken",

                "environment/background",
                "environment/background_one",
                "environment/background_three",
                "environment/background_two",

                "heavybot/slam/heavySlam",
                "heavybot/stand/heavyStand",
                "heavybot/walk/heavyWalk",

                "spark/shoot/sparkShoot",
                "spark/spark/spark",
                "spark/stand/sparkStand",
                "spark/walk/sparkWalk"
        };
        for (int i = 0; i < names.length; i++){
            objs.add(new DebugDrawable(left + (curCol * colW), top - (curRow * rowH), 6, 6, names[i]));

            curCol++;
            if (curCol > maxCols){
                curCol = 0;
                curRow++;
            }
        }

//        BlankObject lightPather = new BlankObject();
//        lightPather.traits.add(new PositionTrait(lightPather, 0, 0, 0));
//        lightPather.traits.add(new LightTrait(lightPather));
//        PathFollowerTrait pft = new PathFollowerTrait(lightPather, PathFollowerTrait.PathFollowStyle.LOOP, 0,
//                new Vector2(-20, 10.5f),
//                new Vector2(20, 10.5f),
//                new Vector2(20, -10.5f),
//                new Vector2(-20, -10.5f));
//        pft.travelTimeInMilliseconds = 3000;
//        lightPather.traits.add(pft);
//        lightPather.initializeTraits();
//        objs.add(lightPather);

        final Light mouseLight = new Light(0, 0);
        mouseLight.addAndInitializeTrait(new UpdatableTrait(mouseLight, new Runnable() {
            @Override
            public void run() {
                Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
                mousePos.x = (mousePos.x / Gdx.graphics.getWidth()) * 48 - 24;
                mousePos.y = (mousePos.y / Gdx.graphics.getHeight()) * 27 - 13.5f;
                PositionTrait pos = mouseLight.getTrait(PositionTrait.class);
                pos.x = mousePos.x;
                pos.y = mousePos.y;
            }
        }));
        objs.add(mouseLight);

        return objs;
    }
}
