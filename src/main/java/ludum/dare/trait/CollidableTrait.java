package ludum.dare.trait;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.winger.struct.Tups;
import ludum.dare.collision.CollisionGroup;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/2/15.
 */
public class CollidableTrait extends Trait {
    private Runnable runnable;
    public CollidableTrait(GameObject obj, Runnable runnable) {
        super(obj);
        this.runnable = runnable;
    }

    public void collect(){
        runnable.run();
    }

    public List<Tups.Tup2<GameObject, GameObject>> checkCollisions(List<GameObject> listGameObjects, List<Tups.Tup2<GameObject, GameObject>> listCollisions){


        List<Trait> myTraits = self.getTraits(TimedCollisionTrait.class);
        TimedCollisionTrait timedCollisionTrait = (TimedCollisionTrait) myTraits.get(0);
        CollisionGroup myHurtBoxes = timedCollisionTrait.getCurrentHurtboxes();

        Rectangle theirAdjustedRectanglePosition;
        Rectangle myAdjustedRectanglePosition;
        Circle theirAdjustedCirclePosition;
        Circle myAdjustedCirclePosition;
        PositionTrait myScreenPosition = self.getTrait(PositionTrait.class);

        for(GameObject obj : listGameObjects){

            if (obj != this.self) {

                List<Trait> theirTraits = obj.getTraits(TimedCollisionTrait.class);
                if (theirTraits.get(0) != null) {
                    CollisionGroup theirHurtBoxes = ((TimedCollisionTrait) theirTraits.get(0)).getCurrentHurtboxes();

                    if (theirHurtBoxes != null && myHurtBoxes != null) {
                        if (theirHurtBoxes.boxes != null && myHurtBoxes.boxes != null) {
                            for (Rectangle theirRec : theirHurtBoxes.boxes) {
                                for (Rectangle myRec : myHurtBoxes.boxes) {
                                    PositionTrait theirScreenPosition = obj.getTrait(PositionTrait.class);
                                    theirAdjustedRectanglePosition = new Rectangle(theirScreenPosition.x + theirRec.x, theirScreenPosition.y + theirRec.y, theirRec.getWidth(), theirRec.getHeight());
                                    myAdjustedRectanglePosition = new Rectangle(myScreenPosition.x + myRec.x, myScreenPosition.y  + myRec.y, myRec.getWidth(), myRec.getHeight());

                                    if (Intersector.overlaps(theirAdjustedRectanglePosition, myAdjustedRectanglePosition)) {
                                        Tups.Tup2<GameObject, GameObject> collision = Tups.tup2(obj, self);

                                        boolean collisionFound = false;

                                        for(Tups.Tup2<GameObject, GameObject> tuple : listCollisions){
                                            if((collision.i1() == tuple.i2() && collision.i2() == tuple.i1()) || (collision.i1() == tuple.i1() && collision.i2() == tuple.i2())){
                                                collisionFound = true;
                                            }
                                        }

                                        if(!collisionFound){
                                            System.out.println(System.currentTimeMillis() + " - Colision between " + obj.ID + " and " + self.ID);
                                            listCollisions.add(collision);
                                        }

                                    }
                                }
                            }
                        }
                    }

                    if (theirHurtBoxes != null && myHurtBoxes != null) {
                        if (theirHurtBoxes.boxes != null && myHurtBoxes.circles != null) {
                            for (Rectangle theirRec : theirHurtBoxes.boxes) {
                                if (myHurtBoxes.circles != null) {
                                    for (Circle myCirc : myHurtBoxes.circles) {
                                        PositionTrait theirScreenPosition = obj.getTrait(PositionTrait.class);
                                        theirAdjustedRectanglePosition = new Rectangle(theirScreenPosition.x + theirRec.x, theirScreenPosition.y + theirRec.y, theirRec.width, theirRec.height);
                                        myAdjustedCirclePosition = new Circle(myScreenPosition.x + myCirc.x, myScreenPosition.y + myCirc.y, myCirc.radius);

                                        if (Intersector.overlaps(myAdjustedCirclePosition, theirAdjustedRectanglePosition)) {
                                            Tups.Tup2<GameObject, GameObject> collision = Tups.tup2(obj, self);

                                            boolean collisionFound = false;

                                            for (Tups.Tup2<GameObject, GameObject> tuple : listCollisions) {
                                                if ((collision.i1() == tuple.i2() && collision.i2() == tuple.i1()) || (collision.i1() == tuple.i1() && collision.i2() == tuple.i2())) {
                                                    collisionFound = true;
                                                }
                                            }

                                            if (!collisionFound) {
                                                System.out.println(System.currentTimeMillis() + " - Colision between " + obj.ID + " and " + self.ID);
                                                listCollisions.add(collision);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if(theirHurtBoxes != null && myHurtBoxes != null) {
                        if (theirHurtBoxes.circles != null && myHurtBoxes.circles != null) {
                            for (Circle theirCirc : theirHurtBoxes.circles) {
                                for (Circle myCirc : myHurtBoxes.circles) {
                                    PositionTrait theirScreenPosition = obj.getTrait(PositionTrait.class);
                                    theirAdjustedCirclePosition = new Circle(theirScreenPosition.x + theirCirc.x, theirScreenPosition.y + theirCirc.y, theirCirc.radius);
                                    myAdjustedCirclePosition = new Circle(myScreenPosition.x + myCirc.x, myScreenPosition.y  + myCirc.y, myCirc.radius);

                                    if (Intersector.overlaps(theirAdjustedCirclePosition, myAdjustedCirclePosition)) {
                                        Tups.Tup2<GameObject, GameObject> collision = Tups.tup2(obj, self);

                                        boolean collisionFound = false;

                                        for(Tups.Tup2<GameObject, GameObject> tuple : listCollisions){
                                            if((collision.i1() == tuple.i2() && collision.i2() == tuple.i1()) || (collision.i1() == tuple.i1() && collision.i2() == tuple.i2())){
                                                collisionFound = true;
                                            }
                                        }

                                        if(!collisionFound){
                                            System.out.println(System.currentTimeMillis() + " - Colision between " + obj.ID + " and " + self.ID);
                                            listCollisions.add(collision);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if(theirHurtBoxes != null && myHurtBoxes != null) {
                        if (theirHurtBoxes.circles != null && myHurtBoxes.boxes != null) {
                            for (Circle theirCirc : theirHurtBoxes.circles) {
                                if (myHurtBoxes.boxes != null) {
                                    for (Rectangle myRec : myHurtBoxes.boxes) {
                                        PositionTrait theirScreenPosition = obj.getTrait(PositionTrait.class);
                                        theirAdjustedCirclePosition = new Circle(theirScreenPosition.x + theirCirc.x, theirScreenPosition.y + theirCirc.y, theirCirc.radius);
                                        myAdjustedRectanglePosition = new Rectangle(myScreenPosition.x + myRec.x, myScreenPosition.y + myRec.y, myRec.getWidth(), myRec.getHeight());

                                        if (Intersector.overlaps(theirAdjustedCirclePosition, myAdjustedRectanglePosition)) {
                                            Tups.Tup2<GameObject, GameObject> collision = Tups.tup2(obj, self);

                                            boolean collisionFound = false;

                                            for (Tups.Tup2<GameObject, GameObject> tuple : listCollisions) {
                                                if ((collision.i1() == tuple.i2() && collision.i2() == tuple.i1()) || (collision.i1() == tuple.i1() && collision.i2() == tuple.i2())) {
                                                    collisionFound = true;
                                                }
                                            }

                                            if (!collisionFound) {
                                                System.out.println(System.currentTimeMillis() + " - Colision between " + obj.ID + " and " + self.ID);
                                                listCollisions.add(collision);
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return listCollisions;
    }

    @Override
    public Class[] requires(){
        return new Class[0];
    }
}
