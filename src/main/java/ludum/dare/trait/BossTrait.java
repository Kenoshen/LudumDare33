package ludum.dare.trait;

import com.badlogic.gdx.math.MathUtils;
import ludum.dare.world.Boss;

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
    private boolean enemySpawned = false;

    private enum State {
        CAN_1_ATTACK,
        CAN_2_ATTACK,
        SPAWN,
        RESET,
        WAIT
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

        System.out.println(state + " " + elapsedTime);

        if (!started) {
            if (elapsedTime >= 3) {
                started = true;
                state = State.RESET;
            } else {
                return;
            }
        }

        if (state.equals(State.RESET)) {
            // RESET and prep next phase
            elapsedTime = 0;
            shotsFired = 0;
            enemySpawned = false;
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
                System.out.println("State " + state + " chosen");
            }
        } else if (state.equals(State.CAN_1_ATTACK)) {
            if (shotsFired < 5) {
                if (elapsedTime >= shotsFired * shotSpeed) {
                    boss.can1.shoot();
                    shotsFired++;
                    System.out.println("Shoot Can 1");
                }
            } else {
                state = State.RESET;
            }
        } else if (state.equals(State.CAN_2_ATTACK)) {
            if (shotsFired < 5) {
                if (elapsedTime >= shotsFired * shotSpeed) {
                    boss.can2.shoot();
                    shotsFired++;
                    System.out.println("Shoot Can 2");
                }
            } else {
                state = State.RESET;
            }
        } else if (state.equals(State.SPAWN)) {
            boss.door.getTrait(AnimatorTrait.class).changeStateIfUnique("open", false);
            if (elapsedTime >= 1f && !enemySpawned) {
                enemySpawned = true;
                boss.door.spawnSomething();
            } else if (elapsedTime >= 2) {
                boss.door.getTrait(AnimatorTrait.class).changeStateIfUnique("close", false);
                state = State.RESET;
            }
        }
    }
}
