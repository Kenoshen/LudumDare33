package ludum.dare.trait;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
            // TODO: figure out why stuff isn't drawing correctly, should be something related to the camera and spritebatch
            sprite.setX(pos.x);
            sprite.setY(pos.y);
            sprite.setSize(size.width, size.height);
            sprite.setOrigin(size.width / 2f, size.height / 2f);

            sprite.setRotation(pos.rotation);

            if (spriteBatch.isDrawing()) {
                sprite.draw(spriteBatch);
            }
        }
    }
}
