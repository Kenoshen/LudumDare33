package ludum.dare.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.state.KeyboardKey;
import com.winger.log.HTMLLogger;
import com.winger.log.LogGroup;
import ludum.dare.Game;
import ludum.dare.utils.AtlasManager;

/**
 * Created by mwingfield on 8/6/15.
 */
public class SplashScreen implements Screen {
    private static final HTMLLogger log = HTMLLogger.getLogger(GameScreen.class, LogGroup.System);

    private Image ldWallpaper;
    private Image bdWallpaper;
    private Stage stage;
    private Game game;

    public SplashScreen(Game game){
        this.game = game;
        stage = new Stage();
        ldWallpaper = new Image(AtlasManager.instance.findRegion("ld33"));
        ldWallpaper.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bdWallpaper = new Image(AtlasManager.instance.findRegion("bitDecayGamesInverted"));
        bdWallpaper.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(ldWallpaper);
        stage.addActor(bdWallpaper);
    }

    @Override
    public void show() {
        AtlasManager.instance.loadAtlasAsynch("packed/game.atlas");
        AtlasManager.instance.loadAtlasAsynch("packed/game_n.atlas");
        bdWallpaper.addAction(Actions.alpha(0));
        ldWallpaper.addAction(
                Actions.sequence(
                        Actions.alpha(0),
                        Actions.delay(0.25f),
                        Actions.fadeIn(0.5f),
                        Actions.delay(1.5f),
                        Actions.fadeOut(0.5f),
                        Actions.run(
                                new Runnable() {
                                        @Override
                                        public void run() {
                                            bdWallpaper.addAction(Actions.sequence(
                                                    Actions.fadeIn(0.5f),
                                                    Actions.delay(1.5f),
                                                    Actions.fadeOut(0.5f),
                                                    Actions.run(
                                                            new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    game.setScreen(new MainMenuScreen(game));
                                                                }
                                                            }
                                                    )));
                                        }
                                }
                        )
                )
        );
    }

    @Override
    public void render(float delta) {
        AtlasManager.instance.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (CKeyboard.instance.isKeyJustPressed(KeyboardKey.S)) {
            game.setScreen(new MainMenuScreen(game));
        }

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
