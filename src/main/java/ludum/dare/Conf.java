package ludum.dare;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by mwingfield on 8/4/15.
 */
public class Conf extends com.winger.utils.Config {
    public static final Conf instance = new Conf();
    private boolean standalone = false;

    private Conf(){
        super();

        parse(Gdx.files.internal("conf/project.config"));
    }

    private void recurse(FileHandle dir){
        if (dir.exists()){
            FileHandle[] list = dir.list();
            for (FileHandle fh : list){
                if (fh.isDirectory()){
                    recurse(fh);
                } else if ("project.config".equals(fh.name())){
                    parse(fh);
                    break;
                }
            }
        }
    }

    public String version(){
        return get("version");
    }

    public int screenWidth(){
        return get("game.screen.width", Double.class).intValue();
    }

    public int screenHeight(){
        return get("game.screen.height", Double.class).intValue();
    }

    public boolean isDebug(){
        return get("game.debug", Boolean.class);
    }

    public boolean isStandalone(){
        return standalone;
    }

    public float playerJumpForce(){
        return get("player.jumpForce", Double.class).floatValue();
    }

    public float playerWalkSpeed(){
        return get("player.walkSpeed", Double.class).floatValue();
    }

    public float playerRunSpeed(){
        return get("player.runSpeed", Double.class).floatValue();
    }

    public float worldStepTime(){
        return get("world.step.time", Double.class).floatValue();
    }
}
