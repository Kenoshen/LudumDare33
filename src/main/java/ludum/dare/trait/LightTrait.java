package ludum.dare.trait;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by mwingfield on 8/2/15.
 */
public class LightTrait extends Trait {
    private static final Color DEFAULT_AMBIENT_COLOR = new Color(0.1f, 0.1f, 0.1f, 1f);
    private static final Color DEFAULT_COLOR = new Color(1f, 1f, 1f, 1f);
    private static final Vector3 DEFAULT_ATTENUATION = new Vector3(0.5f, 0.5f, 15);
    private static final float DEFAULT_INTENSITY = 1f;
    private static final float DEFAULT_Z = 0.05f;

    private static Class[] REQUIRES = new Class[]{ PositionTrait.class };

    public PositionTrait pos;
    public Color ambientColor;
    public Color color;
    public Vector3 attenuation;
    public float intensity;
    public float z;


    public LightTrait(GameObject obj, Color color, Color ambientColor, float intensity, Vector3 attenuation){
        super(obj);
        this.color = color.cpy();
        this.ambientColor = ambientColor.cpy();
        this.intensity = intensity;
        this.attenuation = attenuation.cpy();
        this.z = DEFAULT_Z;
    }

    public LightTrait(GameObject obj, Color color, Color ambientColor, float intensity){
        this(obj, color, ambientColor, intensity, DEFAULT_ATTENUATION.cpy());
    }

    public LightTrait(GameObject obj, Color color, Color ambientColor){
        this(obj, color, ambientColor, DEFAULT_INTENSITY);
    }

    public LightTrait(GameObject obj, Color color){
        this(obj, color, DEFAULT_AMBIENT_COLOR.cpy());
    }

    public LightTrait(GameObject obj){
        this(obj, DEFAULT_COLOR.cpy());
    }

    public LightTrait setZ(float z){
        this.z = z;
        return this;
    }

    @Override
    public void initialize(){
        pos = self.getTrait(PositionTrait.class);
    }

    @Override
    public Class[] requires() {
        return REQUIRES;
    }

    public LightTrait updateShaderProgram(ShaderProgram program, int lightIndex, Camera cam)
    {
        //log.info("Light" + lightIndex + " " + new Vector3(pos.x, pos.y, pos.z + 0.05f));
        Vector3 p = new Vector3(pos.x, pos.y, pos.z);
        Vector3 pScreen = cam.project(p);
        pScreen.z += z;
        program.setUniformf("light" + lightIndex, pScreen);
        program.setUniformf("lightColor" + lightIndex, new Vector3(color.r, color.g, color.b));
        if (lightIndex == 0) {
//            program.setUniformf("ambientColor" + lightIndex, new Vector3(ambientColor.r, ambientColor.g, ambientColor.b));
            program.setUniformf("ambientColor" + lightIndex, new Vector3(DEFAULT_AMBIENT_COLOR.r, DEFAULT_AMBIENT_COLOR.g, DEFAULT_AMBIENT_COLOR.b));
        } else {
            program.setUniformf("ambientColor" + lightIndex, new Vector3(0, 0, 0));
        }
        program.setUniformf("intensity" + lightIndex, intensity);
        program.setUniformf("attenuation" + lightIndex, attenuation);
        return this;
    }

    public LightTrait debug(ShapeRenderer renderer){
        renderer.setColor(Color.WHITE);
        renderer.circle(pos.x, pos.y, 1);
        return this;
    }
}
