package sandbox.ludum.dare.trait;

import com.winger.log.HTMLLogger;

/**
 * Created by mwingfield on 8/3/15.
 */
public class DebugTrait extends Trait {
    private static final HTMLLogger log = HTMLLogger.getLogger(DebugTrait.class);

    private PositionTrait pos;

    public DebugTrait(GameObject obj) {
        super(obj);
    }

    public void initialize(){
        super.initialize();

        pos = self.getTrait(PositionTrait.class);
    }

    @Override
    public Class[] requires() {
        return new Class[0];
    }

    public void debug(){
        //log.debug(pos.x + ", " + pos.y);
    }
}
