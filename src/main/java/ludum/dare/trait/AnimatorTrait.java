package ludum.dare.trait;

import com.badlogic.gdx.graphics.g2d.Animation;
import ludum.dare.utils.NamedAnimation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mwingfield on 8/10/15.
 */
public class AnimatorTrait extends Trait {
    private static Class[] REQUIRES = new Class[]{ DrawableTrait.class };

    private Map<String, NamedAnimation> states = new HashMap<>();
    private String state = null;
    private DrawableTrait drawer;
    private float curTimer = 0;
    private boolean loop = false;

    public AnimatorTrait(GameObject obj, Map<String, NamedAnimation> states){
        super(obj);
        this.states.putAll(states);
    }

    @Override
    public void initialize(){
        super.initialize();
        drawer = self.getTrait(DrawableTrait.class);
    }

    @Override
    public Class[] requires() {
        return REQUIRES;
    }

    public Animation getCurrentAnimation(){
        if (state != null && states.containsKey(state)){
            return states.get(state);
        } else {
            return null;
        }
    }

    public boolean setState(String state){
        return setState(state, false);
    }

    public boolean setState(String state, boolean loop){
        if (state != null && states.containsKey(state)){
            this.state = state;
            this.loop = loop;
            curTimer = 0;
            return true;
        } else {
            // return false; // TODO: maybe use the true/false thing instead of an exception?
            throw new RuntimeException("State does not exist on animator: " + state + ".  Possible values: " + states.keySet());
        }
    }

    public boolean changeStateIfUnique(String state){
        return changeStateIfUnique(state, false);
    }

    public boolean changeStateIfUnique(String state, boolean loop){
        if (! this.state.equalsIgnoreCase(state)){
            return setState(state, loop);
        } else {
            return false;
        }
    }

    public void update(float delta){
        Animation a = getCurrentAnimation();
        if (a != null){
            curTimer += delta;
            drawer.sprite.setRegion(a.getKeyFrame(curTimer, loop));
        }
    }
}
