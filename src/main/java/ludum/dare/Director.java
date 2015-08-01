package ludum.dare;

import com.winger.Winger;
import com.winger.draw.texture.CSpriteBatch;
import com.winger.input.delegate.CKeyboardEventHandler;
import com.winger.input.delegate.CMouseEventHandler;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.input.raw.state.ButtonState;
import com.winger.log.HTMLLogger;
import com.winger.log.LogGroup;
import ludum.dare.stage.MainMenuStage;
import ludum.dare.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 7/30/15.
 */
public class Director {
    private static final HTMLLogger log = HTMLLogger.getLogger(Director.class, LogGroup.GameLogic, LogGroup.System);
    public static float worldStepTime = 1 / 60f;
    public boolean worldEnabled = false;

    public CMouse mouse;
    public CKeyboard keyboard;

    private List<Stage> stages = new ArrayList<Stage>();
    private MainMenuStage mainMenuStage;

    public Director(CMouse mouse, CKeyboard keyboard){
        log.debug("Construct Director");
        //
        this.mouse = mouse;
        this.keyboard = keyboard;
    }

    public void initialize(){
        log.debug("Initialize Director");
        //
        mainMenuStage = new MainMenuStage(Winger.ui.getPage("mainmenu"), this);
        //
        Winger.ui.transitionToPage(mainMenuStage.ui);
    }

    public void addStage(Stage stage){
        log.debug("Add Stage to director");
        //
        stages.add(stage);
    }

    public void subscribeToAllMouseEvents(CMouseEventHandler handler){
        mouse.subscribeToAllMouseEvents(handler);
    }

    public void subscribeToAllKeyboardEvents(CKeyboardEventHandler handler){
        keyboard.subscribeToAllKeyboardEvents(handler, ButtonState.DOWN);
        keyboard.subscribeToAllKeyboardEvents(handler, ButtonState.UP);
    }

    public void update(){
        for(Stage stage : stages){
            if (stage.isEnabled()){
                stage.update(worldStepTime);
            }
        }
    }

    public void draw(CSpriteBatch spriteBatch){
        for(Stage stage : stages){
            if (stage.isVisible()){
                stage.draw(spriteBatch);
            }
        }
    }
}
