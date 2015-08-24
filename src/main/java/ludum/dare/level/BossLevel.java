package ludum.dare.level;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import ludum.dare.trait.GameObject;
import ludum.dare.trait.LightTrait;
import ludum.dare.trait.PathFollowerTrait;
import ludum.dare.trait.PositionTrait;
import ludum.dare.utils.AtlasManager;
import ludum.dare.world.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/6/15.
 */
public class BossLevel extends Level {

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public List<GameObject> loadLevel() {
        List<GameObject> objs = new ArrayList<>();

        ShockCan can1 = new ShockCan(12, 3, 0);
        ShockCan can2 = new ShockCan(20f, -6, 0);
        SpawnerDoor door = new SpawnerDoor(-8, 3, 0);
        objs.add(can1);
        objs.add(can2);
        objs.add(door);

        objs.add(new Boss(can1, can2, door));

//        objs.add(new EnemyThrower(15, -10, 0));
//        objs.add(new EnemyHeavy(15, -10, 0));
//        objs.add(new Fourty(-10,-10,0,3));
//        objs.add(new Chicken(-15,-10,0,6));

        objs.add(new Player(-8, -5, 0, CMouse.instance, CKeyboard.instance, null));
        TextureRegion tex = AtlasManager.instance.findRegion("white");
//        objs.add(new SquareProp(2, 7, 0, 1, 1,  new Sprite(tex)));
//        objs.add(new SquareProp(2, 7, 0, 1, 1, new Sprite(tex)));
//        objs.add(new SquareProp(2, 7, 0, 1, 1, new Sprite(tex)));

        BlankObject lightPather = new BlankObject();
        lightPather.traits.add(new PositionTrait(lightPather, 0, 0, 0));
        lightPather.traits.add(new LightTrait(lightPather));
        lightPather.traits.add(new PathFollowerTrait(lightPather, PathFollowerTrait.PathFollowStyle.LOOP, 0,
                new Vector2(-20, 0),
                new Vector2(0, 15),
                new Vector2(20, 0),
                new Vector2(0, -5)));
        lightPather.initializeTraits();
        objs.add(lightPather);

        return objs;
    }
}
