package ludum.dare.trait;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.winger.struct.Tups;
import ludum.dare.utils.AnimationCallback;
import ludum.dare.utils.NamedAnimation;

import java.util.*;

/**
 * Created by mwingfield on 8/10/15.
 */
public class AnimatorTrait extends Trait {
    private static Class[] REQUIRES = new Class[]{ DrawableTrait.class };

    private Map<String, NamedAnimation> states = new HashMap<>();
    private String state = null;
    private DrawableTrait drawer;
    private float curTimer = 0;
    private boolean loop = true;
    public boolean flipped = false;

    private List<AnimationCallback> callbackList = new ArrayList<AnimationCallback>();

    public AnimatorTrait(GameObject obj, Map<String, NamedAnimation> states){
        super(obj);
        this.states.putAll(states);
        for(Map.Entry<String, NamedAnimation> entry : states.entrySet()){
            if (entry.getValue() == null || entry.getValue().getNumberOfFrames() == 0){
                throw new RuntimeException("Named Animation(" + entry.getKey() + ") cannot be null or have 0 frames in an AnimatorTrait");
            }
        }
        // This will probably leave. But for now just default to the first animation given
        Set<String> givenKeys = states.keySet();
        if (givenKeys.size() > 0) {
            setState(givenKeys.iterator().next());
        }
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

    public NamedAnimation getCurrentAnimation(){
        if (state != null && states.containsKey(state)){
            return states.get(state);
        } else {
            return null;
        }
    }

    public boolean setState(String state){
        return setState(state, true);
    }

    public boolean setState(String state, boolean loop){
        return setState(state, loop, false);
    }

    public boolean setState(String state, boolean loop, boolean keepTime) {
        if (state != null && states.containsKey(state)){
            this.state = state;
            this.loop = loop;
            if (!keepTime) {
                curTimer = 0;
            }
            for(AnimationCallback callback : callbackList) {
                callback.animationStarted(state);
            }
            return true;
        } else {
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
        NamedAnimation a = getCurrentAnimation();
        if (a != null){
            curTimer += delta;
            Tups.Tup2<TextureRegion, TextureRegion> texs = a.getKeyFrame(curTimer, loop);
            drawer.sprite.setRegion(texs.i1());
            drawer.sprite.setNormalRegion(texs.i2());
            drawer.sprite.flip(flipped, false);
            drawer.sprite.setSize(a.getSize().x, a.getSize().y);
            drawer.offset = a.getOffset(flipped);

            if (!loop && a.isAnimationFinished(curTimer)) {
                for(AnimationCallback callback : callbackList) {
                    callback.animationEnded(a.getName());
                }
            }
        }
    }

    public void registerAnimationCallback(AnimationCallback callback) {
        callbackList.add(callback);
    }
}
