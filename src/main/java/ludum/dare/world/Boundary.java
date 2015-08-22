package ludum.dare.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.winger.physics.body.ChainBody;
import ludum.dare.Conf;
import ludum.dare.trait.PhysicalTrait;
import ludum.dare.trait.GameObject;
import ludum.dare.trait.PositionTrait;

/**
 * Created by mwingfield on 8/3/15.
 */
public class Boundary extends GameObject {
    public Boundary(Vector2... chain){
        traits.add(new PositionTrait(this, 0, 0));

        ChainBody body = new ChainBody(chain);

        BodyDef bD = new BodyDef();
        bD.type = BodyDef.BodyType.StaticBody;
        FixtureDef fD = new FixtureDef();
        //
        // this part sets the collision filter for the boundary object
        // all boundary objects collide with Actor objects
        fD.filter.categoryBits = Conf.instance.physicsBitFilterBoundary();
        fD.filter.maskBits = Conf.instance.physicsBitFilterActor();
        //
        body.init(fD, bD);
        traits.add(new PhysicalTrait(this, body));

        initializeTraits();
    }
}
