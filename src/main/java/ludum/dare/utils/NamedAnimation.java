package ludum.dare.utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.winger.struct.Tups;

/**
 * Created by Admin on 8/21/2015.
 */
public class NamedAnimation extends Animation{
    private Animation animation;
    private Animation normalAnimation;
    private static float scaleFactor = .5f;
    private String name;
    /**
     * Holds the last frame we last returned
     */
    private int lastCalledFrame;

    public NamedAnimation(String name, float frameDuration, Array<? extends TextureRegion> keyFrames, Array<? extends TextureRegion> normalKeyFrames) {
        this(name, frameDuration, keyFrames, normalKeyFrames, new Vector2(0, 0), new Vector2(1, 1));
    }

    public NamedAnimation(String name, float frameDuration, Array<? extends TextureRegion> keyFrames, Array<? extends TextureRegion> normalKeyFrames, Vector2 offset, Vector2 size) {
        this.animation = new Animation(frameDuration, keyFrames);
        this.normalAnimation = new Animation(frameDuration, normalKeyFrames);
        if (normalAnimation.getKeyFrames().length == 0){
            this.normalAnimation = null;
        }
        this.name = name;
    }

    public Tups.Tup2<TextureRegion, TextureRegion> getKeyFrame(float stateTime) {
        lastCalledFrame = animation.getKeyFrameIndex(stateTime);
        return Tups.tup2(animation.getKeyFrame(stateTime), (normalAnimation != null ? normalAnimation.getKeyFrame(stateTime) : null));
    }

    public Tups.Tup2<TextureRegion, TextureRegion> getKeyFrame(float stateTime, boolean looping) {
        animation.setPlayMode(looping ? Animation.PlayMode.LOOP : Animation.PlayMode.NORMAL);
        if (normalAnimation != null) {
            normalAnimation.setPlayMode(looping ? Animation.PlayMode.LOOP : Animation.PlayMode.NORMAL);
        }
        lastCalledFrame = animation.getKeyFrameIndex(stateTime);
        return Tups.tup2(animation.getKeyFrame(stateTime, looping), (normalAnimation != null ? normalAnimation.getKeyFrame(stateTime, looping) : null));
    }

    public int getNumberOfFrames(){
        return animation.getKeyFrames().length;
    }

    public String getName() {
        return name;
    }

    public int getLastCalledFrame() {
        return lastCalledFrame;
    }
}
