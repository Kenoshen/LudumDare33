package ludum.dare;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.winger.utils.GlobalClipboard;

public class Application
{
    // another test comment
    public static void main(String[] arg)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 450;
        System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
        Game game = new Game();
        com.badlogic.gdx.Application app = new LwjglApplication(game, config);
        GlobalClipboard.instance().setClipboard(app.getClipboard());
    }
}
