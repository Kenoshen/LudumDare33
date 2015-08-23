package ludum.dare.world;

import ludum.dare.utils.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.winger.physics.CBody;
import com.winger.physics.body.BoxBody;
import ludum.dare.Conf;
import ludum.dare.trait.*;

/**
 * Created by mwingfield on 8/3/15.
 */
public class SquareProp extends GameObject {
    public SquareProp(float x, float y, float z, float width, float height, Sprite sprite){
        traits.add(new PositionTrait(this, x, y, z));
        traits.add(new SizeTrait(this, width, height));
        traits.add(new DrawableTrait(this, sprite));
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
        CBody body = new BoxBody(width, height).init(fD, bD);
        traits.add(new PhysicalTrait(this, body));

        initializeTraits();
    }
}
