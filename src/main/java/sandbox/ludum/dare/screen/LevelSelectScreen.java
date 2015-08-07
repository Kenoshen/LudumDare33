package sandbox.ludum.dare.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.winger.struct.Tups;
import sandbox.ludum.dare.Game;
import sandbox.ludum.dare.level.Level;
import sandbox.ludum.dare.level.TestLevel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/6/15.
 */
public class LevelSelectScreen implements Screen {

    private Game game;
    private Stage stage = new Stage();

    private Skin skin;
    private Table levelButtons;
    private List<Tups.Tup2<Level, ImageTextButton>> lvls = new ArrayList<>();

    public LevelSelectScreen(Game game){
        this.game = game;

        skin = new Skin(Gdx.files.internal("src/main/resources/skins/menu-skin.json"),
                new TextureAtlas(Gdx.files.internal("src/main/resources/packed/ui.atlas")));

        levelButtons = new Table();
        stage.addActor(levelButtons);

        List<Level> levels = new ArrayList<>();
        levels.add(new TestLevel());
        for (FileHandle fileHandle : Gdx.files.internal("src/main/resources/levels").list()){
            levels.add(new Level(fileHandle));
        }
        for (Level level : levels){
            ImageTextButton btn = new ImageTextButton(level.name(), skin, "simple");
            btn.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // TODO: go to the game screen with the level object
                }
            });
            levelButtons.add(btn); // TODO: do any button positioning
            lvls.add(new Tups.Tup2<>(level, btn));
        }

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.39f, 0.58f, 0.92f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
