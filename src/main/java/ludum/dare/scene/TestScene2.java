package ludum.dare.scene;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.winger.draw.texture.CSprite;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.struct.CRectangle;
import com.winger.utils.RandomUtils;
import ludum.dare.set.Actor;
import ludum.dare.set.Boundary;
import ludum.dare.set.SquareProp;
import ludum.dare.trait.GameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/3/15.
 */
public class TestScene2 extends Scene {

    @Override
    public String name(){
        return this.getClass().getSimpleName();
    }

    @Override
    public List<GameObject> loadScene(){
        List<GameObject> objs = new ArrayList<>();

        objs.add(new Boundary(new Vector2(-20, -20), new Vector2(20, -20), new Vector2(20, 20), new Vector2(-20, 20), new Vector2(-20, -20)));
        objs.add(new Actor(0, 5, 0, 2, 4, new CSprite("cross", CRectangle.empty(), false), CMouse.instance, CKeyboard.instance, null));

        for (int i = 0; i < 10; i++){
            objs.add(new SquareProp(RandomUtils.rand(), RandomUtils.rand(), 0, 1, 1, new CSprite("white", CRectangle.empty(), false).setColor(RandomUtils.randomColor())));
        }

        return objs;
    }
}
