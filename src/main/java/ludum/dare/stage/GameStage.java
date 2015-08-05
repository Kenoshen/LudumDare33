package ludum.dare.stage;

import com.winger.draw.texture.CSprite;
import com.winger.draw.texture.CSpriteBatch;
import com.winger.physics.CWorld;
import com.winger.struct.Tups;
import com.winger.ui.Page;
import ludum.dare.Director;
import ludum.dare.scene.Scene;
import ludum.dare.trait.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/2/15.
 */
public class GameStage extends Stage {
    public static float WORLD_STEP_TIME = 1 / 60f;

    public List<GameObject> gameObjects = new ArrayList<>();
    private List<GameObject> objsToDelete = new ArrayList<>();

    public GameStage(Page ui, Director director) {
        super(ui, director);
        log.debug("Game Stage constructed");
    }

    public void loadScene(Scene scene){
        // should clean up old game objects (remove from world, etc)
        objsToDelete = new ArrayList<>();
        gameObjects.forEach(obj -> {
            obj.markForDeletion();
            objsToDelete.add(obj);
        });
        removeMarkedGameObjects();
        //
        log.debug("Load scene");
        gameObjects = scene.loadScene();
    }

    @Override
    public void update() {
        CWorld.world.update(WORLD_STEP_TIME);
        gameObjects.forEach(obj -> {
            List<Trait> traits = obj.getTraits(ControlTrait.class, PhysicalTrait.class, DebugTrait.class);
            if (traits.get(0) != null) {
                ((ControlTrait) traits.get(0)).update();
            }
            if (traits.get(1) != null) {
                ((PhysicalTrait) traits.get(1)).step();
            }
            if (traits.get(2) != null) {
                ((DebugTrait) traits.get(2)).debug();
            }

            // handle deletion of objects gracefully
            if (obj.shouldBeDeleted()) {
                objsToDelete.add(obj);
            }
        });

        removeMarkedGameObjects();
    }

    @Override
    public void draw(CSpriteBatch spriteBatch) {
        gameObjects.forEach(obj ->{
            DrawableTrait drawable = obj.getTrait(DrawableTrait.class);
            if (drawable != null){
                drawable.draw(spriteBatch);
            }
        });
        if (CWorld.world.debug()){
            CWorld.world.draw();
        }
    }

    public void addGameObject(GameObject obj){
        log.debug("Add game object");
        if (obj != null) {
            gameObjects.add(obj);
        }
    }

    private void removeMarkedGameObjects(){
        // handle deletion of objects gracefully
        if (objsToDelete.size() > 0){
            objsToDelete.forEach(obj -> {
                gameObjects.remove(obj);
                PhysicalTrait pt = obj.getTrait(PhysicalTrait.class);
                if (pt != null){
                    pt.delete();
                }
            });
            objsToDelete = new ArrayList<>();
        }
    }
}
