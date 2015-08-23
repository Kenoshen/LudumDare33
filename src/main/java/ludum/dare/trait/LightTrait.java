package ludum.dare.trait;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by mwingfield on 8/2/15.
 */
public class LightTrait extends Trait {
    private static Class[] REQUIRES = new Class[]{ PositionTrait.class };

    public PositionTrait pos;
    public Color ambientColor = new Color(0.3f, 0.3f, 1f, 1f);
    public Color color = new Color(1f, 0.7f, 0.6f, 1f);
    public Vector3 attenuation = new Vector3(0.4f, 3f, 20f);
    public float intensity = 0.3f;

    public LightTrait(GameObject obj){
        super(obj);
    }

    public LightTrait(GameObject obj, Color color){
        this(obj);
        this.color = color.cpy();
    }

    @Override
    public void initialize(){
        pos = self.getTrait(PositionTrait.class);
    }

    @Override
    public Class[] requires() {
        return REQUIRES;
    }

    public void updateShaderProgram(ShaderProgram program, int lightIndex, Camera cam)
    {
        //log.info("Light" + lightIndex + " " + new Vector3(pos.x, pos.y, pos.z + 0.05f));
        Vector3 p = new Vector3(pos.x, pos.y, pos.z);
        Vector3 pScreen = cam.project(p);
        pScreen.z += 0.05f;
        program.setUniformf("light" + lightIndex, pScreen);
        program.setUniformf("lightColor" + lightIndex, new Vector3(color.r, color.g, color.b));
    }
}
