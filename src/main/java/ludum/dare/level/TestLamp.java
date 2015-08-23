package ludum.dare.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.utils.RandomUtils;
import javafx.scene.effect.Light;
import ludum.dare.collision.AnimationBundle;
import ludum.dare.trait.*;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.NamedAnimation;
import ludum.dare.utils.Sprite;
import ludum.dare.world.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/6/15.
 */
public class TestLamp extends Level{
    @Override
    public String name(){
        return this.getClass().getSimpleName();
    }

    @Override
    public List<GameObject> loadLevel(){
        List<GameObject> objs = new ArrayList<>();



        GameObject a = new BlankObject();
        a.traits.add(new PositionTrait(a, 0, 0, 0));
        Sprite s = new Sprite(AtlasManager.instance.findRegion("testEnvironment"));
        s.setNormalRegion(AtlasManager.instance.findRegion("testEnvironment_n"));
        s.setSize(100, 50);
        a.traits.add(new DrawableTrait(a, s));
        a.traits.add(new LightTrait(a));
        a.initializeTraits();
        objs.add(a);



        final GameObject o = new Player(0, 0, 0, CMouse.instance, CKeyboard.instance, null);
        o.addAndInitializeTrait(new CameraFollowTrait(o));
        o.addAndInitializeTrait(new LightTrait(o, Color.WHITE, Color.BLACK, 1).setZ(0.03f));
        o.addAndInitializeTrait(new UpdatableTrait(o, new Runnable() {
            @Override
            public void run() {
                LightTrait light = o.getTrait(LightTrait.class);
            }
        }));

        objs.add(o);

        return objs;
    }
}
