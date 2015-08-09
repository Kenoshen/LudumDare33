package ludum.dare.trait;

import com.badlogic.gdx.math.Vector2;
import com.winger.draw.texture.CSprite;
import com.winger.draw.texture.CSpriteBatch;

/**
 * Created by mwingfield on 8/2/15.
 */
public class DrawableTrait extends Trait {
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
        // TODO: figure out why the sprites are displaying at half the size they should be
        sprite.setWidth(size.width * 2); // this is a hack (the * 2), because of the above comment
        sprite.setHeight(size.height * 2);
        sprite.setOrigin(new Vector2(size.width, size.height)); // this is a hack, should be width / 2f, etc

        sprite.setRotation(pos.rotation);

        sprite.draw(spriteBatch);
    }
}
