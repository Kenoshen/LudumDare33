package ludum.dare.utils;

import ludum.dare.trait.GameObject;

/**
 * Created by Admin on 8/23/2015.
 */
public abstract class HealthCallback {
    public abstract void damageReceived(int amount, GameObject from);
    public abstract void healthRegained(int amount, GameObject from);
}
