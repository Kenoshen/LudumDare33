package ludum.dare.trait;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import ludum.dare.utils.Sprite;

/**
 * Created by mwingfield on 8/2/15.
 */
public class DrawableTrait extends Trait {
    private static Class[] REQUIRES = new Class[]{ PositionTrait.class, SizeTrait.class };

    public Sprite sprite;
    private PositionTrait pos;
    private SizeTrait size;

    public DrawableTrait(GameObject obj, Sprite sprite) {
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

    public void draw(SpriteBatch spriteBatch){
        if (sprite != null) {
            // TODO: not sure why the size has to be double... that is really confusing me...
            sprite.setSize(size.width * 2, size.height * 2);
            sprite.setOrigin(sprite.getWidth() / 2f, sprite.getHeight() / 2f);
            sprite.setX(pos.x - sprite.getWidth() / 2f); // also, this stuff seems kind of hacky?
            sprite.setY(pos.y - sprite.getHeight() / 2f);

            sprite.setRotation(pos.rotation);

            if (spriteBatch.isDrawing()) {
                sprite.draw(spriteBatch);
            }
        }
    }
}
