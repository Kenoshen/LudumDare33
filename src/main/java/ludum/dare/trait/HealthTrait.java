package ludum.dare.trait;

import ludum.dare.utils.HealthCallback;

/**
 * Created by jake on 8/22/2015.
 */
public class HealthTrait extends Trait {
    public final int maxHealth;
    public int health;
    private HealthCallback callback;

    public HealthTrait(GameObject obj, int h, HealthCallback cback) {
        super(obj);
        maxHealth = h;
        health = h;
        cback = cback == null ? new HealthCallback() {
            @Override
            public void damageReceived(int amount, GameObject from) {

            }

            @Override
            public void healthRegained(int amount, GameObject from) {

            }

            @Override
            public void died() {

            }
        } : cback;
        callback = cback;
    }

    @Override
    public Class[] requires() {return new Class[0];
    }
    public void damage(int d, GameObject from) {
        health -= d;
        health = Math.max(0, health);
        callback.damageReceived(d, from);
        if (health <= 0) {
            callback.died();
        }
    }
    public void heal(int h, GameObject from){
        health += h;
        health = Math.min(health, maxHealth);
        callback.healthRegained(h, from);
    }
}
