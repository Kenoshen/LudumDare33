package ludum.dare.trait;

import com.badlogic.gdx.math.Vector2;
import com.winger.input.delegate.CGamePadEventHandler;
import com.winger.input.delegate.CKeyboardEventHandler;
import com.winger.input.delegate.CMouseEventHandler;
import com.winger.input.raw.CGamePad;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.input.raw.state.*;
import com.winger.physics.body.PlayerBody;
import ludum.dare.Conf;

/**
 * Created by mwingfield on 8/2/15.
 */
public class ControlTrait extends Trait implements CMouseEventHandler, CKeyboardEventHandler, CGamePadEventHandler {
    private static Class[] REQUIRES = new Class[]{ PhysicalTrait.class };

    private CMouse mouse;
    private CKeyboard keyboard;
    private CGamePad gamepad;

    private PhysicalTrait physical;
    private PlayerBody player;


    public ControlTrait(GameObject obj, CMouse mouse, CKeyboard keyboard) {
        this(obj, mouse, keyboard, null);
    }

    public ControlTrait(GameObject obj, CGamePad gamepad) {
        this(obj, null, null, gamepad);
    }

    public ControlTrait(GameObject obj, CMouse mouse, CKeyboard keyboard, CGamePad gamepad) {
        super(obj);
        this.mouse = mouse;
        this.keyboard = keyboard;
        this.gamepad = gamepad;
    }

    @Override
    public void initialize(){
        super.initialize();
        physical = self.getTrait(PhysicalTrait.class);
        if (!(physical.body instanceof PlayerBody)){
            throw new RuntimeException("ControlTrait requires PhysicalTrait, but it also requires a PlayerBody for the physicalTrait.body");
        }
        player = (PlayerBody)physical.body;

        if (mouse != null){
            mouse.subscribeToAllMouseClickEvents(this);
        }
        if (keyboard != null){
            keyboard.subscribeToAllKeyboardEvents(this, ButtonState.UP);
            keyboard.subscribeToAllKeyboardEvents(this, ButtonState.DOWN);
        }
        if (gamepad != null){
            gamepad.subscribeToAllGamePadEvents(this);
        }
    }


    @Override
    public Class[] requires() {
        return REQUIRES;
    }


    public void update(){
        if (keyboard != null){
            if (keyboard.isKeyBeingPressed(KeyboardKey.LEFT) || keyboard.isKeyBeingPressed(KeyboardKey.A)){
                if (keyboard.isKeyBeingPressed(KeyboardKey.LEFT_SHIFT) || keyboard.isKeyBeingPressed(KeyboardKey.RIGHT_SHIFT)){
                    player.run(-Conf.instance.playerRunSpeed());
                } else {
                    player.walk(-Conf.instance.playerWalkSpeed());
                }
            } else if (keyboard.isKeyBeingPressed(KeyboardKey.RIGHT) || keyboard.isKeyBeingPressed(KeyboardKey.D)){
                if (keyboard.isKeyBeingPressed(KeyboardKey.LEFT_SHIFT) || keyboard.isKeyBeingPressed(KeyboardKey.RIGHT_SHIFT)){
                    player.run(Conf.instance.playerRunSpeed());
                } else {
                    player.walk(Conf.instance.playerWalkSpeed());
                }
            }
        }
    }


    @Override
    public void handleButtonEvent(CGamePad gamePad, CGamePadButton button, ButtonState state) {

    }

    @Override
    public void handleTriggerEvent(CGamePad gamePad, CGamePadTrigger trigger, float state) {

    }

    @Override
    public void handleStickEvent(CGamePad gamePad, CGamePadStick stick, Vector2 state) {

    }

    @Override
    public void handleKeyEvent(CKeyboard keyboard, KeyboardKey key, ButtonState state) {
        switch(state){
            case DOWN:
                if (key == KeyboardKey.UP || key == KeyboardKey.W || key == KeyboardKey.SPACE){
                    player.jump(Conf.instance.playerJumpForce());
                }
                break;
            case UP:
                break;
        }
    }

    @Override
    public void handleClickEvent(CMouse mouse, CMouseButton button, ButtonState state) {

    }

    @Override
    public void handleScrollEvent(CMouse mouse, float difference) {

    }

    @Override
    public void handleMoveEvent(CMouse mouse, Vector2 position) {

    }

    @Override
    public void handleDragEvent(CMouse mouse, CMouseButton button, CMouseDrag state) {

    }
}
