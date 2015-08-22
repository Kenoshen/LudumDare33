package ludum.dare;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class PackTextures
{
    public static void main(String[] arg)
    {
        System.out.println("Start packing textures");
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.filterMin = Texture.TextureFilter.Linear;
        settings.filterMag = Texture.TextureFilter.Linear;
        TexturePacker.process(settings, "src/main/resources/imgs/ui/menu", "src/main/resources/packed", "ui");
        TexturePacker.process(settings, "src/main/resources/imgs/ui/background", "src/main/resources/packed", "ui-background");
        TexturePacker.process(settings, "src/main/resources/imgs/game", "src/main/resources/packed", "game");
        System.out.println("Finished packing textures");
    }
}
