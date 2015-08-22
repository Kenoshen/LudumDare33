package ludum.dare.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import javafx.animation.Animation;
import ludum.dare.trait.*;

import java.util.Map;

/**
 * Created by jake on 8/21/2015.
 */
public class Enemy extends GameObject{
    private PhysicalTrait physical;
    private AnimatorTrait animator;

    public Enemy(float x, float y, float z, float width, float height, Sprite eSprite, Map<String, Animation> states){
        traits.add(new PositionTrait(this, x, y, z));
        traits.add(new SizeTrait(this, width, height));
        traits.add(new DrawableTrait(this, eSprite));
        //animator = new AnimatorTrait(this, states);
    }
    public void update(float delta){

    }
}

