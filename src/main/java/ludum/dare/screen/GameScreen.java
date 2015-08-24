package ludum.dare.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.winger.camera.FollowOrthoCamera;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.log.HTMLLogger;
import com.winger.log.LogGroup;
import com.winger.physics.CWorld;
import com.winger.struct.Tups;
import ludum.dare.Conf;
import ludum.dare.Game;
import ludum.dare.level.Level;
import ludum.dare.trait.*;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.SkinManager;
import ludum.dare.utils.Sprite;
import ludum.dare.world.AIHiveMind;
import ludum.dare.world.SoundLibrary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by mwingfield on 8/6/15.
 */
public class GameScreen implements Screen {
    private static final HTMLLogger log = HTMLLogger.getLogger(GameScreen.class, LogGroup.System);

    private static final int MAX_LIGHTS = 10;

    private Game game;
    private Stage stage = new Stage();

    private FollowOrthoCamera camera;
    private CWorld world;

    private CMouse mouse;
    private CKeyboard keyboard;

    private SpriteBatch batch;
    private ShapeRenderer shaper;

    private Music music;

    List<Tups.Tup2<GameObject, GameObject>> listCollisions;

    public  List<GameObject> gameObjects = new ArrayList<>();
    private List<GameObject> objsToDelete = new ArrayList<>();
    private static List<GameObject> objsToAdd = new ArrayList<>();

    private AIHiveMind AIHM = new AIHiveMind();

    ShaderProgram program;
    private Comparator<? super GameObject> compare = new Comparator<GameObject>() {
        @Override
        public int compare(GameObject o1, GameObject o2) {
            List<Trait> o1Traits = o1.getTraits(PhysicalTrait.class, PositionTrait.class);
            float y1 = 0;
            if (o1Traits.get(0) != null){
                y1 = ((PhysicalTrait) o1Traits.get(0)).body.getPosition().y;
            } else if (o1Traits.get(1) != null){
                y1 = ((PositionTrait) o1Traits.get(1)).y;
            }

            List<Trait> o2Traits = o2.getTraits(PhysicalTrait.class, PositionTrait.class);
            float y2 = 0;
            if (o2Traits.get(0) != null){
                y2 = ((PhysicalTrait) o2Traits.get(0)).body.getPosition().y;
            } else if (o2Traits.get(1) != null){
                y2 = ((PositionTrait) o2Traits.get(1)).y;
            }

            if (y1 == y2) {
                return Integer.compare(o1.hashCode(), o2.hashCode());
            } else {
                return y1 > y2 ? -1 : 1;
            }
        }
    };

