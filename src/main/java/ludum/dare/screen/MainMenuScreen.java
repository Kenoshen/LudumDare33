package ludum.dare.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ludum.dare.Game;


/**
 * Created by mwingfield on 8/6/15.
 */
public class MainMenuScreen implements Screen {

    private Game game;
    private Stage stage = new Stage();

    private Skin skin;
    private Table menu;
    private TextButton playBtn;
    private TextButton highscoreBtn;
    private TextButton optionsBtn;
    private TextButton quitBtn;

    public MainMenuScreen(final Game game){
        this.game = game;

        skin = new Skin(Gdx.files.internal("src/main/resources/skins/menu-skin.json"),
                new TextureAtlas(Gdx.files.internal("src/main/resources/packed/ui.atlas")));

        playBtn = new TextButton("Play", skin, "flare");
        playBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectScreen(game));
            }
        });
        highscoreBtn = new TextButton("Highscores", skin, "flare");
        optionsBtn = new TextButton("Options", skin, "simple");
        quitBtn = new TextButton("Exit", skin, "simple");
        quitBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        menu = new Table();
        menu.add(playBtn).height(60).padBottom(5).padTop(150).row();
        menu.add(highscoreBtn).height(60).padBottom(5).row();
        menu.add(optionsBtn).height(60).padBottom(5).row();
        menu.add(quitBtn).height(60).padBottom(5).row();
        menu.setFillParent(true);

        stage.addActor(menu);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        // animate the main menu when entering
        int startFrom = -300;
        menu.addAction(Actions.sequence(Actions.moveBy(0, startFrom), Actions.moveBy(0, -startFrom, 1)));
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
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
