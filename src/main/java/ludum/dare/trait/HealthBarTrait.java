package ludum.dare.trait;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.winger.camera.FollowOrthoCamera;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.Sprite;

/**
 * Created by Admin on 8/24/2015.
 */
public class HealthBarTrait extends Trait{

    private Vector3 bottomLeft = new Vector3(0,Gdx.graphics.getHeight(),0);
    private Vector3 bottomRight = new Vector3(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),0);

    Sprite fourty;
    Sprite blackbar;
    float fourtyWidth = 1;


    private HealthTrait health;

    public HealthBarTrait(GameObject obj) {
        super(obj);
    }

    @Override
    public Class[] requires() {
        return new Class[]{HealthTrait.class};
    }

    @Override
    public void initialize() {
        super.initialize();
        health = self.getTrait(HealthTrait.class);
        fourty = new Sprite(AtlasManager.instance.findRegions("collectables/40brown").first());
        fourty.setSize(fourtyWidth, fourtyWidth*2);

        blackbar = new Sprite(AtlasManager.instance.findRegion("white"));
        blackbar.setColor(Color.BLACK);
    }

    public void draw(FollowOrthoCamera cam, SpriteBatch batch) {
        Vector3 screenBotLeft = cam.unproject(bottomLeft.cpy());
        Vector3 screenBotRight = cam.unproject(bottomRight.cpy());


        int width = (int)(screenBotRight.x - screenBotLeft.x);

        blackbar.setSize(width*1.5f, 3);
        blackbar.setPosition(screenBotLeft.x-width*.25f, screenBotLeft.y-.5f);

        blackbar.draw(batch);

        int count = (int)(width / fourtyWidth);

        float percent = 1f * health.health / health.maxHealth;

        int toDisplay = (int)(count * percent);

        for(int i = 0; i < toDisplay; i++) {
            fourty.setPosition(screenBotLeft.x + i * fourtyWidth, screenBotLeft.y);
            fourty.draw(batch);
        }
    }
}
