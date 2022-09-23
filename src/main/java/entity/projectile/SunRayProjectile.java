package entity.projectile;

import engine.tickManager.TickManager;
import entity.mobs.GameDamage;
import entity.mobs.Mob;
import entity.mobs.PlayerMob;
import entity.trails.Trail;
import gfx.camera.GameCamera;
import gfx.drawOptions.texture.TextureDrawOptions;
import gfx.drawables.EntityDrawable;
import gfx.drawables.LevelSortedDrawable;
import gfx.drawables.OrderableDrawables;
import level.maps.Level;
import level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class SunRayProjectile extends Projectile {
    public SunRayProjectile() {
    }

    public SunRayProjectile(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback) {
        this.setLevel(level);
        this.setOwner(owner);
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.distance = distance;
        this.setDamage(damage);
        this.knockback = knockback;
    }

    public void init() {
        super.init();
        this.height = 18.0F;
        this.piercing = 0;
        this.width = 6.0F;
    }

    public Color getParticleColor() {
        return new Color(231, 199, 77);
    }

    public Trail getTrail() {
        return new Trail(this, this.getLevel(), new Color(231, 199, 77), 6.0F, 200, this.getHeight());
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (!this.removed()) {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y);
            final TextureDrawOptions options = this.texture.initDraw().light(light).rotate(this.getAngle(), this.texture.getWidth() / 2, 0).pos(drawX, drawY - (int)this.getHeight());
            list.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
            this.addShadowDrawables(tileList, drawX, drawY, light, this.getAngle(), 0);
        }
    }
}
