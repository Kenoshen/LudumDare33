package ludum.dare.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import ludum.dare.Game;
import ludum.dare.level.TestSubLevels;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.SkinManager;
import ludum.dare.world.SoundLibrary;


/**
 * Created by mwingfield on 8/6/15.
 */
public class MainMenuScreen implements Screen {

    private Game game;
    private Stage stage = new Stage();

    private Table menu;
    private Image background;
    private Label title;
    private TextButton playBtn;
    private TextButton creditsBtn;
    private TextButton quitBtn;

    public MainMenuScreen(final Game game){
        this.game = game;

        Skin skin = SkinManager.instance.getSkin("menu-skin");

        background = new Image(AtlasManager.instance.findRegion("titleBlank"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        title = new Label("Robo Hobo", skin);
        title.setFontScale(2);
        title.setAlignment(Align.top);
        title.setFillParent(true);

        playBtn = new TextButton("Play", skin, "button");
        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TestSubLevels level = new TestSubLevels();
                GameScreen screen = new GameScreen(game, level);
                game.setScreen(screen);
            }
        });
        creditsBtn = new TextButton("Credits", skin, "button");
        creditsBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int seconds = 1;
                int moveOff = Gdx.graphics.getWidth();
                menu.addAction(Actions.sequence(
                        Actions.moveBy(moveOff, 0, seconds),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                game.setScreen(new CreditsScreen(game));
                            }
                        })
                ));
            }
        });
        quitBtn = new TextButton("Exit", skin, "button");
        quitBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });


        menu = new Table();
        menu.add(playBtn).height(60).padBottom(20).padTop(150).row();
        menu.add(creditsBtn).height(60).padBottom(20).row();
        menu.add(quitBtn).height(60).padBottom(20).row();
        menu.align(Align.center);
        menu.setFillParent(true);

        stage.addActor(background);
        stage.addActor(title);
        stage.addActor(menu);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Music music = SoundLibrary.GetMusic("intro-withoutDelay");
        if (!music.isPlaying()) {
            music.setLooping(true);
            music.setVolume(0.4f);
            music.play();
        }
        // animate the main menu when entering
        int seconds = 1;
        int moveOff = Gdx.graphics.getWidth();
        menu.addAction(Actions.sequence(
                Actions.moveBy(moveOff, 0),
                Actions.moveBy(-moveOff, 0, seconds)
        ));
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
