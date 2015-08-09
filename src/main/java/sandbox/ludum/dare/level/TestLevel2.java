package sandbox.ludum.dare.level;

import com.badlogic.gdx.math.Vector2;
import com.winger.draw.texture.CSprite;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.struct.CRectangle;
import com.winger.utils.RandomUtils;
import sandbox.ludum.dare.trait.GameObject;
import sandbox.ludum.dare.world.Actor;
import sandbox.ludum.dare.world.Boundary;
import sandbox.ludum.dare.world.SquareProp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/6/15.
 */
public class TestLevel2 extends Level{
    @Override
    public String name(){
        return this.getClass().getSimpleName();
    }

    @Override
    public List<GameObject> loadLevel(){
        List<GameObject> objs = new ArrayList<>();

        objs.add(new Boundary(new Vector2(-20, -20), new Vector2(20, -20), new Vector2(20, 20), new Vector2(-20, 20), new Vector2(-20, -20)));
        objs.add(new Actor(0, 5, 0, 2, 4, new CSprite("cross", CRectangle.empty(), false), CMouse.instance, CKeyboard.instance, null));

        for (int i = 0; i < 10; i++){
            objs.add(new SquareProp(RandomUtils.rand(), RandomUtils.rand(), 0, 1, 1, new CSprite("white", CRectangle.empty(), false).setColor(RandomUtils.randomColor())));
        }

        return objs;
    }
}
