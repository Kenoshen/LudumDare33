package ludum.dare.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.winger.physics.CBody;
import com.winger.physics.body.BoxBody;
import ludum.dare.trait.*;

/**
 * Created by mwingfield on 8/3/15.
 */
public class SquareProp extends GameObject {
    public SquareProp(float x, float y, float z, float width, float height, Sprite sprite){
        traits.add(new PositionTrait(this, x, y, z));
        traits.add(new SizeTrait(this, width, height));
        traits.add(new DrawableTrait(this, sprite));
        CBody body = new BoxBody(width, height).init(new Vector2(x, y));
        traits.add(new PhysicalTrait(this, body));

        initializeTraits();
    }
}
