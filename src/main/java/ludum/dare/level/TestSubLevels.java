package ludum.dare.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import ludum.dare.screen.GameScreen;
import ludum.dare.trait.*;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.Sprite;
import ludum.dare.world.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/6/15.
 */
public class TestSubLevels extends Level{
    private float screenWidth = 48;
    private float screenHeight = 27;
    private float halfScreenWidth = 24;
    private float halfScreenHeight = 13.5f;

    private Player player;

    @Override
    public String name(){
        return this.getClass().getSimpleName();
    }

    private int index = -1;

    @Override
    public List<GameObject> loadLevel(){
        return nextSection();
    }

    public List<GameObject> nextSection(){
        index ++;
        if (index == 0){
            return section0();
        } else if (index == 1){
            return section1();
        }else {
            throw new RuntimeException("No more sections in this level");
        }
    }


    private List<GameObject> section0(){
        List<GameObject> objs = new ArrayList<>();

        final CameraTarget target = new CameraTarget(0, 0);
        objs.add(target);

        objs.add(newBottomBoundary(0));
        objs.add(path(-halfScreenWidth, -halfScreenHeight, -halfScreenWidth, -3, halfScreenWidth, -1));

        objs.add(new Background(0, 0.01f, "environment/background"));
        objs.add(new Background(0, "environment/background_one"));

        player = new Player(0, -5, 0, CMouse.instance, CKeyboard.instance, null);
        objs.add(player);

        final Boundary rightSideBoundary = path(halfScreenWidth, -1, halfScreenWidth, -halfScreenHeight);
        objs.add(rightSideBoundary);

        BlankObject lightPather = new BlankObject();
        lightPather.traits.add(new PositionTrait(lightPather, 0, 13, 0));
        LightTrait lt = new LightTrait(lightPather);
        lt.z = 0.1f;
        lightPather.traits.add(lt);
        lightPather.traits.add(new PathFollowerTrait(lightPather, PathFollowerTrait.PathFollowStyle.LOOP, 0,
                new Vector2(0, 13),
                new Vector2(20, 13)));
        lightPather.initializeTraits();
        objs.add(lightPather);

        BlankObject lightPather2 = new BlankObject();
        lightPather2.traits.add(new PositionTrait(lightPather2, -50, -15, 0));
        LightTrait lt2 = new LightTrait(lightPather2, Color.RED);
        lt2.z = 0.01f;
        lightPather2.traits.add(lt2);
        PathFollowerTrait pft = new PathFollowerTrait(lightPather2, PathFollowerTrait.PathFollowStyle.LOOP, 0,
                new Vector2(-50, -13),
                new Vector2(96, -13));
        pft.travelTimeInMilliseconds = 2000;
        lightPather2.traits.add(pft);
        lightPather2.initializeTraits();
        objs.add(lightPather2);


        final BlankObject trigger = new BlankObject();
        trigger.addAndInitializeTrait(new UpdatableTrait(trigger, new Runnable() {
            @Override
            public void run() {
                if (player.getTrait(PositionTrait.class).x > 13){
                    trigger.markForDeletion();
                    target.markForDeletion();
                    rightSideBoundary.markForDeletion();
                    GameScreen.addObjects(nextSection());
                }
            }
        }));
        objs.add(trigger);

        return objs;
    }

    private List<GameObject> section1(){
        List<GameObject> objs = new ArrayList<>();

        final CameraTarget target = new CameraTarget(screenWidth, 0);
        objs.add(target);

        objs.add(newBottomBoundary(1));
        objs.add(path(-halfScreenWidth + screenWidth, -1, halfScreenWidth + screenWidth, -1));

        objs.add(new Background(1, "environment/background_two"));

        Light light = new Light(screenWidth - 11, 5);
        light.light.color = Color.BLUE;
        light.light.attenuation.z = 20;
        light.light.z = 0.1f;
        objs.add(light);

        final Boundary rightSideBoundary = path(halfScreenWidth + screenWidth, -1, halfScreenWidth + screenWidth, -halfScreenHeight);
        objs.add(rightSideBoundary);

        return objs;
    }

    private Boundary newBottomBoundary(int section){
        float left = -halfScreenWidth + (section * screenWidth);
        float right = left + screenWidth;
        return new Boundary(new Vector2(left, -halfScreenHeight), new Vector2(right, -halfScreenHeight));
    }

    private Boundary path(float... f){
        if (f.length % 2 != 0){
            throw new RuntimeException("Cannot have a vector2 path with an odd number of floats");
        }
        Vector2[] vs = new Vector2[f.length / 2];
        for (int i = 0; i < f.length; i += 2){
            vs[i / 2] = new Vector2(f[i], f[i+1]);
        }
        return new Boundary(vs);
    }
}
