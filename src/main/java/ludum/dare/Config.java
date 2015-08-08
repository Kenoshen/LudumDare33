package ludum.dare;

/**
 * Created by mwingfield on 8/4/15.
 */
public class Config extends com.winger.utils.Config {
    public static final Config instance = new Config();

    private Config(){
        super("./src/main/resources/conf/project.config", true);
    }

    public String version(){
        return get("version");
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
