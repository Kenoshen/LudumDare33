package ludum.dare.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
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

    public CutsceneScreen(final Game game){
        this.game = game;

        listStoryText = new ArrayList<>();
        keyboard = CKeyboard.instance;

        listStoryText.add(generateWrappableLabel("In the future... ", SkinManager.instance.getSkin("menu-skin"), true));
        listStoryText.add(generateWrappableLabel("Hobos are considered a myth... ", SkinManager.instance.getSkin("menu-skin"), true));
        listStoryText.add(generateWrappableLabel("These \"creatures\" are found \nonly in children's tales... ", SkinManager.instance.getSkin("menu-skin"), true));
        listStoryText.add(generateWrappableLabel("Depicted as killers and robbers... ", SkinManager.instance.getSkin("menu-skin"), true));
        listStoryText.add(generateWrappableLabel("How would people react if one showed up? ", SkinManager.instance.getSkin("menu-skin"), true));

        bitmapFont = new BitmapFont();

        for(Label l : listStoryText) {
            Container tmpContainer = new Container(l);
            tmpContainer.setFillParent(true);
            l.setAlignment(Align.center);
            stage.addActor(tmpContainer);
        }

        Gdx.input.setInputProcessor(stage);
    }

    private Label generateWrappableLabel(String text, Skin skin, boolean wrappable){
        Label tmp = new Label(text, skin);
        tmp.setFontScale(.85f);
        tmp.setWrap(wrappable);

        return tmp;
    }

    @Override
    public void show() {

        for(Label l : listStoryText){
            l.addAction(Actions.alpha(0));
        }

        showNextText(0);
    }

    private void showNextText(final int index){

        if(index == listStoryText.size()){
            return;
        }

        if(index == listStoryText.size()-1){
            listStoryText.get(index).addAction(Actions.sequence(
                    Actions.delay(0.25f),
                    Actions.fadeIn(0.5f),
                    Actions.delay(3.5f),
                    Actions.fadeOut(0.5f),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            game.setScreen(new SplashScreen(game));
                        }
                    })));
        }

        listStoryText.get(index).addAction(Actions.sequence(
                Actions.delay(0.25f),
                Actions.fadeIn(0.5f),
                Actions.delay(3.5f),
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

        if (keyboard.isKeyJustPressed(KeyboardKey.S)){
            game.setScreen(new MainMenuScreen(game));
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
