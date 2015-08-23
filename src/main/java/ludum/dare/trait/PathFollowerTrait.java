package ludum.dare.trait;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.winger.math.NumberMath;
import com.winger.math.tween.Tween;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/2/15.
 */
public class PathFollowerTrait extends Trait {

    public enum PathFollowStyle{
        LOOP,
        REVERSE,
        ONCE
    }
    private static Class[] REQUIRES = new Class[]{ PositionTrait.class };

    public PositionTrait pos;
    public List<Vector2> path = new ArrayList<>();
    public PathFollowStyle style = PathFollowStyle.LOOP;
    public float travelTimeInMilliseconds = 1000;
    public float wait = 0;
    public float waitAtEach = 0;
    public boolean waiting = false;
    public boolean atEnd = false;

    private Vector2 target = null;
    private float currentTravelTimeInMilliseconds = 0;
    private int currentPathIndex = 0;
    private Vector2 start = null;

    public PathFollowerTrait(GameObject obj, PathFollowStyle style, float wait, Vector2... points){
        super(obj);
        this.style = style;
        this.wait = wait;
        if (points.length < 2){
            throw new RuntimeException("Can't a path of less than 2");
        }
        for (Vector2 point : points){
            path.add(point);
        }
    }

    public PathFollowerTrait(GameObject obj, Vector2... points){
        this(obj, PathFollowStyle.LOOP, 0, points);
    }

    @Override
    public void initialize(){
        pos = self.getTrait(PositionTrait.class);
    }

    @Override
    public Class[] requires() {
        return REQUIRES;
    }

    public void travelOnPath(float delta){
        if (target != null){
            if (currentTravelTimeInMilliseconds >= travelTimeInMilliseconds){
                pos.x = target.x;
                pos.y = target.y;
                target = null;
                waiting = true;
                currentTravelTimeInMilliseconds = 0;
            } else {
                float travelTimeRatio = currentTravelTimeInMilliseconds / travelTimeInMilliseconds;
                if (travelTimeRatio >= 1){
                    travelTimeRatio = 1;
                } else if (travelTimeRatio <= 0){
                    travelTimeRatio = 0;
                }
                pos.x = start.x + (target.x - start.x) * (travelTimeRatio);
                pos.y = start.y + (target.y - start.y) * (travelTimeRatio) ;

                currentTravelTimeInMilliseconds += delta * 1000;
            }
        } else if(!waiting) {
            currentPathIndex += 1;
            if (currentPathIndex >= path.size()){
                currentPathIndex = -1;
                atEnd = true;
                waiting = true;
                currentTravelTimeInMilliseconds = 0;
            } else {
                target = path.get(currentPathIndex);
                start = new Vector2(pos.x, pos.y);
                currentTravelTimeInMilliseconds = 0;
            }
        } else if (waiting && atEnd){
            if (currentTravelTimeInMilliseconds >= wait){
                waiting = false;
                atEnd = false;
            } else {
                currentTravelTimeInMilliseconds += delta * 1000;
            }
        } else if (waiting){
            if (currentTravelTimeInMilliseconds >= wait){
                waiting = false;
            } else {
                currentTravelTimeInMilliseconds += delta * 1000;
            }
        }
    }
}
