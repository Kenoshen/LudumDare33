package ludum.dare.trait;

/**
 * Created by mwingfield on 8/2/15.
 */
public class CollectableTrait extends Trait {
    private Runnable runnable;
    public CollectableTrait(GameObject obj, Runnable runnable) {
        super(obj);
        this.runnable = runnable;
    }

    public void collect(){
        runnable.run();
    }

    @Override
    public Class[] requires(){
        return new Class[0];
    }
}
