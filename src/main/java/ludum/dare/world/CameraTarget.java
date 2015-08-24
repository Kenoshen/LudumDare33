package ludum.dare.world;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.winger.physics.CBody;
import com.winger.physics.body.CircleBody;
import ludum.dare.Conf;
import ludum.dare.collision.AnimationBundle;
import ludum.dare.trait.*;
import ludum.dare.utils.Sprite;

/**
 * Created by mwingfield on 8/3/15.
 */
public class CameraTarget extends GameObject {
    public CameraTarget(float x, float y){
        traits.add(new PositionTrait(this, x, y, 0));

        traits.add(new CameraFollowTrait(this));
        initializeTraits();
    }
}
