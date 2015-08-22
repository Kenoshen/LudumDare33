package ludum.dare.utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Admin on 8/21/2015.
 */
public class NamedAnimation extends Animation{
    private static float scaleFactor = .5f;
    private String name;
    private Vector2 offset;
    /**
     * Holds the last frame we last returned
     */
    private int lastCalledFrame;
    private Vector2 size = new Vector2();

    public NamedAnimation(String name, float frameDuration, Array<? extends TextureRegion> keyFrames) {
        this(name, frameDuration, keyFrames, new Vector2(0, 0), new Vector2(1, 1));
    }

    public NamedAnimation(String name, float frameDuration, Array<? extends TextureRegion> keyFrames, Vector2 offset, Vector2 size) {
        super(frameDuration, keyFrames);
        this.name = name;
        this.offset = offset;
        this.size = size;
    }

    @Override
    public TextureRegion getKeyFrame(float stateTime) {
        lastCalledFrame = super.getKeyFrameIndex(stateTime);
        return super.getKeyFrame(stateTime);
    }

    @Override
    public TextureRegion getKeyFrame(float stateTime, boolean looping) {
        setPlayMode(looping ? PlayMode.LOOP : PlayMode.NORMAL);
        lastCalledFrame = super.getKeyFrameIndex(stateTime);
        return super.getKeyFrame(stateTime, looping);
    }

    public String getName() {
        return name;
    }

    public int getLastCalledFrame() {
        return lastCalledFrame;
    }

    public Vector2 getOffset() {
        return offset;
    }

    public Vector2 getSize() {
        return size;
    }
}
