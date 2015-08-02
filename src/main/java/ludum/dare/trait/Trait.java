package ludum.dare.trait;

/**
 * Created by mwingfield on 8/2/15.
 */
public abstract class Trait {
    public GameObject self;
    public Class[] requires = null;
    public Trait(GameObject obj){

    }

    public void initialize(){
        // TODO: check the requires array for traits on the obj
    }
}
