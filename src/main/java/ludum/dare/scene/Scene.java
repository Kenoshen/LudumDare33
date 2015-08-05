package ludum.dare.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import ludum.dare.trait.GameObject;

import java.io.File;
import java.util.List;

/**
 * Created by mwingfield on 8/3/15.
 */
public class Scene {
    private FileHandle fileHandle;

    public Scene(String fileLocation){
        // check the file location for a real file
        fileHandle = Gdx.files.internal(fileLocation);
        if (! fileHandle.exists()){
            throw new RuntimeException("File at " + fileLocation + " does not exist");
        } else if (!"scene".equals(fileHandle.extension())) {
            throw new RuntimeException("File at " + fileLocation + " must have extension: .scene");
        }
    }

    protected Scene(){
        // this is for test scenes or manually created scenes
    }

    public List<GameObject> loadScene(){
        // TODO: load some level file and parse into game objects and what ever other settings
        return null;
    }
}
