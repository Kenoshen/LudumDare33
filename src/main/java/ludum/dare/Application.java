package ludum.dare;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.winger.log.HTMLLogger;
import com.winger.log.LogGroup;
import com.winger.log.LogLevel;
import com.winger.utils.GlobalClipboard;

public class Application
{
    private static final HTMLLogger log = HTMLLogger.getLogger(Application.class, LogGroup.System);
    public static void main(String[] arg)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        //
        // //////////////////////////////////////
        // CONF
        config.width = 800;
        config.height = 450;
        boolean debug = true;
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
        GlobalClipboard.instance().setClipboard(app.getClipboard());
    }
}
