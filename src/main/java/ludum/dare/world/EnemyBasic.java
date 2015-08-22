package ludum.dare.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.winger.physics.CBody;
import com.winger.physics.body.BoxBody;
import com.winger.physics.body.PlayerBody;
import ludum.dare.trait.*;

import java.util.Map;

/**
 * Created by jake on 8/21/2015.
 */
public class EnemyBasic extends GameObject{
    private PhysicalTrait physical;
//    private AnimatorTrait animator;

    public EnemyBasic(float x, float y, float z, float width, float height, Sprite eSprite, Map<String, Animation> states){
        traits.add(new PositionTrait(this, x, y, z));
        //traits.add(new SizeTrait(this, width, height));
        traits.add(new DrawableTrait(this, eSprite));
        traits.add(new AITrait(this));
        traits.add(new EnemyBasicBehaviorTrait(this));
//        animator = new AnimatorTrait(this, states);

        CBody body = new BoxBody(width, height).init(new Vector2(x, y));
        physical = new PhysicalTrait(this, body);
        traits.add(physical);

        traits.add(new DebugTrait(this));

        initializeTraits();
        body.setFriction(1);
    }
}

