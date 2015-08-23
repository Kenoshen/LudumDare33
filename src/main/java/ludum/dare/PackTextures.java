package ludum.dare;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class PackTextures
{
    public static void main(String[] arg)
    {
        System.out.println("Start packing textures");
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxWidth *= 2;
        settings.maxHeight *= 2;
        settings.filterMin = Texture.TextureFilter.Linear;
        settings.filterMag = Texture.TextureFilter.Linear;
        TexturePacker.process(settings, "src/main/resources/imgs/ui/menu", "src/main/resources/packed", "ui");
        TexturePacker.process(settings, "src/main/resources/imgs/ui/background", "src/main/resources/packed", "ui-background");
        TexturePacker.process(settings, "src/main/resources/imgs/game/collectables", "src/main/resources/packed", "collectables");
        TexturePacker.process(settings, "src/main/resources/imgs/game/bum", "src/main/resources/packed", "bum");
        TexturePacker.process(settings, "src/main/resources/imgs/game/bum_n", "src/main/resources/packed", "bum_n");
        TexturePacker.process(settings, "src/main/resources/imgs/game/bot", "src/main/resources/packed", "bot");
        TexturePacker.process(settings, "src/main/resources/imgs/game/bot_n", "src/main/resources/packed", "bot_n");
        TexturePacker.process(settings, "src/main/resources/imgs/game/misc", "src/main/resources/packed", "misc");
        TexturePacker.process(settings, "src/main/resources/imgs/game/spark", "src/main/resources/packed", "spark");
        System.out.println("Finished packing textures");
    }
}
