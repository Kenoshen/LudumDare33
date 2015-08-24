package ludum.dare.world;

import com.badlogic.gdx.math.Vector2;
import ludum.dare.trait.DrawableTrait;
import ludum.dare.trait.GameObject;
import ludum.dare.trait.PositionTrait;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.Sprite;

/**
 * Created by Michael on 8/23/2015.
 */
public class DebugDrawable extends GameObject {
    private PositionTrait pos;

    public DebugDrawable(float x, float y, float w, float h, String imgName){
        pos = new PositionTrait(this, x, y, 0);
        traits.add(pos);
        Sprite s = new Sprite(AtlasManager.instance.findRegion(imgName));
        s.setNormalRegion(AtlasManager.instance.findRegion(imgName + "_n"));
        s.setSize(w, h);
        DrawableTrait drawable = new DrawableTrait(this, s);
        traits.add(drawable);

        initializeTraits();
    }
}
