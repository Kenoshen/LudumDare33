package ludum.dare.scene;

import com.badlogic.gdx.math.Vector2;
import com.winger.Winger;
import com.winger.draw.texture.CSprite;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.struct.CRectangle;
import ludum.dare.set.Actor;
import ludum.dare.set.Boundary;
import ludum.dare.set.SquareProp;
import ludum.dare.trait.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/3/15.
 */
public class TestScene extends Scene {

    @Override
    public List<GameObject> loadScene(){
        List<GameObject> objs = new ArrayList<>();

        objs.add(new Boundary(new Vector2(-20, 1), new Vector2(20, 1)));
        objs.add(new Actor(0, 5, 0, 2, 4, new CSprite("cross", CRectangle.empty(), false), CMouse.instance, CKeyboard.instance, null));
        objs.add(new SquareProp(2, 7, 0, 1, 1, new CSprite("white", CRectangle.empty(), false)));
        objs.add(new SquareProp(2, 7, 0, 1, 1, new CSprite("white", CRectangle.empty(), false)));
        objs.add(new SquareProp(2, 7, 0, 1, 1, new CSprite("white", CRectangle.empty(), false)));

        return objs;
    }
}
