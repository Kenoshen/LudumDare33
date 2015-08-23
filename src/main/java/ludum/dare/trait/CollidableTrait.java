package ludum.dare.trait;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.winger.struct.Tups;
import ludum.dare.collision.CollisionGroup;
import ludum.dare.utils.CollisionCallback;

import java.util.List;

/**
 * Created by mwingfield on 8/2/15.
 */
public class CollidableTrait extends Trait {

    private CollisionCallback callback;
    private TimedCollisionTrait collisions;
    private PositionTrait position;
    private long gracePeriod;

    public CollidableTrait(GameObject obj, CollisionCallback callback) {
        super(obj);
        this.callback = callback;
    }


    @Override
    public Class[] requires() {
        return new Class[]{TimedCollisionTrait.class, PositionTrait.class};
    }

    @Override
    public void initialize() {
        super.initialize();
        collisions = self.getTrait(TimedCollisionTrait.class);
        position = self.getTrait(PositionTrait.class);
    }

    public void checkCollisions(List<GameObject> listGameObjects, List<Tups.Tup2<GameObject, GameObject>> listCollisions) {
        CollisionGroup myHitBoxes = collisions.getCurrentHitboxes();
        for (GameObject obj : listGameObjects) {
            TimedCollisionTrait theirCollisions = obj.getTrait(TimedCollisionTrait.class);
            PositionTrait theirPos = obj.getTrait(PositionTrait.class);
            if (theirCollisions != null && theirPos != null) {
                if (tryCollide(myHitBoxes, new Vector2(position.x, position.y), theirCollisions.getCurrentHurtboxes(), new Vector2(theirPos.x, theirPos.y))) {
                    if(System.currentTimeMillis() - gracePeriod > 500){
                        gracePeriod = System.currentTimeMillis();
                        callback.collide(obj);
                    }
                }
            }
        }
    }

    private boolean tryCollide(CollisionGroup hitboxes, Vector2 hitOffset, CollisionGroup hurtboxes, Vector2 hurtOffset) {
        if (hitboxes == null || hurtboxes == null) {
            return false;
        } else {
            Rectangle adjHitRect;
            Rectangle adjHurtRect;

            Circle adjHitCirc;
            Circle adjHurtCirc;

            if (hitboxes.boxes != null) {
                for (Rectangle hitRect : hitboxes.boxes) {
                    adjHitRect = new Rectangle(hitRect.x + hitOffset.x, hitRect.y + hitOffset.y, hitRect.width, hitRect.height);
                    if (hurtboxes.boxes != null) {
                        for (Rectangle hurtRect : hurtboxes.boxes) {
                            adjHurtRect = new Rectangle(hurtRect.x + hurtOffset.x, hurtRect.y + hurtOffset.y, hurtRect.width, hurtRect.height);

                            if (Intersector.overlaps(adjHitRect, adjHurtRect)) {
                                return true;
                            }
                        }
                    }

                    if (hurtboxes.circles != null) {
                        for (Circle hurtCirc : hurtboxes.circles) {
                            adjHurtCirc = new Circle(hurtCirc.x + hurtOffset.x, hurtCirc.y + hurtOffset.y, hurtCirc.radius);
                            if (Intersector.overlaps(adjHurtCirc, adjHitRect)) {
                                return true;
                            }
                        }
                    }
                }
            }

            if (hitboxes.circles != null) {
                for (Circle hitCirc : hitboxes.circles) {
                    adjHitCirc = new Circle(hitCirc.x + hitOffset.x, hitCirc.y + hitOffset.y, hitCirc.radius);
                    if (hurtboxes.boxes != null) {
                        for (Rectangle hurtRect : hurtboxes.boxes) {
                            adjHurtRect = new Rectangle(hurtRect.x + hurtOffset.x, hurtRect.y + hurtOffset.y, hurtRect.width, hurtRect.height);

                            if (Intersector.overlaps(adjHitCirc, adjHurtRect)) {
                                return true;
                            }
                        }
                    }

                    if (hurtboxes.circles != null) {
                        for (Circle hurtCirc : hurtboxes.circles) {
                            adjHurtCirc = new Circle(hurtCirc.x + hurtOffset.x, hurtCirc.y + hurtOffset.y, hurtCirc.radius);
                            if (Intersector.overlaps(adjHitCirc, adjHurtCirc)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
