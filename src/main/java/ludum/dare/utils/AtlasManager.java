package ludum.dare.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mwingfield on 8/10/15.
 */
public class AtlasManager {

    public static final AtlasManager instance = new AtlasManager();

    public Map<String, TextureAtlas> atlases = new HashMap<>();

    private AtlasManager(){}

    public TextureAtlas getAtlas(String name){
        if (name != null && atlases.containsKey(name)){
            return atlases.get(name);
        }
        return null;
    }

    public void loadAtlas(String atlasLocation){
        loadAtlas(Gdx.files.internal(atlasLocation));
    }

    public void loadAtlas(FileHandle atlasLocation){
        loadAtlas(new TextureAtlas(atlasLocation), atlasLocation.nameWithoutExtension());
    }

    public void loadAtlas(TextureAtlas atlas, String name){
        if (name == null || "".equalsIgnoreCase(name)){
            throw new RuntimeException("Atlas cannot have a null or empty name");
        }
        atlases.put(name, atlas);
    }

    public TextureAtlas.AtlasRegion findRegion(String name){
        for (String atlasName : atlases.keySet()){
            TextureAtlas atlas = atlases.get(atlasName);
            TextureAtlas.AtlasRegion region = atlas.findRegion(name);
            if (region != null){
                return region;
            }
        }
        return null;
    }

    public TextureAtlas.AtlasRegion[] findRegions(String name){
        for (String atlasName : atlases.keySet()){
            TextureAtlas atlas = atlases.get(atlasName);
            Array<TextureAtlas.AtlasRegion> regions = atlas.findRegions(name);
            if (regions != null && regions.size > 0){
                return regions.toArray();
            }
        }
        return null;
    }
}