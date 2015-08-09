package ludum.dare;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.winger.log.HTMLLogger;
import com.winger.log.LogGroup;
import com.winger.log.LogLevel;
import com.winger.utils.GlobalClipboard;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class Application
{
    private static final HTMLLogger log = HTMLLogger.getLogger(Application.class, LogGroup.System);
    public static void main(String[] arg)
    {
        TexturePacker.process("./src/main/resources/imgs/ui/menu", "./src/main/resources/packed", "ui");
        TexturePacker.process("./src/main/resources/imgs/ui/background", "./src/main/resources/packed", "ui-background");
        TexturePacker.process("./src/main/resources/imgs/game", "./src/main/resources/packed", "game");
        //
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        //
        // //////////////////////////////////////
        // CONF
        config.width = Config.instance.screenWidth();
        config.height = Config.instance.screenHeight();
        boolean debug = Config.instance.isDebug();
        // //////////////////////////////////////
        //
        if (debug) {
            HTMLLogger.setGlobalLogLevel(LogLevel.Debug);
        } else {
            HTMLLogger.setGlobalLogLevel(LogLevel.Info);
        }
        log.debug("Application main() width: " + config.width + " height: " + config.height + " debug: " + debug);
        System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
        Game game = new Game(debug);
        com.badlogic.gdx.Application app = new LwjglApplication(game, config);
        game.app = app;
        GlobalClipboard.instance().setClipboard(app.getClipboard());
    }
}
