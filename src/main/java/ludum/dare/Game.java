package ludum.dare;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.winger.Winger;
import com.winger.draw.texture.CSpriteBatch;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.input.raw.state.KeyboardKey;
import com.winger.log.HTMLLogger;
import com.winger.log.LogGroup;
import com.winger.log.LogLevel;
import com.winger.physics.CWorld;
import com.winger.stats.FPSCalculator;

public class Game extends ApplicationAdapter
{
    private static final HTMLLogger log = HTMLLogger.getLogger(Game.class, LogGroup.System);
    com.badlogic.gdx.Application app;
    CSpriteBatch batch;
    CSpriteBatch uiBatch;
    CWorld world;
    OrthographicCamera camera;
    CMouse mouse;
    CKeyboard keyboard;
    FPSCalculator fps;

    Director director;

    boolean debug = false;

    public Game(boolean debug){
        super();
        this.debug = debug;
        log.debug("Game constructed (debug: " + debug + ")");
    }

    public Game(){this(false);}

    @Override
    public void create()
    {
        super.create();
        log.debug("Game create(" + Config.instance.version() + ")");
        //
        batch = new CSpriteBatch();
        uiBatch = new CSpriteBatch();

        Winger.texture.loadTexturesInDirectory("src/main/resources/imgs");
        //Winger.texture.loadTextureAtlas("src/main/resources/atlas", "atlas");
        Winger.ui.addPagesInDirectory("src/main/resources/pages");
        //
        Winger.ui.setUISpriteBatch(uiBatch);
        //
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 0.1f;
        batch.setCamera(camera);
        //
        world = new CWorld(camera);
        world.init(new Vector2(0, -30), true);
        world.debug(debug);
        //
        mouse = CMouse.instance;
        keyboard = CKeyboard.instance;
        //
        director = new Director(mouse, keyboard);
        director.initialize();
        //
        fps = new FPSCalculator();
        fps.framesToTrack(120);
        fps.color(Color.WHITE.cpy());
    }


    @Override
    public void render()
    {
        super.render();

        // update
        fps.update();
        mouse.update();
        keyboard.update();
        Winger.ui.update();
        director.update();

        // draw
        Gdx.gl.glClearColor(0.39f, 0.58f, 0.92f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // in game drawing
        camera.update();
        batch.begin();
        director.draw(batch);
        batch.end();

        // ui drawing
        uiBatch.begin();
        Winger.ui.draw();
        fps.displayFps(uiBatch, 50, Gdx.graphics.getHeight() - 50);
        uiBatch.end();


        if (keyboard.isKeyJustPressed(KeyboardKey.ESCAPE)){
            app.exit();
        }
    }


    @Override
    public void dispose()
    {
        log.debug("Game dispose()");
        world._world.dispose();
        super.dispose();
    }
}
