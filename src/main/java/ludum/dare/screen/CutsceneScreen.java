package ludum.dare.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.state.KeyboardKey;
import ludum.dare.Game;
import ludum.dare.utils.SkinManager;

import java.util.ArrayList;


public class CutsceneScreen implements Screen {

    private Game game;
    private Stage stage = new Stage();

    ArrayList<Label> listStoryText;
    BitmapFont bitmapFont;

    CKeyboard keyboard;

    private Music introMusic;
    private boolean fade = false;

    public CutsceneScreen(final Game game) {

        introMusic = Gdx.audio.newMusic(Gdx.files.internal("music/intro-withoutDelay.mp3"));
        introMusic.setVolume(1);
        this.game = game;

        listStoryText = new ArrayList<>();
        keyboard = CKeyboard.instance;

        listStoryText.add(generateWrappableLabel("In the future... ", SkinManager.instance.getSkin("menu-skin"), true));
        listStoryText.add(generateWrappableLabel("generic stories are told... ", SkinManager.instance.getSkin("menu-skin"), true));
        listStoryText.add(generateWrappableLabel("Something something hobos... ", SkinManager.instance.getSkin("menu-skin"), true));
        listStoryText.add(generateWrappableLabel("One comes back years after the\nrest had fallen", SkinManager.instance.getSkin("menu-skin"), true));

        bitmapFont = new BitmapFont();

        for (Label l : listStoryText) {
            Container tmpContainer = new Container(l);
            tmpContainer.setFillParent(true);
            l.setAlignment(Align.center);
            stage.addActor(tmpContainer);
        }

        Gdx.input.setInputProcessor(stage);
    }

    private Label generateWrappableLabel(String text, Skin skin, boolean wrappable) {
        Label tmp = new Label(text, skin);
        tmp.setFontScale(.85f);
        tmp.setWrap(wrappable);

        return tmp;
    }

    @Override
    public void show() {
        for (Label l : listStoryText) {
            l.addAction(Actions.alpha(0));
        }

        introMusic.play();
        showNextText(0);
    }

    private void showNextText(final int index) {

        if (index == listStoryText.size()) {
            return;
        }

        if (index == listStoryText.size() - 1) {
            listStoryText.get(index).addAction(Actions.sequence(
                    Actions.delay(0.25f),
                    Actions.fadeIn(0.5f),
                    Actions.delay(2f),
                    Actions.fadeOut(0.5f),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            fade = true;
                        }
                    })));
        }

        listStoryText.get(index).addAction(Actions.sequence(
                Actions.delay(0.25f),
                Actions.fadeIn(0.5f),
                Actions.delay(2f),
                Actions.fadeOut(0.5f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        showNextText(index + 1);
                    }
                })));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        keyboard.update();

        if (keyboard.isKeyJustPressed(KeyboardKey.S)) {
            game.setScreen(new MainMenuScreen(game));
        }

        if (fade) {
            introMusic.setVolume(introMusic.getVolume() - .008f);
            if (introMusic.getVolume() <= 0) {
                introMusic.stop();
                game.setScreen(new SplashScreen(game));
                return;
            }
        }
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
