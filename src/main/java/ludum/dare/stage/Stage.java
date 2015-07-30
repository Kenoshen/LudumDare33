package ludum.dare.stage;

import com.badlogic.gdx.math.Vector2;
import com.winger.draw.texture.CSpriteBatch;
import com.winger.input.delegate.CKeyboardEventHandler;
import com.winger.input.delegate.CMouseEventHandler;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.input.raw.state.ButtonState;
import com.winger.input.raw.state.CMouseButton;
import com.winger.input.raw.state.CMouseDrag;
import com.winger.input.raw.state.KeyboardKey;
import com.winger.log.HTMLLogger;
import com.winger.log.LogGroup;
import com.winger.ui.Page;
import ludum.dare.Director;

/**
 * Created by mwingfield on 7/30/15.
 */
public class Stage implements CMouseEventHandler, CKeyboardEventHandler {
    protected final HTMLLogger log;
    public Page ui;
    public Director director;
    public Stage(Page ui, Director director){
        this.ui = ui;
        this.director = director;
        director.addStage(this);

        log = HTMLLogger.getLogger(this.getClass(), LogGroup.GameLogic, LogGroup.System);
    }

    public boolean isEnabled(){
        return ui.isEnabled;
    }

    public boolean isVisible(){
        return ui.isVisible;
    }

    public void update(float delta){

    }

    public void draw(CSpriteBatch spriteBatch){

    }

    @Override
    public final void handleKeyEvent(CKeyboard cKeyboard, KeyboardKey keyboardKey, ButtonState buttonState) {
        if (isEnabled()){
            handleKeyEvt(cKeyboard, keyboardKey, buttonState);
        }
    }

    protected void handleKeyEvt(CKeyboard cKeyboard, KeyboardKey keyboardKey, ButtonState buttonState){

    }

    @Override
    public final void handleClickEvent(CMouse cMouse, CMouseButton cMouseButton, ButtonState buttonState) {
        if (isEnabled()){
            handleClickEvt(cMouse, cMouseButton, buttonState);
        }
    }

    protected void handleClickEvt(CMouse cMouse, CMouseButton cMouseButton, ButtonState buttonState) {

    }

    @Override
    public final void handleScrollEvent(CMouse cMouse, float v) {
        if (isEnabled()){
            handleScrollEvt(cMouse, v);
        }
    }

    protected void handleScrollEvt(CMouse cMouse, float v) {

    }

    @Override
    public final void handleMoveEvent(CMouse cMouse, Vector2 vector2) {
        if (isEnabled()){
            handleMoveEvt(cMouse, vector2);
        }
    }

    protected void handleMoveEvt(CMouse cMouse, Vector2 vector2) {

    }

    @Override
    public final void handleDragEvent(CMouse cMouse, CMouseButton cMouseButton, CMouseDrag cMouseDrag) {
        if (isEnabled()){
            handleDragEvt(cMouse, cMouseButton, cMouseDrag);
        }
    }

    protected void handleDragEvt(CMouse cMouse, CMouseButton cMouseButton, CMouseDrag cMouseDrag) {

    }
}
