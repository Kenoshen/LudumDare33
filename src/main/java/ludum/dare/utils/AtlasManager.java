package ludum.dare.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.winger.log.HTMLLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mwingfield on 8/10/15.
 */
public class AtlasManager {

    private static final HTMLLogger log = HTMLLogger.getLogger(HTMLLogger.class);

    public static final AtlasManager instance = new AtlasManager();
    public static List<String> loadedAtlases = new ArrayList<>();

    public static final AssetManager manager = new AssetManager();

    public Map<String, TextureAtlas> atlases = new HashMap<>();

    private boolean doneLoading = false;

    private AtlasManager(){}

    public TextureAtlas getAtlas(String name){
        if (name != null && atlases.containsKey(name)){
            return atlases.get(name);
        }
        throw new RuntimeException("Atlas by name:" + name + " does not exist, check game.java to load atlas.");
    }

    public void loadAtlasAsynch(String atlastLocation) {
        doneLoading = false;
        loadedAtlases.add(atlastLocation);
        manager.load(atlastLocation, TextureAtlas.class);
    }

    public void update() {
        if (manager.update() && !doneLoading) {
            doneLoading = true;
            log.info("done loading assets");
        }
    }

    public void finishLoading() {
        long timer = System.currentTimeMillis();
        manager.finishLoading();
        timer = System.currentTimeMillis();
        for (String load : loadedAtlases) {
            loadAtlas(manager.get(load, TextureAtlas.class), load);
        }
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
        log.info("Load Atlas: " + name);
    }

    public TextureAtlas.AtlasRegion findRegion(String name){
        for (String atlasName : atlases.keySet()){
            TextureAtlas atlas = atlases.get(atlasName);
            TextureAtlas.AtlasRegion region = atlas.findRegion(name);
            if (region != null){
                return region;
            }
        }
        throw new RuntimeException("Could not find texture region with name: " + name);
    }

    public Array<TextureAtlas.AtlasRegion> findRegions(String name){
        for (String atlasName : atlases.keySet()){
            TextureAtlas atlas = atlases.get(atlasName);
            Array<TextureAtlas.AtlasRegion> regions = atlas.findRegions(name);
            if (regions != null && regions.size > 0){
                return regions;
            }
        }
        throw new RuntimeException("Could not find texture regions with name: " + name);
    }
}
