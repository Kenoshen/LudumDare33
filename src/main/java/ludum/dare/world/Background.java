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
public class Background extends GameObject {
    private PositionTrait pos;

    public Background(float x, String backgroundName){
        pos = new PositionTrait(this, x * 48, 13.5f, 0);
        traits.add(pos);
        Sprite s = new Sprite(AtlasManager.instance.findRegion(backgroundName));
        s.setNormalRegion(AtlasManager.instance.findRegion(backgroundName + "_n"));
        s.setSize(48, 27);
        DrawableTrait drawable = new DrawableTrait(this, s);
        traits.add(drawable);

        initializeTraits();
        drawable.offset = new Vector2(0, -13.5f);
    }

    public Background(float x, float yOffset, String backgroundName){
        this(x, backgroundName);
        pos.y += yOffset;
    }
}
