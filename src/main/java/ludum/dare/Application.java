package ludum.dare;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.winger.log.HTMLLogger;
import com.winger.log.LogGroup;
import com.winger.utils.GlobalClipboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application
{
    private static final HTMLLogger log = HTMLLogger.getLogger(Application.class, LogGroup.System);
    public static void main(String[] arg)
    {
        System.out.println(Arrays.toString(arg));
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Scabs";
        config.foregroundFPS = 60;
        config.backgroundFPS = 0;
        config.resizable = false;
        config.width = 1280;
        config.height = 720;
        List<String> highDef = new ArrayList<>();
        highDef.add("high-def");
        highDef.add("hi-def");
        highDef.add("highdef");
        highDef.add("hidef");
        highDef.add("high-res");
        highDef.add("hi-res");
        highDef.add("highres");
        highDef.add("hires");
        highDef.add("hd");
        List<String> lowDef = new ArrayList<>();
        lowDef.add("low-def");
        lowDef.add("lowdef");
        lowDef.add("low-res");
        lowDef.add("lowres");
        lowDef.add("ld");
        lowDef.add("sd");
        List<String> noShader = new ArrayList<>();
        noShader.add("no-shader");
        noShader.add("noshader");
        noShader.add("shader-off");
        noShader.add("shaderoff");
        noShader.add("bad-graphics");
        noShader.add("badgraphics");
        boolean useAwesomeShader = true;
        for (String argument : arg){
            if (argument != null) {
                String lowCase = argument.toLowerCase();
                if (highDef.contains(lowCase)){
                    config.width = 1600;
                    config.height = 900;
                } else if (lowDef.contains(lowCase)){
                    config.width = 800;
                    config.height = 450;
                } else if (noShader.contains(lowCase)){
                    useAwesomeShader = false;
                }
            }
        }
        config.addIcon("imgs/icons/icon_128.png", Files.FileType.Internal);
        config.addIcon("imgs/icons/icon_32.png", Files.FileType.Internal);
        config.addIcon("imgs/icons/icon_16.png", Files.FileType.Internal);
        System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
        Game game = new Game(useAwesomeShader);
        com.badlogic.gdx.Application app = new LwjglApplication(game, config);
        game.app = app;
        GlobalClipboard.instance().setClipboard(app.getClipboard());
    }
}
