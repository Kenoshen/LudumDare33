package ludum.dare.level;

import com.badlogic.gdx.graphics.Color;
import ludum.dare.utils.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.utils.RandomUtils;
import ludum.dare.trait.CameraFollowTrait;
import ludum.dare.trait.GameObject;
import ludum.dare.utils.AtlasManager;
import ludum.dare.world.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/6/15.
 */
public class TestLevel2 extends Level{
    @Override
    public String name(){
        return this.getClass().getSimpleName();
    }

    @Override
    public List<GameObject> loadLevel(){
        List<GameObject> objs = new ArrayList<>();
        float boundarySize = 30;

        GameObject o = new Boundary(new Vector2(-boundarySize * 2, -boundarySize), new Vector2(boundarySize * 2, -boundarySize), new Vector2(boundarySize * 2, boundarySize), new Vector2(-boundarySize * 2, boundarySize), new Vector2(-boundarySize * 2, -boundarySize));
        objs.add(o);
        o = new Player(0, 5, 0, CMouse.instance, CKeyboard.instance, null);
        o.addAndInitializeTrait(new CameraFollowTrait(o));
        objs.add(o);

        o = new EnemyBasic(10, 5, 0, 6, 6);
        o.addAndInitializeTrait(new CameraFollowTrait(o));
        objs.add(o);

        o = new EnemyBasic(10, 5, 0, 6, 6);
        o.addAndInitializeTrait(new CameraFollowTrait(o));
        objs.add(o);

        o = new EnemyBasic(10, 5, 0, 6, 6);
        o.addAndInitializeTrait(new CameraFollowTrait(o));
        objs.add(o);

        o = new EnemyThrower(10, 5, 0);
        o.addAndInitializeTrait(new CameraFollowTrait(o));
        objs.add(o);

        TextureRegion tex = AtlasManager.instance.findRegion("white");
//        for (int i = 0; i < 10; i++){
//            Sprite s = new Sprite(tex);
//            s.setColor(RandomUtils.randomColor());
//            objs.add(new SquareProp(RandomUtils.rand(), RandomUtils.rand(), 0, 1, 1, s));
//        }

//        Sprite s = new Sprite(tex);
//        s.setColor(Color.YELLOW.cpy());
//        o = new CircleProp(RandomUtils.rand(), RandomUtils.rand(), 0, 1, s);
//        o.addAndInitializeTrait(new CameraFollowTrait(o));
//        objs.add(o);
//
//        s = new Sprite(tex);
//        s.setColor(Color.YELLOW.cpy());
//        o = new CircleProp(RandomUtils.rand(), RandomUtils.rand(), 0, 1, s);
//        o.addAndInitializeTrait(new CameraFollowTrait(o));
//        objs.add(o);

        return objs;
    }
}
