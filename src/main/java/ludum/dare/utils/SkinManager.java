package ludum.dare.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mwingfield on 8/10/15.
 */
public class SkinManager {

    public static final SkinManager instance = new SkinManager();

    public Map<String, Skin> skins = new HashMap<>();

    private SkinManager(){}

    public Skin getSkin(String name){
        if (name != null && skins.containsKey(name)){
            return skins.get(name);
        }
        return null;
    }

    public void loadSkin(String skinLocation, String atlasName){
        loadSkin(Gdx.files.internal(skinLocation), atlasName);
    }

    public void loadSkin(FileHandle skinLocation, String atlasName){
        skins.put(skinLocation.nameWithoutExtension(), new Skin(skinLocation, AtlasManager.instance.getAtlas(atlasName)));
    }

    public void loadSkin(Skin skin, String name){
        if (name == null || "".equalsIgnoreCase(name)){
            throw new RuntimeException("Skin cannot have a null or empty name");
        }
        skins.put(name, skin);
    }
}
