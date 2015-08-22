package ludum.dare;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.winger.log.HTMLLogger;
import com.winger.log.LogGroup;
import com.winger.utils.GlobalClipboard;

public class Application
{
    private static final HTMLLogger log = HTMLLogger.getLogger(Application.class, LogGroup.System);
    public static void main(String[] arg)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "HOBO BALLS IN MY FACE";
        config.foregroundFPS = 60;
        config.backgroundFPS = 0;
        config.resizable = false;
        config.width = 800;
        config.height = 450;
        config.addIcon("imgs/icons/icon_128.png", Files.FileType.Internal);
        config.addIcon("imgs/icons/icon_32.png", Files.FileType.Internal);
        config.addIcon("imgs/icons/icon_16.png", Files.FileType.Internal);
        System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
        Game game = new Game();
        com.badlogic.gdx.Application app = new LwjglApplication(game, config);
        game.app = app;
        GlobalClipboard.instance().setClipboard(app.getClipboard());
    }
}