    public GameScreen(final Game game, Level level){

        AtlasManager.instance.loadAtlas("packed/game.atlas");
        AtlasManager.instance.loadAtlas("packed/game_n.atlas");

        music = SoundLibrary.GetMusic("Main_Song");
        music.setVolume(.5f);
        music.play();

        this.game = game;
        //
        camera = new FollowOrthoCamera(48, 27);
        camera.minZoom = 1f;
        camera.maxZoom = 1f;
        //
        listCollisions = new ArrayList<>();
        //
        world = new CWorld(camera);
        world.init(new Vector2(0, 0), true);
        world.debug(Conf.instance.isDebug());
        //
        mouse = CMouse.instance;
        keyboard = CKeyboard.instance;
        //
        batch = new SpriteBatch();
        program = createShader();
        batch.setShader(program);
        shaper = new ShapeRenderer();
        //
        final GameScreen self = this;
        TextButton btn = new TextButton("Back", SkinManager.instance.getSkin("menu-skin"), "button");
        btn.setPosition(50, 50);
        btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen(new LevelSelectScreen(game));
                self.endGame();

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
        for(GameObject g : gameObjects){
            List things = g.getTraits(InputHandlerTrait.class, AITrait.class);
            if (things.get(0) != null){
                AIHiveMind.addPlayer(g);
            }
            if (things.get(1)!= null){
                AIHiveMind.addEnemy(g);
            }
        }
    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(0.39f, 0.58f, 0.92f, 1);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        world.update(Conf.instance.worldStepTime());
        AIHiveMind.update();
        for (GameObject obj : gameObjects){
            List<Trait> traits = obj.getTraits(InputHandlerTrait.class, ControlTrait.class, PhysicalTrait.class, DebugTrait.class, UpdatableTrait.class, PathFollowerTrait.class, MoveDirectionTrait.class, ControlTraitEnemy.class);
            if (traits.get(0) != null) {
                ((InputHandlerTrait) traits.get(0)).update();
            }
            if (traits.get(1) != null) {
                ((ControlTrait) traits.get(1)).update();
            }
            if (traits.get(2) != null) {
                ((PhysicalTrait) traits.get(2)).step();
            }
            if (traits.get(3) != null) {
                ((DebugTrait) traits.get(3)).debug();
            }
            if (traits.get(4) != null) {
                ((UpdatableTrait) traits.get(4)).update();
            }
            if (traits.get(5) != null) {
                ((PathFollowerTrait) traits.get(5)).travelOnPath(delta);
            }
            if (traits.get(6) != null) {
                ((MoveDirectionTrait) traits.get(6)).travel(delta);
            }
            if(traits.get(7) != null){
                ((ControlTraitEnemy) traits.get(7)).update();
            }

            // handle deletion of objects gracefully
            if (obj.shouldBeDeleted()) {
                objsToDelete.add(obj);
            }
        }

        removeMarkedGameObjects();
        addObjectsToAdd();
       

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shaper.setProjectionMatrix(camera.combined);

        batch.begin();
        shaper.begin(ShapeRenderer.ShapeType.Line);

        Collections.sort(gameObjects, compare);

        int currentNumberOfLights = 0;
        for (GameObject obj : gameObjects){
            List<Trait> traits = obj.getTraits(AnimatorTrait.class, DrawableTrait.class, TimedCollisionTrait.class, CameraFollowTrait.class, CollidableTrait.class, LightTrait.class);
            if (traits.get(0) != null){
                ((AnimatorTrait) traits.get(0)).update(delta);
            }
            if (traits.get(1) != null){
                ((DrawableTrait) traits.get(1)).draw(batch);
            }
            if (traits.get(2) != null){
                ((TimedCollisionTrait) traits.get(2)).draw(shaper); // TODO: uncomment for debugging
            }
            if (traits.get(3) != null){
                ((CameraFollowTrait) traits.get(3)).updateCamera(camera);
            }
            if (traits.get(4) != null){
                ((CollidableTrait) traits.get(4)).checkCollisions(gameObjects, listCollisions);
            }
            if (traits.get(5) != null){
                if (currentNumberOfLights < MAX_LIGHTS) {
                    ((LightTrait) traits.get(5)).updateShaderProgram(program, currentNumberOfLights, camera).debug(shaper); // TODO: uncomment for debugging
                    currentNumberOfLights++;
                } else {
                    log.debug("currentNumberOfLights("+ (currentNumberOfLights + 1) + ") cannot exceed MAX_LIGHTS("+ MAX_LIGHTS + ")");
                }
            }
        }

        listCollisions.clear();
        batch.end();
        shaper.end();

        if (world.debug()){
            world.draw(); //TODO: uncomment for debugging
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
        objsToDelete = new ArrayList<>();
        for (GameObject obj : gameObjects){
            obj.markForDeletion();
            objsToDelete.add(obj);
        }
        removeMarkedGameObjects();
        world._world.dispose();
        //batch.dispose();
        //program.dispose();
        stage.dispose();

        music.stop();
        music.dispose();
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

    public static void addObject(GameObject object){
        objsToAdd.add(object);
    }

    public static void addObjects(List<GameObject> objects){
        objsToAdd.addAll(objects);
    }

    private void addObjectsToAdd(){
        gameObjects.addAll(objsToAdd);
        objsToAdd.clear();
    }

    private ShaderProgram createShader() {
        ShaderProgram program = new ShaderProgram(Gdx.files.internal("shaders/vertex.vert"), Gdx.files.internal("shaders/fragment.frag"));
        // u_proj and u_trans will not be active but SpriteBatch will still try to set them...
        program.pedantic = false;
        if (program.isCompiled() == false)
            throw new IllegalArgumentException("couldn't compile shader: "
                    + program.getLog());

        // we are only using this many uniforms for testing purposes...!!
        program.begin();
        program.setUniformi("u_texture", 0);
        program.setUniformi("u_normals", 1);
        program.setUniformf("strength", 1);
        program.setUniformf("ambientIntensity", 0.3f);
        program.setUniformf("ambientColor", new Vector3(0.3f, 0.6f, 1f));
        program.setUniformf("resolution", new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        program.setUniformf("lightColor", new Vector3(1f, 0.7f, 0.6f));
        program.setUniformf("attenuation", new Vector3(0f, 0f, 5f));
        program.setUniformi("useShadow", true ? 1 : 0);
        program.setUniformi("useNormals", true ? 1 : 0);
        program.setUniformi("yInvert", false ? 1 : 0);
        program.end();

        return program;
    }

    public void endGame(){
        Image fader = new Image(AtlasManager.instance.findRegion("white"));
        fader.setColor(Color.BLACK);
        fader.setBounds(-800, -800, Gdx.graphics.getWidth() + 1600, Gdx.graphics.getHeight() + 1600);
        fader.addAction(Actions.sequence(Actions.alpha(0),
                Actions.fadeIn(2.5f),
                Actions.delay(1.5f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(new MainMenuScreen(game));
                    }
                })));
        stage.addActor(fader);
    }

}
