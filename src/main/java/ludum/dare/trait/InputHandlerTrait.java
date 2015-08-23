package ludum.dare.trait;

import com.badlogic.gdx.math.Vector2;
import com.winger.input.delegate.CGamePadEventHandler;
import com.winger.input.delegate.CKeyboardEventHandler;
import com.winger.input.delegate.CMouseEventHandler;
import com.winger.input.raw.CGamePad;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.input.raw.state.*;
import ludum.dare.Conf;

import javax.naming.ldap.Control;

import static ludum.dare.trait.ControlTrait.ControlAction;

/**
 * Created by mwingfield on 8/2/15.
 */
public class InputHandlerTrait extends Trait implements CMouseEventHandler, CKeyboardEventHandler, CGamePadEventHandler {
    private static Class[] REQUIRES = new Class[]{ ControlTrait.class };

    private CMouse mouse;
    private CKeyboard keyboard;
    private CGamePad gamepad;

    private ControlTrait control;


    public InputHandlerTrait(GameObject obj, CMouse mouse, CKeyboard keyboard) {
        this(obj, mouse, keyboard, null);
    }

    public InputHandlerTrait(GameObject obj, CGamePad gamepad) {
        this(obj, null, null, gamepad);
    }

    public InputHandlerTrait(GameObject obj, CMouse mouse, CKeyboard keyboard, CGamePad gamepad) {
        super(obj);
        this.mouse = mouse;
        this.keyboard = keyboard;
        this.gamepad = gamepad;
    }

    @Override
    public void initialize(){
        super.initialize();
        control = self.getTrait(ControlTrait.class);
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
            if (keyboard.isKeyJustPressed(KeyboardKey.P)) {
                control.requestMove(ControlAction.ATTACK);
            } else {
                // currently run has been removed. Only walk. Standby.
                if (keyboard.isKeyJustPressed(KeyboardKey.SPACE)) {
                    control.requestMove(ControlAction.JUMP);
                }
                if (keyboard.isKeyBeingPressed(KeyboardKey.LEFT) || keyboard.isKeyBeingPressed(KeyboardKey.A)) {
                    if (keyboard.isKeyBeingPressed(KeyboardKey.LEFT_SHIFT) || keyboard.isKeyBeingPressed(KeyboardKey.RIGHT_SHIFT)) {
                        control.requestMove(ControlAction.LEFT);
                    } else {
                        control.requestMove(ControlAction.LEFT);
                    }
                } else if (keyboard.isKeyBeingPressed(KeyboardKey.RIGHT) || keyboard.isKeyBeingPressed(KeyboardKey.D)) {
                    if (keyboard.isKeyBeingPressed(KeyboardKey.LEFT_SHIFT) || keyboard.isKeyBeingPressed(KeyboardKey.RIGHT_SHIFT)) {
                        control.requestMove(ControlAction.RIGHT);
                    } else {
                        control.requestMove(ControlAction.RIGHT);
                    }
                }
                if (keyboard.isKeyBeingPressed(KeyboardKey.UP) || keyboard.isKeyBeingPressed(KeyboardKey.W)) {
                    if (keyboard.isKeyBeingPressed(KeyboardKey.LEFT_SHIFT) || keyboard.isKeyBeingPressed(KeyboardKey.RIGHT_SHIFT)) {
                        control.requestMove(ControlAction.UP);
                    } else {
                        control.requestMove(ControlAction.UP);
                    }
                } else if (keyboard.isKeyBeingPressed(KeyboardKey.DOWN) || keyboard.isKeyBeingPressed(KeyboardKey.S)) {
                    if (keyboard.isKeyBeingPressed(KeyboardKey.LEFT_SHIFT) || keyboard.isKeyBeingPressed(KeyboardKey.RIGHT_SHIFT)) {
                        control.requestMove(ControlAction.DOWN);
                    } else {
                        control.requestMove(ControlAction.DOWN);
                    }
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
