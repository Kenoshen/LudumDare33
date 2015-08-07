package ludum.dare.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.winger.Winger;
import com.winger.draw.texture.CSpriteBatch;
import com.winger.ui.Element;
import com.winger.ui.ElementFactory;
import com.winger.ui.ElementRecord;
import com.winger.ui.Page;
import com.winger.ui.element.impl.DefaultElement;
import com.winger.ui.element.impl.GridElement;
import ludum.dare.Director;
import ludum.dare.scene.Scene;
import ludum.dare.scene.TestScene;
import ludum.dare.scene.TestScene2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/5/15.
 */
public class LevelSelectStage extends Stage{

    public List<Scene> scenes = new ArrayList<>();
    private GameStage gameStage;

    public LevelSelectStage(Page ui, Director director, String sceneFilesDir, GameStage gameStage) {
        super(ui, director);
        log.debug("Level Select Stage constructed");
        this.gameStage = gameStage;
        load(sceneFilesDir);
    }

    private void load(String sceneFilesDir){
        FileHandle dir = Gdx.files.internal(sceneFilesDir);
        if (dir.exists() && dir.isDirectory()){
            for (FileHandle file : dir.list()){
                scenes.add(new Scene(file));
            }
        }

        scenes.add(new TestScene());
        scenes.add(new TestScene2());

        GridElement parent = (GridElement) ui.getElementById("grid");
        for (Scene scene : scenes) {
            DefaultElement element = new DefaultElement(ui, null, parent);
            element.initialize();
            element.userData(scene);
            element.id(scene.name());
            element.text(scene.name());
            element.textColor(Color.WHITE.cpy());
            element.color(Color.BLACK.cpy());
            element.texture(Winger.texture.getTexture("white"));
            element.width(200);
            element.height(100);
            element.font(Winger.font.getDefaultFont());
            element.transitionOnSelect("game");
            element.onSelectEnd(scene.name() + "SelectEnd", e -> {
                Scene s = (Scene) e.userData();
                gameStage.loadScene(s);
            });
            ui.addElement(element);
            parent.children().add(element);
            log.debug("Add level select option for: " + scene.name() + "\n" + element);
        }
        parent.arrangeChildren();
        log.debug("Grid children: " + parent.children());
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(CSpriteBatch spriteBatch) {

    }
}
