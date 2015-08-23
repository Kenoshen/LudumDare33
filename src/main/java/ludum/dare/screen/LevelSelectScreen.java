package ludum.dare.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.winger.struct.Tups;
import ludum.dare.Game;
import ludum.dare.level.Level;
import ludum.dare.level.TestLevel;
import ludum.dare.level.TestLevel2;
import ludum.dare.utils.SkinManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/6/15.
 */
public class LevelSelectScreen implements Screen {

    private Game game;
    private Stage stage = new Stage();

    private Table levelButtons;
    private List<Tups.Tup2<Level, TextButton>> lvls = new ArrayList<>();

    public LevelSelectScreen(final Game game){
        this.game = game;

        Skin skin = SkinManager.instance.getSkin("menu-skin");

        final Sound soundSelect = Gdx.audio.newSound(Gdx.files.internal("sfx/Menu_Select.ogg"));

        levelButtons = new Table();
        levelButtons.setFillParent(true);
        stage.addActor(levelButtons);

        List<Level> levels = new ArrayList<>();
        levels.add(new TestLevel());
        levels.add(new TestLevel2());
        for (FileHandle fileHandle : Gdx.files.internal("levels").list()){
            levels.add(new Level(fileHandle));
        }
        for (final Level level : levels){
            TextButton btn = new TextButton(level.name(), skin, "simple");
            btn.setUserObject(level);
            btn.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    soundSelect.play(.25f);

                    stage.getRoot().setColor(Color.WHITE);
                    stage.addAction(Actions.sequence(Actions.fadeOut(2f),
                            Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    game.setScreen(new GameScreen(game, level));
                                }
                            })));
                }
            });
            levelButtons.add(btn); // TODO: do any button positioning
            lvls.add(new Tups.Tup2<>(level, btn));
        }

        TextButton backButton = new TextButton("Back", skin, "simple");
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        backButton.setPosition(0, 0);
        stage.addActor(backButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
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
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
