package sandbox.ludum.dare;

/**
 * Created by mwingfield on 8/4/15.
 */
public class Config extends com.winger.utils.Config {
    public static final Config instance = new Config();

    private Config(){
        super(true);
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

    public float playerJumpForce(){
        return get("player.jumpForce", Double.class).floatValue();
    }

    public float playerWalkSpeed(){
        return get("player.walkSpeed", Double.class).floatValue();
    }

    public float playerRunSpeed(){
        return get("player.runSpeed", Double.class).floatValue();
    }
}
