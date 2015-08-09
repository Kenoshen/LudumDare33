package sandbox.ludum.dare.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.winger.physics.body.ChainBody;
import sandbox.ludum.dare.trait.GameObject;
import sandbox.ludum.dare.trait.PhysicalTrait;
import sandbox.ludum.dare.trait.PositionTrait;

/**
 * Created by mwingfield on 8/3/15.
 */
public class Boundary extends GameObject {
    public Boundary(Vector2... chain){
        traits.add(new PositionTrait(this, 0, 0));

        ChainBody body = new ChainBody(chain);
        BodyDef bD = new BodyDef();
        bD.type = BodyDef.BodyType.StaticBody;
        body.init(bD);
        traits.add(new PhysicalTrait(this, body));

        initializeTraits();
    }
}
