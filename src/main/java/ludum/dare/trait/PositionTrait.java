package ludum.dare.trait;

/**
 * Created by mwingfield on 8/2/15.
 */
public class PositionTrait extends Trait {

    public float x = 0;
    public float y = 0;
    public float z = 0;

    public PositionTrait(GameObject obj) {
        super(obj);
    }

    public PositionTrait(GameObject obj, float x, float y){
        super(obj);
        this.x = x;
        this.y = y;
    }

    public PositionTrait(GameObject obj, float x, float y, float z){
        super(obj);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public Class[] requires() {
        return new Class[0];
    }
}
