package ludum.dare.world;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.winger.physics.CBody;
import com.winger.physics.body.BoxBody;
import ludum.dare.trait.*;

import java.util.Map;

/**
 * Created by jake on 8/22/2015.
 */
public class EnemyThrower extends GameObject{
    private PhysicalTrait physical;

    public EnemyThrower(float x, float y, float z, float width, float height, Sprite eSprite){
        traits.add(new PositionTrait(this, x, y, z));
        traits.add(new SizeTrait(this, width, height));
        traits.add(new DrawableTrait(this, eSprite));
        traits.add(new AITrait(this));
        traits.add(new EnemyThrowerBehaviorTrait(this));
        traits.add(new HealthTrait(this));

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
