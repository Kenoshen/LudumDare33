package ludum.dare.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import ludum.dare.Game;


public class CutsceneScreen implements Screen {

    private Game game;
    private Stage stage = new Stage();

    private TextField storyTextField;
    BitmapFont whiteText;

    public CutsceneScreen(final Game game){
        this.game = game;



//        TextFieldStyle(BitmapFont font, Color fontColor, Drawable cursor, Drawable selection, Drawable background)

//        storyTextField = new TextField("Hello", skin);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        storyTextField.addAction(
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

                                    }
                                }
                        )
                )
        );
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
