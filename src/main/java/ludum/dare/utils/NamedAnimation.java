package ludum.dare.utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Admin on 8/21/2015.
 */
public class NamedAnimation extends Animation{
    private String name;
    /**
     * Holds the last frame we last returned
     */
    private int lastCalledFrame;

    public NamedAnimation(String name, float frameDuration, Array<? extends TextureRegion> keyFrames) {
        super(frameDuration, keyFrames);
        this.name = name;
    }

    public NamedAnimation(String name, float frameDuration, Array<? extends TextureRegion> keyFrames, PlayMode playMode) {
        super(frameDuration, keyFrames, playMode);
        this.name = name;
    }

    public NamedAnimation(String name, float frameDuration, TextureRegion... keyFrames) {
        super(frameDuration, keyFrames);
        this.name = name;
    }

    @Override
    public TextureRegion getKeyFrame(float stateTime) {
        lastCalledFrame = super.getKeyFrameIndex(stateTime);
        return super.getKeyFrame(stateTime);
    }

    @Override
    public TextureRegion getKeyFrame(float stateTime, boolean looping) {
        lastCalledFrame = super.getKeyFrameIndex(stateTime);
        return super.getKeyFrame(stateTime, looping);
    }

    public String getName() {
        return name;
    }

    public int getLastCalledFrame() {
        return lastCalledFrame;
    }
}
