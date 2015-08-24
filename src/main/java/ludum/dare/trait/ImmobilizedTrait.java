package ludum.dare.trait;

/**
 * Created by jake on 8/23/2015.
 */
public class ImmobilizedTrait extends Trait {
    public boolean imob = false;
    public Type type;
    public ImmobilizedTrait(GameObject obj) {
        super(obj);
    }

    public enum Type {
        HIT,
        ZAP
    }

    @Override
    public Class[] requires() {
        return new Class[0];
    }
}
