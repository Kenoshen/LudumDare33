package ludum.dare.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.utils.RandomUtils;
import ludum.dare.collision.AnimationBundle;
import ludum.dare.collision.CollisionGroup;
import ludum.dare.collision.CollisionSequence;
import ludum.dare.trait.*;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.InteractableGenerator;
import ludum.dare.utils.NamedAnimation;
import ludum.dare.world.Boundary;
import ludum.dare.world.CircleProp;
import ludum.dare.world.Player;
import ludum.dare.world.SquareProp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/6/15.
 */
public class TestLevel3 extends Level{
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

        o = InteractableGenerator.GenerateRectangle(10, 0, 10, 10);
        o.ID = "Square1";
        objs.add(o);

        o = InteractableGenerator.GenerateRectangle(-10, 0, 10, 10);
        o.ID = "Square2";
        objs.add(o);

        o = InteractableGenerator.GenerateCircle(0, 15, 5);
        o.ID = "Circle1";
        objs.add(o);

        return objs;
    }
}
