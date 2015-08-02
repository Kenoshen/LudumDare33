package ludum.dare.trait;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/2/15.
 */
public abstract class GameObject {
    public List<Trait> traits = new ArrayList<>();

    // TODO: figure out how to do the dynamic return value
    public void getTrait(Class<? extends Trait> traitType){
        for (Trait trait : traits){
            if (trait.getClass().equals(traitType){
                return traitType.cast(trait);
            }
        }
    }
}
