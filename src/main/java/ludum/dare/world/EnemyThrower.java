package ludum.dare.world;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.winger.physics.CBody;
import com.winger.physics.body.BoxBody;
import ludum.dare.collision.AnimationBundle;
import ludum.dare.collision.CollisionGroup;
import ludum.dare.collision.CollisionSequence;
import ludum.dare.trait.*;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.NamedAnimation;

/**
 * Created by jake on 8/22/2015.
 */
public class EnemyThrower extends GameObject{
    private PhysicalTrait physical;
    private Vector2 target;
    private AnimatorTrait animator;


    public EnemyThrower(float x, float y, float z){
        float width = 12;
        float height = 12;
        traits.add(new PositionTrait(this, x, y, z));
        traits.add(new DrawableTrait(this));

        traits.add(new HealthTrait(this, 40, null));

        AnimationBundle bundle = new AnimationBundle();

        final NamedAnimation animation = new NamedAnimation("stand", .1f,AtlasManager.instance.findRegions("spark/stand/sparkStand"), AtlasManager.instance.findRegions("spark/stand/sparkStand"), new Vector2(0, 0), new Vector2(width*1.5f, height));
        bundle.addNamedAnimation(animation);
        CollisionSequence standSequence = new CollisionSequence();
        standSequence.name = "stand";

        CollisionGroup standGroup = new CollisionGroup();
        standGroup.boxes = new Rectangle[] {new Rectangle(-2,-5.5f,4,10)};

        standSequence.frames = new CollisionGroup[8];
        for(int i = 0; i < standSequence.frames.length; i++){
            standSequence.frames[i] = standGroup;
        }
        bundle.addHurtboxSequence(standSequence);

        bundle.addNamedAnimation(new NamedAnimation("walk", .1f,AtlasManager.instance.findRegions("spark/walk/sparkWalk"),
                AtlasManager.instance.findRegions("spark/walk/sparkWalk_n"), new Vector2(0,-.5f), new Vector2(width*1.5f, height)));
        CollisionSequence walkSequence = new CollisionSequence();
        walkSequence.name = "walk";

        CollisionGroup walkGroup = new CollisionGroup();
        walkGroup.boxes = new Rectangle[] {new Rectangle(-2,-5.5f,4,10)};

        walkSequence.frames = new CollisionGroup[8];
        for(int i = 0; i < walkSequence.frames.length; i++){
            walkSequence.frames[i] = walkGroup;
        }
        bundle.addHurtboxSequence(walkSequence);

        bundle.addNamedAnimation(new NamedAnimation("shoot", .1f, AtlasManager.instance.findRegions("spark/shoot/sparkShoot"),
                AtlasManager.instance.findRegions("spark/shoot/sparkShoot_n"), new Vector2(0, 1), new Vector2(width * 1.5f, height * 1.25f)));
        CollisionSequence shootSequence = new CollisionSequence();
        shootSequence.name = "shoot";

        CollisionGroup shootGroup = new CollisionGroup();
        shootGroup.boxes = new Rectangle[] {new Rectangle(-2,-5.5f,4,10)};

        shootSequence.frames = new CollisionGroup[10];
        for(int i = 0; i < shootSequence.frames.length; i++){
            shootSequence.frames[i] = shootGroup;
        }
        bundle.addHurtboxSequence(shootSequence);

        bundle.addNamedAnimation(new NamedAnimation("die", .1f, AtlasManager.instance.findRegions("bot/die/botDie"),
                AtlasManager.instance.findRegions("bot/die/botDie_n"), new Vector2(0, 0), new Vector2(width, height)));


        animator = new AnimatorTrait(this, bundle.getAnimations());
        traits.add(animator);
        traits.add(new TimedCollisionTrait(this, bundle));

        traits.add(new AITrait(this));
        traits.add(new AIMovementRangedTrait(this, 6.0f, 25.0f));

        FixtureDef fd = new FixtureDef();
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.x = x;
        bd.position.y = y;
        bd.fixedRotation = true;
        CBody body = new BoxBody(width/2, 1).init(fd, bd);
        physical = new PhysicalTrait(this, body);
        physical.setOffset(0, height/2 -.5f);
        traits.add(physical);

        traits.add(new DebugTrait(this));
    }
}
