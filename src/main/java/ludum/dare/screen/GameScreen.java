package ludum.dare.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
import com.winger.struct.Tups;
import ludum.dare.Conf;
import ludum.dare.Game;
import ludum.dare.level.Level;
import ludum.dare.trait.*;
import ludum.dare.utils.SkinManager;
import ludum.dare.world.AIHiveMind;

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
    private ShapeRenderer shaper;

    private Music introMusic;

    List<Tups.Tup2<GameObject, GameObject>> listCollisions;

    public List<GameObject> gameObjects = new ArrayList<>();
    private List<GameObject> objsToDelete = new ArrayList<>();

    private AIHiveMind AIHM = new AIHiveMind();

    ShaderProgram program;

    public GameScreen(final Game game, Level level){

        if (level.name().equals("TestLevel3")){
            introMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Main_song.ogg"));
            introMusic.setVolume(1);
            introMusic.play();
        }

        this.game = game;
        //
        camera = new FollowOrthoCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = camera.minZoom;
        camera.minZoom = 1;
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
        Gdx.gl.glClearColor(0.39f, 0.58f, 0.92f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        CWorld.world.update(Conf.instance.worldStepTime());
        for (GameObject obj : gameObjects){
            List<Trait> traits = obj.getTraits(InputHandlerTrait.class, ControlTrait.class, PhysicalTrait.class, DebugTrait.class, UpdatableTrait.class);
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

            // handle deletion of objects gracefully
            if (obj.shouldBeDeleted()) {
                objsToDelete.add(obj);
            }
        }

        removeMarkedGameObjects();
        AIHiveMind.update();

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shaper.setProjectionMatrix(camera.combined);

        batch.begin();
        shaper.begin(ShapeRenderer.ShapeType.Line);
        program.setUniformf("light", new Vector3(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), 0.05f));
        for (GameObject obj : gameObjects){
            List<Trait> traits = obj.getTraits(AnimatorTrait.class, DrawableTrait.class, TimedCollisionTrait.class, CameraFollowTrait.class, CollidableTrait.class);
            if (traits.get(0) != null){
                ((AnimatorTrait) traits.get(0)).update(delta);
            }
            if (traits.get(1) != null){
                ((DrawableTrait) traits.get(1)).draw(batch);
            }
            if (traits.get(2) != null){
                ((TimedCollisionTrait) traits.get(2)).draw(shaper);
            }
            if (traits.get(3) != null){
                ((CameraFollowTrait) traits.get(3)).updateCamera(camera);
            }
            if (traits.get(4) != null){
                ((CollidableTrait) traits.get(4)).checkCollisions(gameObjects, listCollisions);
            }
        }

        listCollisions.clear();
        batch.end();
        shaper.end();

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

    private ShaderProgram createShader() {
        String vert = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
                + "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
                + "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
                + "uniform mat4 u_proj;\n" //
                + "uniform mat4 u_trans;\n" //
                + "uniform mat4 u_projTrans;\n" //
                + "varying vec4 v_color;\n" //
                + "varying vec2 v_texCoords;\n" //
                + "\n" //
                + "void main()\n" //
                + "{\n" //
                + "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
                + "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
                + "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
                + "}\n";

        String frag = "#ifdef GL_ES\n" +
                "precision mediump float;\n" +
                "#endif\n" +
                "varying vec4 v_color;\n" +
                "varying vec2 v_texCoords;\n" +

                "uniform sampler2D u_texture;\n" +
                "uniform sampler2D u_normals;\n" +
                "uniform vec3 light;\n" +
                "uniform vec3 ambientColor;\n" +
                "uniform float ambientIntensity; \n" +
                "uniform vec2 resolution;\n" +
                "uniform vec3 lightColor;\n" +
                "uniform bool useNormals;\n" +
                "uniform bool useShadow;\n" +
                "uniform vec3 attenuation;\n" +
                "uniform float strength;\n" +
                "uniform bool yInvert;\n"+
                "\n" +
                "void main() {\n" +
                "       //sample color & normals from our textures\n" +
                "       vec4 color = texture2D(u_texture, v_texCoords.st);\n" +
                "       vec3 nColor = texture2D(u_normals, v_texCoords.st).rgb;\n\n" +
                "       //some bump map programs will need the Y value flipped..\n" +
                "       nColor.g = yInvert ? 1.0 - nColor.g : nColor.g;\n\n" +
                "       //this is for debugging purposes, allowing us to lower the intensity of our bump map\n" +
                "       vec3 nBase = vec3(0.5, 0.5, 1.0);\n" +
                "       nColor = mix(nBase, nColor, strength);\n\n" +
                "       //normals need to be converted to [-1.0, 1.0] range and normalized\n" +
                "       vec3 normal = normalize(nColor * 2.0 - 1.0);\n\n" +
                "       //here we do a simple distance calculation\n" +
                "       vec3 deltaPos = vec3( (light.xy - gl_FragCoord.xy) / resolution.xy, light.z );\n\n" +
                "       vec3 lightDir = normalize(deltaPos);\n" +
                "       float lambert = useNormals ? clamp(dot(normal, lightDir), 0.0, 1.0) : 1.0;\n" +
                "       \n" +
                "       //now let's get a nice little falloff\n" +
                "       float d = sqrt(dot(deltaPos, deltaPos));"+
                "       \n" +
                "       float att = useShadow ? 1.0 / ( attenuation.x + (attenuation.y*d) + (attenuation.z*d*d) ) : 1.0;\n" +
                "       \n" +
                "       vec3 result = (ambientColor * ambientIntensity) + (lightColor.rgb * lambert) * att;\n" +
                "       result *= color.rgb;\n" +
                "       \n" +
                "       gl_FragColor = v_color * vec4(result, color.a);\n" +
                "}";
        System.out.println("VERTEX PROGRAM:\n------------\n\n"+vert);
        System.out.println("FRAGMENT PROGRAM:\n------------\n\n"+frag);
        ShaderProgram program = new ShaderProgram(vert, frag);
        // u_proj and u_trans will not be active but SpriteBatch will still try to set them...
        program.pedantic = false;
        if (program.isCompiled() == false)
            throw new IllegalArgumentException("couldn't compile shader: "
                    + program.getLog());

        // we are only using this many uniforms for testing purposes...!!
        program.begin();
        program.setUniformi("u_texture", 0);
        program.setUniformi("u_normals", 1);
        program.setUniformf("light", new Vector3(0, 0, 0.001f));
        program.setUniformf("strength", 1);
        program.setUniformf("ambientIntensity", 0.3f);
        program.setUniformf("ambientColor", new Vector3(0.3f, 0.3f, 1f));
        program.setUniformf("resolution", new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        program.setUniformf("lightColor", new Vector3(1f, 0.7f, 0.6f));
        program.setUniformf("attenuation", new Vector3(0.4f, 3f, 20f));
        program.setUniformi("useShadow", true ? 1 : 0);
        program.setUniformi("useNormals", true ? 1 : 0);
        program.setUniformi("yInvert", false ? 1 : 0);
        program.end();

        return program;
    }
}
