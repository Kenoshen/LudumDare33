package ludum.dare.trait;

/**
 * Created by jake on 8/22/2015.
 */
public class HealthTrait extends Trait {
    public int health;
    public HealthTrait(GameObject obj, int h) {
        super(obj);
        health = h;
    }

    @Override
    public Class[] requires() {
        return new Class[0];
    }
    public void damage(int d){
        health -= d;
    }
    public void heal(int h){
        health += h;
    }
}
