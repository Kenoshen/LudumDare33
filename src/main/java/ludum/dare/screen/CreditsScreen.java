package ludum.dare.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.winger.log.HTMLLogger;
import com.winger.log.LogGroup;
import ludum.dare.Game;
import ludum.dare.level.TestSubLevels;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.SkinManager;

/**
 * Created by mwingfield on 8/6/15.
 */
public class CreditsScreen implements Screen {
    private static final HTMLLogger log = HTMLLogger.getLogger(GameScreen.class, LogGroup.System);

    private Image background;
    private Table table;
    private Button back;

    private Stage stage;
    private Game game;

    public CreditsScreen(final Game game){
        this.game = game;
        stage = new Stage();

        Skin skin = SkinManager.instance.getSkin("menu-skin");

        background = new Image(AtlasManager.instance.findRegion("titleBlank"));
        background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(background);

        table = new Table(skin);
        table.setFillParent(true);
        table.add("Jake Fisher:        Programming\n" +
                "Eric Meredith:      Art\n" +
                "Logan Moore:       Programming\n" +
                "Tanner Moore:      Programming\n" +
                "Michael Wingfield:  Programming").row();
        table.add("Unless otherwise stated, assets listed are\nused under the Creative Commons License.\n" +
                "\n" +
                "Intro Music:            SKIQI - WIthout Delay\n" +
                "Cutscene Music:        Secret Tunnels\n" +
                "footstep1:               swuing\n" +
                "footstep2:               swuing\n" +
                "ld33wallpaper:         '01010111'\n" +
                "Shaders With Normals: davedes").row();
        stage.addActor(table);

        back = new TextButton("Back", skin, "button");
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int seconds = 1;
                int moveOff = Gdx.graphics.getWidth();
                table.addAction(Actions.sequence(
                        Actions.moveBy(moveOff, 0, seconds)
                ));
                back.addAction(Actions.sequence(
                        Actions.moveBy(moveOff, 0, seconds),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                game.setScreen(new MainMenuScreen(game));
                            }
                        })
                ));
            }
        });
        stage.addActor(back);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        int seconds = 1;
        int percentage = (int)(Gdx.graphics.getWidth() * 0.23f);
        int moveOff = Gdx.graphics.getWidth();
        table.addAction(Actions.sequence(
                Actions.moveBy(moveOff, 0),
                Actions.moveBy(-(moveOff - percentage), 0, seconds)
        ));
        back.addAction(Actions.sequence(
                Actions.moveBy(moveOff, 0),
                Actions.moveBy(-(moveOff), 0, seconds)
        ));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
