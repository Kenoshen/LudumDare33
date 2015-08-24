package ludum.dare.world;

import ludum.dare.trait.DrawableTrait;
import ludum.dare.trait.GameObject;
import ludum.dare.trait.PositionTrait;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.Sprite;

/**
 * Created by Michael on 8/23/2015.
 */
public class Background extends GameObject {
    public Background(float x, String backgroundName){
        traits.add(new PositionTrait(this, x * 48, 0, 0));
        Sprite s = new Sprite(AtlasManager.instance.findRegion(backgroundName));
        s.setSize(48, 27);
        traits.add(new DrawableTrait(this, s));

        initializeTraits();
    }
}
