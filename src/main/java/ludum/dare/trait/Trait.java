package ludum.dare.trait;

import com.winger.log.HTMLLogger;

/**
 * Created by mwingfield on 8/2/15.
 */
public abstract class Trait {
    protected HTMLLogger log;
    public GameObject self;
    public Trait(GameObject obj){
        self = obj;
    }

    public void initialize(){
        log = HTMLLogger.getLogger(this.getClass());
        Class[] req = requires();
        if (req != null){
            for (Class c : req){
                if (self.getTrait(c) == null){
                    throw new RuntimeException("This trait(" + this.getClass().getSimpleName() + ") requires another trait(" + c.getSimpleName() + ") that doesn't exist");
                }
            }
        }
    }

    public abstract Class[] requires();
}
