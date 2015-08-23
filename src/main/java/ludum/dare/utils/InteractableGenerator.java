package ludum.dare.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import ludum.dare.collision.AnimationBundle;
import ludum.dare.collision.CollisionGroup;
import ludum.dare.collision.CollisionSequence;
import ludum.dare.trait.CollectableTrait;
import ludum.dare.trait.GameObject;
import ludum.dare.world.CircleProp;
import ludum.dare.world.SquareProp;

/**
 * Created by Admin on 8/22/2015.
 */
public class InteractableGenerator {

    public static GameObject GenerateRectangle(float posX, float poxY, float width, float height){

        TextureRegion tex = AtlasManager.instance.findRegion("white");

        AnimationBundle animationBundle = new AnimationBundle();
        Array<TextureRegion> textures = new Array<>();
        textures.add(tex);

        animationBundle.addNamedAnimation(new NamedAnimation("NOANIMATION", 1, textures,textures));

        CollisionSequence collisionSequence = new CollisionSequence();
        collisionSequence.name = "NOANIMATION";
        collisionSequence.frames = new CollisionGroup[1];

        // Generating the hurtbox
        CollisionGroup collisionGroup = new CollisionGroup();
        collisionGroup.boxes = new Rectangle[]{new Rectangle(-(width/2), -(height/2), width, height)};

        collisionSequence.frames[0] = collisionGroup;

        animationBundle.addHurtboxSequence(collisionSequence);


        Sprite s = new Sprite(tex);
        s.setColor(Color.BLACK);
        GameObject o = new SquareProp(posX, poxY, 0, width, height, animationBundle);

        o.addAndInitializeTrait(new CollectableTrait(o, new Runnable() {
            @Override
            public void run() {
                System.out.println("Collision!");
            }
        }));

        return o;
    }

    public static GameObject GenerateCircle(float posX, float poxY, float radius){

        TextureRegion tex = AtlasManager.instance.findRegion("white");

        AnimationBundle animationBundle = new AnimationBundle();
        Array<TextureRegion> textures = new Array<>();
        textures.add(tex);

        animationBundle.addNamedAnimation(new NamedAnimation("NOANIMATION", 1, textures,textures));

        CollisionSequence collisionSequence = new CollisionSequence();
        collisionSequence.name = "NOANIMATION";
        collisionSequence.frames = new CollisionGroup[1];

        // Generating the hurtbox
        CollisionGroup collisionGroup = new CollisionGroup();
        collisionGroup.circles = new Circle[]{new Circle(0,0,radius)};

        collisionSequence.frames[0] = collisionGroup;

        animationBundle.addHurtboxSequence(collisionSequence);


        Sprite s = new Sprite(tex);
        s.setColor(Color.BLACK);
        GameObject o = new CircleProp(posX, poxY, 0, radius, animationBundle);

        o.addAndInitializeTrait(new CollectableTrait(o, new Runnable() {
            @Override
            public void run() {
                System.out.println("Collision!");
            }
        }));

        return o;
    }


}
