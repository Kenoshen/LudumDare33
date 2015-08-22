package ludum.dare.trait;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import ludum.dare.collision.CollisionGroup;

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

    public void checkCollisions(List<GameObject> listGameObjects){

        CollisionGroup myHurtBoxes = ((TimedCollisionTrait) self.getTraits(TimedCollisionTrait.class).get(0)).getCurrentHurtboxes();

        for(GameObject obj : listGameObjects){

            if (obj != this.self) {

                List<Trait> traits = obj.getTraits(TimedCollisionTrait.class);
                if (traits.get(0) != null) {
                    CollisionGroup hurtBoxes = ((TimedCollisionTrait) traits.get(0)).getCurrentHurtboxes();

                    for(Rectangle theirRec : hurtBoxes.boxes){
                        for(Rectangle myRec : myHurtBoxes.boxes){
                            if(myRec.overlaps(theirRec)){
                                System.out.println("Rectangle Collision");
                            }
                        }
                    }

                    for(Circle theirCircle : hurtBoxes.circles){
                        for(Circle myCircle : myHurtBoxes.circles){
                            if(myCircle.overlaps(theirCircle)){
                                System.out.println("Circle Collision");
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Class[] requires(){
        return new Class[0];
    }
}
