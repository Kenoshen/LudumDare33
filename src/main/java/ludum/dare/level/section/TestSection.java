package ludum.dare.level.section;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import ludum.dare.level.Level;
import ludum.dare.trait.GameObject;
import ludum.dare.trait.LightTrait;
import ludum.dare.trait.PathFollowerTrait;
import ludum.dare.trait.PositionTrait;
import ludum.dare.utils.AtlasManager;
import ludum.dare.world.BlankObject;
import ludum.dare.world.Boundary;
import ludum.dare.world.EnemyBasic;
import ludum.dare.world.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/6/15.
 */
public class TestSection extends Level{
    @Override
    public String name(){
        return this.getClass().getSimpleName();
    }

    @Override
    public List<GameObject> loadLevel(){
        List<GameObject> objs = new ArrayList<>();

        objs.add(new Boundary(new Vector2(-20, 1), new Vector2(20, 1)));
        objs.add(new EnemyBasic(9, 5, 0, 12, 12));

        objs.add(new Player(0, 5, 0, CMouse.instance, CKeyboard.instance, null));
        TextureRegion tex = AtlasManager.instance.findRegion("white");
//        objs.add(new SquareProp(2, 7, 0, 1, 1,  new Sprite(tex)));
//        objs.add(new SquareProp(2, 7, 0, 1, 1, new Sprite(tex)));
//        objs.add(new SquareProp(2, 7, 0, 1, 1, new Sprite(tex)));

        BlankObject lightPather = new BlankObject();
        lightPather.traits.add(new PositionTrait(lightPather, 0, 0, 0));
        lightPather.traits.add(new LightTrait(lightPather));
        lightPather.traits.add(new PathFollowerTrait(lightPather, PathFollowerTrait.PathFollowStyle.LOOP, 0,
                new Vector2(-20, 0),
                new Vector2(0, 15),
                new Vector2(20, 0),
                new Vector2(0, -5)));
        lightPather.initializeTraits();
        objs.add(lightPather);

        return objs;
    }
}
