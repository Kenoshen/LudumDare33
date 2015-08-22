package ludum.dare.trait;

/**
 * Created by mwingfield on 8/2/15.
 */
public abstract class UpdatableTrait extends Trait {
    public UpdatableTrait(GameObject obj) {
        super(obj);
    }

    public abstract void update(float delta);

    @Override
    public Class[] requires(){
        return new Class[0];
    }
}
//somewhere