package ludum.dare.trait;

/**
 * Created by mwingfield on 8/2/15.
 */
public abstract class Trait {
    public GameObject self;
    public Trait(GameObject obj){
        self = obj;
    }

    public void initialize(){
        Class[] req = requires();
        if (req != null){
            for (Class c : req){
                if (self.getTrait(c) == null){
                    throw new RuntimeException("This trait requires another trait(" + this.getClass().getSimpleName() + ") that doesn't exist (" + c.getSimpleName() + ")");
                }
            }
        }
    }

    public abstract Class[] requires();
}
