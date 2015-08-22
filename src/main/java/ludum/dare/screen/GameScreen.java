package ludum.dare.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.winger.camera.FollowOrthoCamera;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.log.HTMLLogger;
import com.winger.log.LogGroup;
import com.winger.physics.CWorld;
import ludum.dare.Conf;
import ludum.dare.Game;
import ludum.dare.level.Level;
import ludum.dare.trait.*;
import ludum.dare.utils.SkinManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/6/15.
 */
public class GameScreen implements Screen {
    private static final HTMLLogger log = HTMLLogger.getLogger(GameScreen.class, LogGroup.System);

    private Game game;
    private Stage stage = new Stage();

    private FollowOrthoCamera camera;
    private CWorld world;

    private CMouse mouse;
    private CKeyboard keyboard;

    private SpriteBatch batch;

    public List<GameObject> gameObjects = new ArrayList<>();
    private List<GameObject> objsToDelete = new ArrayList<>();

    public GameScreen(final Game game, Level level){
        this.game = game;
        //
        camera = new FollowOrthoCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = camera.minZoom;
        camera.minZoom = 1;
        //
        world = new CWorld(camera);
        world.init(new Vector2(0, 0), true);
        world.debug(Conf.instance.isDebug());
        //
        mouse = CMouse.instance;
        keyboard = CKeyboard.instance;
        //
        batch = new SpriteBatch();
        //
        TextButton btn = new TextButton("Back", SkinManager.instance.getSkin("menu-skin"), "simple");
        btn.setPosition(50, 50);
        btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectScreen(game));
            }
        });
        stage.addActor(btn);
        Gdx.input.setInputProcessor(stage);
        //
        loadLevel(level);
    }

    private void loadLevel(Level level){
        // should clean up old game objects (remove from world, etc)
        objsToDelete = new ArrayList<>();
        for (GameObject obj : gameObjects){
            obj.markForDeletion();
            objsToDelete.add(obj);
        }
        removeMarkedGameObjects();
        //
        log.debug("Load scene");
        gameObjects = level.loadLevel();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.39f, 0.58f, 0.92f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        CWorld.world.update(Conf.instance.worldStepTime());
        for (GameObject obj : gameObjects){
            List<Trait> traits = obj.getTraits(ControlTrait.class, PhysicalTrait.class, DebugTrait.class, UpdatableTrait.class);
            if (traits.get(0) != null) {
                ((ControlTrait) traits.get(0)).update();
            }
            if (traits.get(1) != null) {
                ((PhysicalTrait) traits.get(1)).step();
            }
            if (traits.get(2) != null) {
                ((DebugTrait) traits.get(2)).debug();
            }
            if (traits.get(3) != null) {
                ((UpdatableTrait) traits.get(3)).update(delta);
            }

            // handle deletion of objects gracefully
            if (obj.shouldBeDeleted()) {
                objsToDelete.add(obj);
            }
        }

        removeMarkedGameObjects();

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        //batch.setTransformMatrix(camera.view);
        batch.begin();
        for (GameObject obj : gameObjects){
            List<Trait> traits = obj.getTraits(AnimatorTrait.class, DrawableTrait.class, CameraFollowTrait.class);
            if (traits.get(0) != null){
                ((AnimatorTrait) traits.get(0)).update(delta);
            }
            if (traits.get(1) != null){
                ((DrawableTrait) traits.get(1)).draw(batch);
            }
            if (traits.get(2) != null){
                ((CameraFollowTrait) traits.get(2)).updateCamera(camera);
            }
        }
        batch.end();

        if (CWorld.world.debug()){
            CWorld.world.draw();
        }

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
        objsToDelete = new ArrayList<>();
        for (GameObject obj : gameObjects){
            obj.markForDeletion();
            objsToDelete.add(obj);
        }
        removeMarkedGameObjects();
        world._world.dispose();
    }

    private void removeMarkedGameObjects(){
        // handle deletion of objects gracefully
        if (objsToDelete.size() > 0){
            for (GameObject obj : objsToDelete){
                gameObjects.remove(obj);
                PhysicalTrait pt = obj.getTrait(PhysicalTrait.class);
                if (pt != null){
                    pt.delete();
                }
            }
            objsToDelete = new ArrayList<>();
        }
    }
}
