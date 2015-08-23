package ludum.dare.trait;

/**
 * Created by jake on 8/23/2015.
 */
public class ImmobilizedTrait extends Trait {
    public boolean imob = false;
    public ImmobilizedTrait(GameObject obj) {
        super(obj);
    }

    @Override
    public Class[] requires() {
        return new Class[0];
    }
}
