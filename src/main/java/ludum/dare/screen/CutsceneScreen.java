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
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.SkinManager;
import ludum.dare.world.SoundLibrary;

import java.util.ArrayList;


public class CutsceneScreen implements Screen {

    private Game game;
    private Stage stage = new Stage();

    ArrayList<Label> listStoryText;
    BitmapFont bitmapFont;

    CKeyboard keyboard;

    private Music introMusic;
    private boolean fadeIn = true;
    private boolean fadeOut = false;

    public CutsceneScreen(final Game game) {

        introMusic = SoundLibrary.GetMusic("intro-withoutDelay");
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
        AtlasManager.instance.loadAtlasAsynch("packed/game.atlas");
        AtlasManager.instance.loadAtlasAsynch("packed/game_n.atlas");
        for (Label l : listStoryText) {
            l.addAction(Actions.alpha(0));
        }

        if (!introMusic.isPlaying()) {
            introMusic.setLooping(true);
            introMusic.setVolume(0);
            introMusic.play();
        }
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
                            fadeOut = true;
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

        AtlasManager.instance.update();
//        private float updateTimer = 0;
//        updateTimer -= delta;
//        if (updateTimer < 0) {
//            updateTimer = .05f;
//            AtlasManager.instance.update();
//        }

        stage.act();
        stage.draw();

        keyboard.update();

        if (keyboard.isKeyJustPressed(KeyboardKey.S)) {
            game.setScreen(new SplashScreen(game));
        }

        if (fadeIn) {
            float vol = introMusic.getVolume() + .003f;
            if (vol > 0.4f){
                vol = 0.4f;
                fadeIn = false;
            }
            introMusic.setVolume(vol);
        }

        if (fadeOut) {
//            float vol = introMusic.getVolume() - .008f;
//            if (vol < 0){
//                vol = 0;
//            }
//            introMusic.setVolume(vol);
//            if (vol == 0) {
//                introMusic.stop();
                game.setScreen(new SplashScreen(game));
//            }
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
