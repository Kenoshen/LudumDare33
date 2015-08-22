package ludum.dare.collision;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Admin on 8/21/2015.
 *
 * All shapes listed have coordinates relative to their OFFSET from the origin of the body they apply to.
 */
public class CollisionGroup {
    public Circle[] circles;
    public Rectangle[] boxes;
}
