package ludum.dare.level;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import ludum.dare.collision.AnimationBundle;
import ludum.dare.trait.GameObject;
import ludum.dare.utils.AtlasManager;
import ludum.dare.world.Boundary;
import ludum.dare.world.EnemyBasic;
import ludum.dare.world.Player;
import ludum.dare.world.SquareProp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mwingfield on 8/6/15.
 */
public class TestLevel extends Level{
    @Override
    public String name(){
        return this.getClass().getSimpleName();
    }

    @Override
    public List<GameObject> loadLevel(){
        List<GameObject> objs = new ArrayList<>();

        objs.add(new Boundary(new Vector2(-20, 1), new Vector2(20, 1)));
        Sprite enemySprite = new Sprite(AtlasManager.instance.findRegion("testEnemySprite"));
        enemySprite.setSize(2,4);
        objs.add(new EnemyBasic(9, 5, 0, 2, 4));

        objs.add(new Player(0, 5, 0, 6, 6, CMouse.instance, CKeyboard.instance, null));
        TextureRegion tex = AtlasManager.instance.findRegion("white");
        Sprite sprite = new Sprite(tex);
        sprite.setSize(1, 1);
        objs.add(new SquareProp(2, 7, 0, 1, 1, sprite));

        return objs;
    }
}
