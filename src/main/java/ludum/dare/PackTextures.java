package ludum.dare;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class PackTextures
{
    public static void main(String[] arg)
    {
        System.out.println("Start packing textures");
        TexturePacker.process("src/main/resources/imgs/ui/menu", "src/main/resources/packed", "ui");
        TexturePacker.process("src/main/resources/imgs/ui/background", "src/main/resources/packed", "ui-background");
        TexturePacker.process("src/main/resources/imgs/game", "src/main/resources/packed", "game");
        System.out.println("Finished packing textures");
    }
}
