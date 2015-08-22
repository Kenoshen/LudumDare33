package ludum.dare.level;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import ludum.dare.hitbox.AnimationBundle;
import ludum.dare.hitbox.HitboxGroup;
import ludum.dare.hitbox.HitboxSequence;
import ludum.dare.trait.GameObject;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.NamedAnimation;
import ludum.dare.world.Boundary;
import ludum.dare.world.EnemyBasic;
import ludum.dare.world.Player;
import ludum.dare.world.SquareProp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mwingfield on 8/6/15.
 */
public class TestLevel extends Level{
    @Override
    public String name(){
        return this.getClass().getSimpleName();
    }

    @Override
    public List<GameObject> loadLevel(){
        List<GameObject> objs = new ArrayList<>();

        objs.add(new Boundary(new Vector2(-20, 1), new Vector2(20, 1)));
        // TODO: try out the animation trait with the player object
        //objs.add(new Player(0, 5, 0, 2, 4, new Sprite(AtlasManager.instance.findRegion("cross")), new HashMap<String, Animation>(), CMouse.instance, CKeyboard.instance, null));
        // Adding EnemyBasic to objs
        objs.add(new EnemyBasic(9, 5, 0, 2, 4, new Sprite(AtlasManager.instance.findRegion("testEnemySprite")), new HashMap<String, Animation>()));
        AnimationBundle bundle = new AnimationBundle();

        TextureAtlas.AtlasRegion frame = AtlasManager.instance.findRegion("cross");
        NamedAnimation animation = new NamedAnimation("basic", 0.03f, new TextureRegion[] {frame, frame, frame, frame, frame});
        bundle.addNamedAnimation(animation);

        HitboxSequence sequence = new HitboxSequence();
        sequence.title = "basic";

        HitboxGroup group1 = new HitboxGroup();
        group1.circles = new Circle[] {new Circle(1,1,1)};

        HitboxGroup group2 = new HitboxGroup();
        group2.circles = new Circle[] {new Circle(1,1.5f,1)};

        HitboxGroup group3 = new HitboxGroup();
        group3.circles = new Circle[] {new Circle(1,2,1)};

        sequence.frames = new HitboxGroup[5];
        sequence.frames[2] = group1;
        sequence.frames[3] = group2;
        sequence.frames[4] = group3;
        bundle.addHitboxSequence(sequence);


        objs.add(new Player(0, 5, 0, 2, 4, bundle, CMouse.instance, CKeyboard.instance, null));

        TextureRegion tex = AtlasManager.instance.findRegion("white");
        objs.add(new SquareProp(2, 7, 0, 1, 1,  new Sprite(tex)));
        objs.add(new SquareProp(2, 7, 0, 1, 1, new Sprite(tex)));
        objs.add(new SquareProp(2, 7, 0, 1, 1, new Sprite(tex)));

        return objs;
    }
}
