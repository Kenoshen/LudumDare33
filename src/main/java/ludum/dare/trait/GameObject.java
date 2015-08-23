package ludum.dare.trait;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwingfield on 8/2/15.
 */
public abstract class GameObject {
    public List<Trait> traits = new ArrayList<>();
    private boolean shouldBeDeleted = false;

    public <T extends Trait> T getTrait(Class<T> traitType){
        for (Trait trait : traits){
            if (trait.getClass().equals(traitType)){
                return traitType.cast(trait);
            }
        }
        return null;
    }

    public List<Trait> getTraits(Class<? extends Trait>... traitTypes){
        List<Trait> ts = new ArrayList<>();
        for (int i = 0; i < traitTypes.length; i++){
            ts.add(null);
        }
        for (Trait trait : traits){
            for (int i = 0; i < traitTypes.length; i++) {
                if (trait.getClass().equals(traitTypes[i])) {
                    ts.set(i, trait);
                }
            }
        }
        return ts;
    }

    public GameObject addAndInitializeTrait(Trait trait){
        if (trait != null) {
            trait.initialize();
            traits.add(trait);
        }
        return this;
    }

    public void markForDeletion(){
        shouldBeDeleted = true;
    }

    public boolean shouldBeDeleted(){
        return shouldBeDeleted;
    }

    public void initializeTraits(){
        for (Trait t : traits){
            t.initialize();
        }
    }

}
