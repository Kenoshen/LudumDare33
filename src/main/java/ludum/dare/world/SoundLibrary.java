package ludum.dare.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

/**
 * Created by Admin on 8/23/2015.
 */
public class SoundLibrary {
    public static final HashMap<String, Sound> hashMapSoundLibrary = new HashMap<>();


    public static Sound GetSound(String name){
        Sound sound;

        sound = hashMapSoundLibrary.get(name);
        if (sound == null){
            sound = Gdx.audio.newSound(Gdx.files.internal("sfx/" + name + ".ogg"));
            hashMapSoundLibrary.put(name, sound);
        }

        return sound;
    }

}
