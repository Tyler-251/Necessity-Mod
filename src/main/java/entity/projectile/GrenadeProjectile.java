//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package entity.projectile;

import entity.levelEvent.explosionEvent.BombExplosionEvent;
import entity.levelEvent.explosionEvent.ExplosionEvent;
import entity.mobs.GameDamage;
import entity.mobs.Mob;
import entity.projectile.BombProjectile;

public class GrenadeProjectile extends BombProjectile {
    public GrenadeProjectile() {
    }

    public GrenadeProjectile(float x, float y, float targetX, float targetY, int speed, int distance, GameDamage damage, Mob owner) {
        super(x, y, targetX, targetY, speed, distance, damage, owner);
    }

    public int getFuseTime() {
        return 800;
    }

    public float getParticleAngle() {
        return 290.0F;
    }

    public float getParticleDistance() {
        return 12.0F;
    }

    public ExplosionEvent getExplosionEvent(float x, float y) {
        BombExplosionEvent boom = new BombExplosionEvent(x, y, 60, new GameDamage(200.0F, 1000.0F), false, 1, this.getOwner());
        return boom;
    }
}
