package ludum.dare.trait;

import com.winger.draw.texture.CSprite;
import com.winger.draw.texture.CSpriteBatch;
import com.winger.struct.Tups;

/**
 * Created by mwingfield on 8/2/15.
 */
public class DrawableTrait extends Trait{
    private static Class[] REQUIRES = new Class[]{ PositionTrait.class, SizeTrait.class };

    public CSprite sprite;
    private PositionTrait pos;
    private SizeTrait size;

    public DrawableTrait(GameObject obj, CSprite sprite) {
        super(obj);
        this.sprite = sprite;
    }

    @Override
    public void initialize(){
        super.initialize();
        pos = self.getTrait(PositionTrait.class);
        size = self.getTrait(SizeTrait.class);
    }

    @Override
    public Class[] requires() {
        return REQUIRES;
    }

    public void draw(CSpriteBatch spriteBatch){
        sprite.update();

        sprite.setX(pos.x);
        sprite.setY(pos.y);
        sprite.setWidth(size.width);
        sprite.setHeight(size.height);

        sprite.draw(spriteBatch);
    }
}
