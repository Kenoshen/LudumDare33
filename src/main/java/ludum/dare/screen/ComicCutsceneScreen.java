package ludum.dare.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.winger.log.HTMLLogger;
import com.winger.log.LogGroup;
import com.winger.struct.Tups;
import ludum.dare.Game;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.Sprite;
import ludum.dare.world.SoundLibrary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/6/15.
 */
public class ComicCutsceneScreen implements Screen {
    private static final HTMLLogger log = HTMLLogger.getLogger(GameScreen.class, LogGroup.System);

    private Stage stage;
    private Game game;

    private int currentAnimation = 0;
    private float stateTime = 0;
    private float totalTime = 0;
    private List<Animation> animations = new ArrayList<>();
    private Sprite sprite;
    private List<Tups.Tup2<Float, Sound>> sounds = new ArrayList<>();



    public ComicCutsceneScreen(Game game){

        AtlasManager.instance.loadAtlas("packed/game.atlas");
        this.game = game;
        stage = new Stage();

        sprite = new Sprite();

        animations.add(new Animation(0.1f, AtlasManager.instance.findRegions("bum/stand/bumStand"), Animation.PlayMode.NORMAL));
        animations.add(new Animation(0.1f, AtlasManager.instance.findRegions("bum/jump/bumJump"), Animation.PlayMode.NORMAL));
        animations.add(new Animation(0.1f, AtlasManager.instance.findRegions("bum/walk/bumWalk"), Animation.PlayMode.NORMAL));

        Image tmpImage = new Image(new SpriteDrawable(sprite));
        tmpImage.setFillParent(true);
        stage.addActor(tmpImage);



        sounds.add(Tups.tup2(1.0f, SoundLibrary.GetSound("Pop")));
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        totalTime += delta;
        stateTime += delta;

        if (currentAnimation < animations.size()) {
            sprite.setRegion(animations.get(currentAnimation).getKeyFrame(stateTime));
            if (animations.get(currentAnimation).isAnimationFinished(stateTime)) {
                if (animations.get(currentAnimation).getPlayMode() == Animation.PlayMode.NORMAL) {
                    currentAnimation++;
                    stateTime = 0;
                }
            }
        }

        if (sounds.size() > 0){
            if (sounds.get(0).i1() < totalTime){
                sounds.get(0).i2().play();
                sounds.remove(0);
            }
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
