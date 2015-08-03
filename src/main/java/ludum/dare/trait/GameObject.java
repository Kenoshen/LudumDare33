package ludum.dare.trait;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/2/15.
 */
public abstract class GameObject {
    public List<Trait> traits = new ArrayList<>();
    private boolean shouldBeDeleted = false;

    // TODO: figure out how to do the dynamic return value
    public <T extends Trait> T getTrait(Class<T> traitType){
        for (Trait trait : traits){
            if (trait.getClass().equals(traitType)){
                return traitType.cast(trait);
            }
        }
        return null;
    }

    public void markForDeletion(){
        shouldBeDeleted = true;
    }

    public boolean shouldBeDeleted(){
        return shouldBeDeleted;
    }

    public void initializeTraits(){
        traits.forEach(t ->{
            t.initialize();
        });
    }
}
