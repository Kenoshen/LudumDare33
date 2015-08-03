package ludum.dare.trait;

/**
 * Created by mwingfield on 8/2/15.
 */
public class ControlTrait extends Trait {
    private static Class[] REQUIRES = new Class[]{ PhysicalTrait.class };

    public ControlTrait(GameObject obj) {
        super(obj);
    }

    @Override
    public Class[] requires() {
        return REQUIRES;
    }
}
