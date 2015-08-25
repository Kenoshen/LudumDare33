package ludum.dare.trait;

import com.badlogic.gdx.math.MathUtils;
import ludum.dare.screen.GameScreen;
import ludum.dare.world.*;

/**
 * Created by Admin on 8/24/2015.
 */
public class BossTrait extends Trait {
    private Boss boss;
    float elapsedTime = 0;

    boolean started = false;

    private State state = State.WAIT;

    private int shotsFired = 0;
    private float shotSpeed = .75f;
    private boolean soundPlayed = false;

    private int explosions = 0;
    private float boomSpeed = .4f;

    private float spawnRate = 1f;
    private int spawnedEnemies = 0;

    private enum State {
        CAN_1_ATTACK,
        CAN_2_ATTACK,
        SPAWN,
        RESET,
        DEFEAT, WAIT
    }

    public BossTrait(Boss boss) {
        super(boss);
        this.boss = boss;
    }

    @Override
    public Class[] requires() {
        return new Class[0];
    }

    public void update(float delta) {
        elapsedTime += delta;

        if (!started) {
            if (elapsedTime >= 3) {
                started = true;
                state = State.RESET;
            } else {
                return;
            }
        }

        if (!state.equals(State.DEFEAT) && boss.can1.getTrait(HealthTrait.class).health <= 0 && boss.can2.getTrait(HealthTrait.class).health <= 0) {
            state = State.DEFEAT;
            elapsedTime = 0;
        }

        if (state.equals(State.DEFEAT)) {
            if (explosions < 5) {
                if (elapsedTime > explosions * boomSpeed + MathUtils.random(-.3f, .3f)) {
                    explosions++;
                    GameScreen.addObject(new Explosion(20, 1.5f, 30 + MathUtils.random(-5, 5)));
                    SoundLibrary.GetSound("Robot_Death").play();
                    if (explosions == 3) {
                        GameScreen.addObject(new HollInTheWall(48 * 6 + 17, 0, 8));
                    }
                }
            }
        }

        if (state.equals(State.RESET)) {
            // RESET and prep next phase
            elapsedTime = 0;
            shotsFired = 0;
            spawnedEnemies = 0;
            state = State.WAIT;
        }

        if (state.equals(State.WAIT)) {
            if (elapsedTime >= 2) {
                switch (MathUtils.random(2)) {
                    case 0:
                        state = State.CAN_1_ATTACK;
                        break;
                    case 1:
                        state = State.CAN_2_ATTACK;
                        break;
                    case 2:
                        state = State.SPAWN;
                        break;
                }
                elapsedTime = 0;
            }
        } else if (state.equals(State.CAN_1_ATTACK)) {
            if (boss.can1.getTrait(HealthTrait.class).health <= 0) {
                state = State.WAIT;
            }
            boss.can1.goGreen();
            if (shotsFired < 5) {
                float nextShotAt = 2 + shotsFired * shotSpeed;
                if (!soundPlayed && elapsedTime >= nextShotAt - .8f) {
                    soundPlayed = true;
                    SoundLibrary.GetSound("Electric_Charge").play();
                }
                if (elapsedTime >= nextShotAt) {
                    boss.can1.shoot();
                    shotsFired++;
                    soundPlayed = false;
                }
            } else {
                boss.can1.goRed();
                state = State.RESET;
            }
        } else if (state.equals(State.CAN_2_ATTACK)) {
            if (boss.can2.getTrait(HealthTrait.class).health <= 0) {
                state = State.WAIT;
            }
            boss.can2.goGreen();
            if (shotsFired < 5) {
                float nextShotAt = 2 + shotsFired * shotSpeed;
                if (!soundPlayed && elapsedTime >= nextShotAt - .7f) {
                    soundPlayed = true;
                    SoundLibrary.GetSound("Electric_Charge").play();
                }
                if (elapsedTime >= nextShotAt) {
                    boss.can2.shoot();
                    shotsFired++;
                    soundPlayed = false;
                }
            } else {
                boss.can2.goRed();
                state = State.RESET;
            }
        } else if (state.equals(State.SPAWN)) {
            int basicCount = 0;
            for (GameObject obj : GameScreen.gameObjects) {
                if (obj instanceof EnemyBasic) {
                    basicCount++;
                }
            }
            if (spawnedEnemies < 2) {
                boss.door.getTrait(AnimatorTrait.class).changeStateIfUnique("open", false);
                if (elapsedTime >= (1f + spawnedEnemies * spawnRate)) {
                    // only spawn up to a max of 2 basic enemies on the screen
                    if (basicCount < 2) {
                        spawnedEnemies++;
                        basicCount++;
                        boss.door.spawnSomething();
                    } else {
                        boss.door.getTrait(AnimatorTrait.class).changeStateIfUnique("close", false);
                        state = State.RESET;
                    }
                }
            } else {
                boss.door.getTrait(AnimatorTrait.class).changeStateIfUnique("close", false);
                state = State.RESET;
            }
        }
    }
}
