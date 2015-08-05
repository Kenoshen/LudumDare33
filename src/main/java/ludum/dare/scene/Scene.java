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
        this(Gdx.files.internal(fileLocation));
    }

    public Scene(FileHandle fileHandle){
        // check the file location for a real file
        this.fileHandle = fileHandle;
        if (! fileHandle.exists()){
            throw new RuntimeException("File at " + fileHandle.path() + " does not exist");
        } else if (!"scene".equals(fileHandle.extension())) {
            throw new RuntimeException("File at " + fileHandle.path() + " must have extension: .scene");
        }
    }

    protected Scene(){
        // this is for test scenes or manually created scenes
    }

    public String name(){
        return fileHandle.name();
    }

    public List<GameObject> loadScene(){
        // TODO: load some level file and parse into game objects and what ever other settings
        return null;
    }
}
