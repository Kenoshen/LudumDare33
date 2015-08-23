package ludum.dare.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import ludum.dare.utils.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.winger.physics.CBody;
import com.winger.physics.body.BoxBody;
import ludum.dare.collision.AnimationBundle;
import ludum.dare.trait.*;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.NamedAnimation;

import java.util.Map;

/**
 * Created by jake on 8/22/2015.
 */
public class EnemyThrower extends GameObject{
    private PhysicalTrait physical;
    private Vector2 target;
    private AnimatorTrait animator;


    public EnemyThrower(float x, float y, float z, float width, float height){
        traits.add(new PositionTrait(this, x, y, z));
        traits.add(new DrawableTrait(this));
        traits.add(new LightTrait(this, Color.GREEN));

        AnimationBundle bundle = new AnimationBundle();

        final NamedAnimation animation = new NamedAnimation("stand", .1f,AtlasManager.instance.getAtlas("spark").findRegions("stand/sparkStand"), AtlasManager.instance.getAtlas("spark").findRegions("stand/sparkStand"), new Vector2(0, 0), new Vector2(width*1.5f, height));
        bundle.addNamedAnimation(animation);
        bundle.addNamedAnimation(new NamedAnimation("walk", .1f,AtlasManager.instance.getAtlas("spark").findRegions("walk/sparkWalk"), AtlasManager.instance.getAtlas("spark").findRegions("walk/sparkWalk"), new Vector2(0,0), new Vector2(width*1.5f, height)));
        bundle.addNamedAnimation(new NamedAnimation("shoot", .1f,AtlasManager.instance.getAtlas("spark").findRegions("shoot/sparkShoot"), AtlasManager.instance.getAtlas("spark").findRegions("shoot/sparkShoot"), new Vector2(0,1), new Vector2(width*1.5f,height*1.25f)));

        animator = new AnimatorTrait(this, bundle.getAnimations());
        traits.add(animator);

        traits.add(new AITrait(this));
        traits.add(new AIMovementRangedTrait(this, 6.0f, 25.0f));
//        traits.add(new AIMovementRetreatTrait(this, 5.0f, 4.0f, 25.0f));
        traits.add(new HealthTrait(this, 50));

        FixtureDef fd = new FixtureDef();
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.x = x;
        bd.position.y = y;
        bd.fixedRotation = true;
        CBody body = new BoxBody(width, height).init(fd, bd);
        physical = new PhysicalTrait(this, body);
        traits.add(physical);

        traits.add(new DebugTrait(this));

        initializeTraits();
    }
}
