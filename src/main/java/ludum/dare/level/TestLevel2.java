package ludum.dare.level;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.utils.RandomUtils;
import ludum.dare.trait.GameObject;
import ludum.dare.utils.AtlasManager;
import ludum.dare.world.Boundary;
import ludum.dare.world.Player;
import ludum.dare.world.SquareProp;

import java.util.ArrayList;
import java.util.HashMap;
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
        objs.add(new Player(0, 5, 0, 2, 4, new Sprite(AtlasManager.instance.findRegion("cross")), new HashMap<String, Animation>(), CMouse.instance, CKeyboard.instance, null));

        TextureRegion tex = AtlasManager.instance.findRegion("cross");
        for (int i = 0; i < 10; i++){
            Sprite s = new Sprite(tex);
            s.setColor(RandomUtils.randomColor());
            objs.add(new SquareProp(RandomUtils.rand(), RandomUtils.rand(), 0, 1, 1, s));
        }

        return objs;
    }
}
