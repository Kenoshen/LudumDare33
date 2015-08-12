package ludum.dare.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.winger.physics.CBody;
import com.winger.physics.body.CircleBody;
import ludum.dare.trait.*;

/**
 * Created by mwingfield on 8/3/15.
 */
public class CircleProp extends GameObject {
    public CircleProp(float x, float y, float z, float radius, Sprite sprite){
        traits.add(new PositionTrait(this, x, y, z));
        traits.add(new SizeTrait(this, radius, radius));
        traits.add(new DrawableTrait(this, sprite));
        CBody body = new CircleBody(radius).init(new Vector2(x, y));
        traits.add(new PhysicalTrait(this, body));

        initializeTraits();
    }
}
