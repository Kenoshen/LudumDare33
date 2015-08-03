package ludum.dare.set;

import com.badlogic.gdx.math.Vector2;
import com.winger.draw.texture.CSprite;
import com.winger.input.raw.CGamePad;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.physics.CBody;
import com.winger.physics.body.PlayerBody;
import ludum.dare.trait.*;

/**
 * Created by mwingfield on 8/3/15.
 */
public class Actor extends GameObject {
    public Actor(float x, float y, float z, float width, float height, CSprite sprite, CMouse mouse, CKeyboard keyboard, CGamePad gamepad){
        traits.add(new PositionTrait(this, x, y, z));
        traits.add(new SizeTrait(this, width, height));
        traits.add(new DrawableTrait(this, sprite));
        traits.add(new ControlTrait(this, mouse, keyboard, gamepad));
        CBody body = new PlayerBody(width, height).init(new Vector2(x, y));
        traits.add(new PhysicalTrait(this, body));

        traits.add(new DebugTrait(this));

        initializeTraits();

        body.setFriction(1);
    }
}
