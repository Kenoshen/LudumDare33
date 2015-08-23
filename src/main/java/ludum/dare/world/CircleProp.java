package ludum.dare.world;

import ludum.dare.utils.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.winger.physics.CBody;
import com.winger.physics.body.CircleBody;
import ludum.dare.Conf;
import ludum.dare.collision.AnimationBundle;
import ludum.dare.trait.*;

/**
 * Created by mwingfield on 8/3/15.
 */
public class CircleProp extends GameObject {
    public CircleProp(float x, float y, float z, float radius, AnimationBundle animationBundle){
        traits.add(new PositionTrait(this, x, y, z));
        traits.add(new SizeTrait(this, radius, radius));

        traits.add(new CollidableTrait(this, null));
        traits.add(new AnimatorTrait(this, animationBundle.getAnimations()));
        traits.add(new TimedCollisionTrait(this, animationBundle));
        traits.add(new DrawableTrait(this, new Sprite()));
        //
        // this part sets the collision filter for the boundary object
        // all boundary objects collide with Actor objects
        FixtureDef fD = new FixtureDef();
        fD.filter.categoryBits = Conf.instance.physicsBitFilterActor();
        fD.filter.maskBits = Conf.instance.physicsBitFilterBoundary();
        //
        BodyDef bD = new BodyDef();
        bD.type = BodyDef.BodyType.DynamicBody;
        bD.position.x = x;
        bD.position.y = y;
        CBody body = new CircleBody(radius).init(fD, bD);
        traits.add(new PhysicalTrait(this, body));

        initializeTraits();
    }
}
