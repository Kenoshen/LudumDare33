package ludum.dare.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.utils.RandomUtils;
import ludum.dare.collision.AnimationBundle;
import ludum.dare.collision.CollisionGroup;
import ludum.dare.collision.CollisionSequence;
import ludum.dare.trait.*;
import ludum.dare.utils.AtlasManager;
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
        o = new Player(0, 5, 0, 4, 4, CMouse.instance, CKeyboard.instance, null);
        o.addAndInitializeTrait(new CameraFollowTrait(o));
        objs.add(o);

        TextureRegion tex = AtlasManager.instance.findRegion("white");
//        for (int i = 0; i < 10; i++){
//            Sprite s = new Sprite(tex);
//            s.setColor(RandomUtils.randomColor());
//            objs.add(new SquareProp(RandomUtils.rand(), RandomUtils.rand(), 0, 1, 1, s));
//        }

        AnimationBundle animationBundle = new AnimationBundle();
        animationBundle.addNamedAnimation(new NamedAnimation("still", 1, tex));

        CollisionSequence collisionSequence = new CollisionSequence();
        collisionSequence.name = "still";
        collisionSequence.frames = new CollisionGroup[1];

        // Generating the hurtbox
        CollisionGroup collisionGroup = new CollisionGroup();
        collisionGroup.boxes = new Rectangle[]{new Rectangle(-5,-5,10,10)};

        collisionSequence.frames[0] = collisionGroup;

        animationBundle.addHurtboxSequence(collisionSequence);


        Sprite s = new Sprite(tex);
        s.setColor(Color.BLACK);
        o = new SquareProp(10, 0, 0, 5, 5, animationBundle);



        o.addAndInitializeTrait(new CollectableTrait(o, new Runnable() {
            @Override
            public void run() {
                System.out.println("Collision!");
            }
        }));
//        o.addAndInitializeTrait(new CollidableTrait(o, new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }));
        objs.add(o);

        s = new Sprite(tex);
        s.setColor(Color.YELLOW.cpy());
        o = new CircleProp(RandomUtils.rand(), RandomUtils.rand(), 0, 1, s);
        o.addAndInitializeTrait(new CameraFollowTrait(o));
        objs.add(o);

        s = new Sprite(tex);
        s.setColor(Color.YELLOW.cpy());
        o = new CircleProp(RandomUtils.rand(), RandomUtils.rand(), 0, 1, s);
        o.addAndInitializeTrait(new CameraFollowTrait(o));
        objs.add(o);

        return objs;
    }
}
