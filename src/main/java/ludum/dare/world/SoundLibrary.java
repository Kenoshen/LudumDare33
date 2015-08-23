package ludum.dare.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

/**
 * Created by Admin on 8/23/2015.
 */
public class SoundLibrary {
    public static final HashMap<String, Sound> hashMapSoundLibrary = new HashMap<>();
    public static final HashMap<String, Music> hashMapMusicLibrary = new HashMap<>();


    public static Sound GetSound(String name){
        Sound sound;

        sound = hashMapSoundLibrary.get(name);
        if (sound == null){
            sound = Gdx.audio.newSound(Gdx.files.internal("sfx/" + name + ".ogg"));
            hashMapSoundLibrary.put(name, sound);
        }

        return sound;
    }

    public static Music GetMusic(String name){
        Music music;

        music = hashMapMusicLibrary.get(name);
        if (music == null){
            music = Gdx.audio.newMusic(Gdx.files.internal("music/" + name + ".ogg"));
            hashMapMusicLibrary.put(name, music);
        }

        return music;
    }

}
