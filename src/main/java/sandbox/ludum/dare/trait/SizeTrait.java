package sandbox.ludum.dare.trait;

/**
 * Created by mwingfield on 8/2/15.
 */
public class SizeTrait extends Trait {
    public float width = 0;
    public float height = 0;

    public SizeTrait(GameObject obj) {
        super(obj);
    }

    public SizeTrait(GameObject obj, float width, float height) {
        super(obj);
        this.width = width;
        this.height = height;
    }

    @Override
    public Class[] requires() {
        return new Class[0];
    }
}
