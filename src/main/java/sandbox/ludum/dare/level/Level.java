package sandbox.ludum.dare.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import sandbox.ludum.dare.trait.GameObject;

import java.util.List;

/**
 * Created by mwingfield on 8/6/15.
 */
public class Level {
    private FileHandle fileHandle;

    public Level(String fileLocation){
        this(Gdx.files.internal(fileLocation));
    }

    public Level(FileHandle fileHandle){
        // check the file location for a real file
        this.fileHandle = fileHandle;
        if (! fileHandle.exists()){
            throw new RuntimeException("File at " + fileHandle.path() + " does not exist");
        } else if (!"lvl".equals(fileHandle.extension())) {
            throw new RuntimeException("File at " + fileHandle.path() + " must have extension: .lvl");
        }
    }

    protected Level(){
        // this is for test scenes or manually created scenes
    }

    public String name(){
        return fileHandle.name();
    }

    public List<GameObject> loadLevel(){
        // TODO: load some level file and parse into game objects and what ever other settings
        return null;
    }
}
