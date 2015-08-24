package ludum.dare.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
    private List<Tups.Tup2<Animation, Float>> animations = new ArrayList<>();
    private Sprite sprite;
    private List<Tups.Tup2<Float, Sound>> sounds = new ArrayList<>();
    private Music music;

    private boolean fade = false;

    public ComicCutsceneScreen(Game game){
        music = SoundLibrary.GetMusic("rain");

        this.game = game;
        stage = new Stage();

        sprite = new Sprite();

        animations.add(Tups.tup2(new Animation(0.1f, AtlasManager.instance.findRegions("1firstPanel"), Animation.PlayMode.LOOP), 3.0f));
        animations.add(Tups.tup2(new Animation(0.1f, AtlasManager.instance.findRegions("2fourthPanel"), Animation.PlayMode.LOOP), 3.0f));
        animations.add(Tups.tup2(new Animation(0.1f, AtlasManager.instance.findRegions("3secondPanel"), Animation.PlayMode.NORMAL), 0.0f));
        animations.add(Tups.tup2(new Animation(0.1f, AtlasManager.instance.findRegions("4thirdPanel"), Animation.PlayMode.LOOP), 3.0f));
        animations.add(Tups.tup2(new Animation(0.1f, AtlasManager.instance.findRegions("5fourthPanel"), Animation.PlayMode.NORMAL), 0.0f));
        animations.add(Tups.tup2(new Animation(0.1f, AtlasManager.instance.findRegions("6fifthPanel"), Animation.PlayMode.NORMAL), 0.0f));
        animations.add(Tups.tup2(new Animation(0.1f, AtlasManager.instance.findRegions("7sixthPanel"), Animation.PlayMode.LOOP), 100.0f));

        Image tmpImage = new Image(new SpriteDrawable(sprite));
        tmpImage.setFillParent(true);
        stage.addActor(tmpImage);

        sounds.add(Tups.tup2(5.5f, SoundLibrary.GetSound("dumpster")));
        sounds.add(Tups.tup2(12.0f, SoundLibrary.GetSound("lightningStrike")));
        sounds.add(Tups.tup2(13.0f, SoundLibrary.GetSound("lightningStrike")));
    }

    @Override
    public void show() {
        music.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        totalTime += delta;
        stateTime += delta;

        if (currentAnimation < animations.size()) {
            sprite.setRegion(animations.get(currentAnimation).i1().getKeyFrame(stateTime));
            if (animations.get(currentAnimation).i1().isAnimationFinished(stateTime)) {
                if (animations.get(currentAnimation).i1().getPlayMode() == Animation.PlayMode.NORMAL || stateTime > animations.get(currentAnimation).i2()) {
                    currentAnimation++;
                    stateTime = 0;
                }
            }
        }
        if(!fade){
            if (totalTime > 18) {
                fade = true;
            }
        }

        if (sounds.size() > 0){
            if (sounds.get(0).i1() < totalTime){
                sounds.get(0).i2().play();
                sounds.remove(0);
            }
        }

        if (fade) {
            music.setVolume(music.getVolume() - .008f);
            if (music.getVolume() <= .1) {
                fade = false;
                music.setVolume(0.01f);
                music.stop();
                stage.addAction(Actions.sequence(
                        Actions.fadeOut(1),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                game.setScreen(new CutsceneScreen(game));
                            }
                        })
                ));
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
