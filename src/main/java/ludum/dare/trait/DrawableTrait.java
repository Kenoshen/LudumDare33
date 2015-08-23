package ludum.dare.trait;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import ludum.dare.utils.Sprite;

/**
 * Created by mwingfield on 8/2/15.
 */
public class DrawableTrait extends Trait {
    private static Class[] REQUIRES = new Class[]{ PositionTrait.class };

    public Sprite sprite;
    private PositionTrait pos;
    public Vector2 offset;

    public DrawableTrait(GameObject obj) {
        this(obj, null);
    }

    public DrawableTrait(GameObject obj, Sprite sprite) {
        super(obj);
        if (sprite == null) {
            sprite = new Sprite();
        }
        this.sprite = sprite;
    }

    @Override
    public void initialize(){
        super.initialize();
        pos = self.getTrait(PositionTrait.class);
        offset = new Vector2(0,0);
    }

    @Override
    public Class[] requires() {
        return REQUIRES;
    }

    public void draw(SpriteBatch spriteBatch){
        if (sprite != null) {
            // TODO: not sure why the size has to be double... that is really confusing me...
            sprite.setOrigin(sprite.getWidth() / 2f, sprite.getHeight() / 2f);
            sprite.setX(pos.x - sprite.getWidth() / 2f + offset.x); // also, this stuff seems kind of hacky?
            sprite.setY(pos.y - sprite.getHeight() / 2f + offset.y);

            sprite.setRotation(pos.rotation);

            if (spriteBatch.isDrawing()) {
                sprite.draw(spriteBatch);
            }
        }
    }
}
