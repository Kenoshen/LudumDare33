package ludum.dare.trait;

/**
 * Created by jake on 8/22/2015.
 */
public class HealthTrait extends Trait {
    public int Health;
    public HealthTrait(GameObject obj) {
        super(obj);
    }

    @Override
    public Class[] requires() {
        return new Class[0];
    }
    public void setHealth(int h){
        Health = h;
    }
}
