package ludum.dare.trait;

/**
 * Created by Admin on 8/21/2015.
 */
public class TimedHitboxTrait extends Trait{
    private static Class[] REQUIRES = new Class[]{ AnimatorTrait.class };

    public TimedHitboxTrait(GameObject obj) {
        super(obj);
    }
}
