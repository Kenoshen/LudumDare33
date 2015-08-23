package ludum.dare;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.input.raw.state.KeyboardKey;
import com.winger.log.HTMLLogger;
import com.winger.log.LogGroup;
import com.winger.log.LogLevel;
import ludum.dare.level.TestLevel;
import ludum.dare.level.TestLevel2;
import ludum.dare.level.TestLevel;
import ludum.dare.level.TestLevel2;
import ludum.dare.level.TestLevel3;
import ludum.dare.screen.CutsceneScreen;
import ludum.dare.screen.GameScreen;
import ludum.dare.screen.GameScreen;
import ludum.dare.screen.MainMenuScreen;
import ludum.dare.screen.SplashScreen;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.SkinManager;

public class Game extends com.badlogic.gdx.Game {
    private static final HTMLLogger log = HTMLLogger.getLogger(Game.class, LogGroup.System);
    com.badlogic.gdx.Application app;

    boolean debug;

    CKeyboard keyboard;
    CMouse mouse;

    public Game() {
        super();
    }

    @Override
    public void create() {
        debug = Conf.instance.isDebug();
        log.debug("Game created (debug: " + debug + ")");
        //
        if (debug) {
            HTMLLogger.setGlobalLogLevel(LogLevel.Debug);
        } else {
            HTMLLogger.setGlobalLogLevel(LogLevel.Info);
        }
        log.debug("Game created (" + Conf.instance.version() + ":" + (debug ? "debug" : "prod") + ")");
        keyboard = CKeyboard.instance;
        mouse = CMouse.instance;
        //
        AtlasManager.instance.loadAtlas("packed/ui.atlas");
        AtlasManager.instance.loadAtlas("packed/ui-background.atlas");
        //
        SkinManager.instance.loadSkin("skins/menu-skin.json", "ui");
        //
        AtlasManager.instance.loadAtlas("packed/misc.atlas");

        AtlasManager.instance.loadAtlas("packed/bum.atlas");
        AtlasManager.instance.loadAtlas("packed/bum_n.atlas");
        AtlasManager.instance.loadAtlas("packed/bot.atlas");
        AtlasManager.instance.loadAtlas("packed/bot_n.atlas");
        AtlasManager.instance.loadAtlas("packed/spark.atlas");
        //
        //setScreen(new CutsceneScreen(this));
//        setScreen(new CutsceneScreen(this));
        //setScreen(new GameScreen(this, new TestLevel3()));
//        setScreen(new MainMenuScreen(this));
        setScreen(new GameScreen(this, new ludum.dare.level.TestLamp()));
    }


    @Override
    public void render() {
        super.render();
        keyboard.update();
        mouse.update();

        if (keyboard.isKeyJustPressed(KeyboardKey.ESCAPE)) {
            app.exit();
        }
    }


    @Override
    public void dispose() {
        log.debug("Game dispose()");
        super.dispose();
    }
}
