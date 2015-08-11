package ludum.dare;

import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.input.raw.state.KeyboardKey;
import com.winger.log.HTMLLogger;
import com.winger.log.LogGroup;
import ludum.dare.level.TestLevel2;
import ludum.dare.screen.GameScreen;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.SkinManager;

public class Game extends com.badlogic.gdx.Game
{
    private static final HTMLLogger log = HTMLLogger.getLogger(Game.class, LogGroup.System);
    com.badlogic.gdx.Application app;

    boolean debug;

    CKeyboard keyboard;
    CMouse mouse;

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
        mouse = CMouse.instance;
        //
        AtlasManager.instance.loadAtlas("./src/main/resources/packed/game.atlas");
        AtlasManager.instance.loadAtlas("./src/main/resources/packed/ui.atlas");
        AtlasManager.instance.loadAtlas("./src/main/resources/packed/ui-background.atlas");
        SkinManager.instance.loadSkin("src/main/resources/skins/menu-skin.json", "ui");
        //
        //setScreen(new SplashScreen(this));
        //setScreen(new MainMenuScreen(this));
        setScreen(new GameScreen(this, new TestLevel2()));
    }


    @Override
    public void render()
    {
        super.render();
        keyboard.update();
        mouse.update();

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
