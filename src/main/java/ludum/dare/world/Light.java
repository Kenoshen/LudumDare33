package ludum.dare.world;

import ludum.dare.trait.DrawableTrait;
import ludum.dare.trait.GameObject;
import ludum.dare.trait.LightTrait;
import ludum.dare.trait.PositionTrait;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.Sprite;

/**
 * Created by Michael on 8/23/2015.
 */
public class Light extends GameObject {
    public LightTrait light;

    public Light(float x, float y){
        traits.add(new PositionTrait(this, x, y, 0));
        light = new LightTrait(this);
        traits.add(light);

        initializeTraits();
    }
}
