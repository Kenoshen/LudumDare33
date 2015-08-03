package ludum.dare.scene;

import ludum.dare.trait.GameObject;

import java.util.List;

/**
 * Created by mwingfield on 8/3/15.
 */
public class Scene {

    public Scene(String fileLocation){
        // TODO: check the file location for a real file
    }

    protected Scene(){
        // this is for test scenes or manually created scenes
    }

    public List<GameObject> loadScene(){
        // TODO: load some level file and parse into game objects and what ever other settings
        return null;
    }
}
