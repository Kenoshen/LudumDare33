package ludum.dare;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.input.raw.state.KeyboardKey;
import com.winger.log.HTMLLogger;
import com.winger.log.LogGroup;
import com.winger.log.LogLevel;
import ludum.dare.level.TestLevel2;
import ludum.dare.screen.CutsceneScreen;
import ludum.dare.screen.GameScreen;
import ludum.dare.screen.SplashScreen;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.SkinManager;

public class Game extends com.badlogic.gdx.Game
{
    private static final HTMLLogger log = HTMLLogger.getLogger(Game.class, LogGroup.System);
    com.badlogic.gdx.Application app;

    boolean debug;

    CKeyboard keyboard;
    CMouse mouse;

    public Game(){
        super();
    }

    @Override
    public void create()
    {
        debug = Conf.instance.isDebug();
        log.debug("Game created (debug: " + debug + ")");
        //
        if (debug) {
            HTMLLogger.setGlobalLogLevel(LogLevel.Debug);
        } else {
            HTMLLogger.setGlobalLogLevel(LogLevel.Info);
        }
        //
        // should only run if you are running from the IDE, not the JAR
        if (Gdx.files.internal("src/main/resources/").exists()) {
            TexturePacker.process("src/main/resources/imgs/ui/menu", "src/main/resources/packed", "ui");
            TexturePacker.process("src/main/resources/imgs/ui/background", "src/main/resources/packed", "ui-background");
            TexturePacker.process("src/main/resources/imgs/game", "src/main/resources/packed", "game");
        }
        //
        log.debug("Game create(" + Conf.instance.version() + ")");
        keyboard = CKeyboard.instance;
        mouse = CMouse.instance;
        //
        AtlasManager.instance.loadAtlas("packed/game.atlas");
        AtlasManager.instance.loadAtlas("packed/ui.atlas");
        AtlasManager.instance.loadAtlas("packed/ui-background.atlas");
        SkinManager.instance.loadSkin("skins/menu-skin.json", "ui");
        //
        //setScreen(new MainMenuScreen(this));
        setScreen(new GameScreen(this, new TestLevel()));
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
