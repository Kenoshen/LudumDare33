package ludum.dare.trait;

import com.badlogic.gdx.math.Vector2;
import com.winger.camera.FollowOrthoCamera;

/**
 * Created by mwingfield on 8/2/15.
 */
public class CameraFollowTrait extends Trait {
    private static Class[] REQUIRES = new Class[]{ PositionTrait.class };

    private PositionTrait pos;

    public CameraFollowTrait(GameObject obj) {
        super(obj);
    }

    @Override
    public void initialize(){
        super.initialize();
        pos = self.getTrait(PositionTrait.class);
    }


    @Override
    public Class[] requires() {
        return REQUIRES;
    }


    public void updateCamera(FollowOrthoCamera cam){
        cam.addFollowPoint(new Vector2(pos.x, pos.y));
    }
}
