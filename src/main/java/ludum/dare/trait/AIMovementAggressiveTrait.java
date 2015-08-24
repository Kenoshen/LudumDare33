package ludum.dare.trait;

import ludum.dare.utils.AnimationCallback;

/**
 * Created by jake on 8/22/2015.
 */
public class AIMovementAggressiveTrait extends Trait implements AnimationCallback {

    private AnimatorTrait animator;

    public AIMovementAggressiveTrait(GameObject obj) {
        super(obj);
    }

    @Override
    public void initialize() {
        super.initialize();
        animator = self.getTrait(AnimatorTrait.class);
        animator.registerAnimationCallback(this);
    }

    @Override
    public Class[] requires() {
        return new Class[] {AnimatorTrait.class};
    }



    @Override
    public void animationStarted(String name) {

    }

    @Override
    public void animationEnded(String name) {
        if (name.equals("hit")) {
        }
    }
}
