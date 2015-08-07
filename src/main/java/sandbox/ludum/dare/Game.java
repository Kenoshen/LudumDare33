package sandbox.ludum.dare;

import com.winger.Winger;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.state.KeyboardKey;
import com.winger.log.HTMLLogger;
import com.winger.log.LogGroup;
import ludum.dare.Config;
import sandbox.ludum.dare.screen.MainMenuScreen;

public class Game extends com.badlogic.gdx.Game
{
    private static final HTMLLogger log = HTMLLogger.getLogger(Game.class, LogGroup.System);
    com.badlogic.gdx.Application app;

    boolean debug;

    CKeyboard keyboard;

    public Game(boolean debug){
        super();
        this.debug = debug;
        log.debug("Game constructed (debug: " + debug + ")");
    }

    public Game(){this(false);}

    @Override
    public void create()
    {
        log.debug("Game create(" + Config.instance.version() + ")");
        keyboard = CKeyboard.instance;
        //
        Winger.texture.loadTextureAtlas("./src/main/resources/packed", "game");
        Winger.texture.loadTextureAtlas("./src/main/resources/packed", "ui");
        Winger.texture.loadTextureAtlas("./src/main/resources/packed", "ui-background");

        //setScreen(new SplashScreen(this));
        setScreen(new MainMenuScreen(this));
    }


    @Override
    public void render()
    {
        super.render();
        keyboard.update();

        if (keyboard.isKeyJustPressed(KeyboardKey.ESCAPE)){
            app.exit();
        }
    }


    @Override
    public void dispose()
    {
        log.debug("Game dispose()");
        super.dispose();
    }
}
