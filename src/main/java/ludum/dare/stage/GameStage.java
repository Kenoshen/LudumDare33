package ludum.dare.stage;

import com.winger.draw.texture.CSpriteBatch;
import com.winger.physics.CWorld;
import com.winger.ui.Page;
import ludum.dare.Director;
import ludum.dare.trait.DrawableTrait;
import ludum.dare.trait.GameObject;
import ludum.dare.trait.PhysicalTrait;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/2/15.
 */
public class GameStage extends Stage {

    public List<GameObject> gameObjects = new ArrayList<>();
    private List<GameObject> objsToDelete = new ArrayList<>();
    public GameStage(Page ui, Director director) {
        super(ui, director);
    }

    @Override
    public void update(float delta) {

        // handle deletion of objects gracefully
        gameObjects.forEach(obj -> {
            if (obj.shouldBeDeleted()) {
                objsToDelete.add(obj);
            }
        });

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

    @Override
    public void draw(CSpriteBatch spriteBatch) {
        gameObjects.forEach(obj ->{
            DrawableTrait drawable = obj.getTrait(DrawableTrait.class);
            if (drawable != null){
                drawable.draw(spriteBatch);
            }
        });
    }
    public void addGameObject(GameObject obj){
        if (obj != null) {
            gameObjects.add(obj);
        }
    }
}
