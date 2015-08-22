package ludum.dare.trait;

/**
 * Created by mwingfield on 8/2/15.
 */
public class UpdatableTrait extends Trait {
    private Runnable runnable;
    public UpdatableTrait(GameObject obj, Runnable runnable) {
        super(obj);
        this.runnable = runnable;
    }

    public void update(){
        runnable.run();
    }

    @Override
    public Class[] requires(){
        return new Class[0];
    }
}
