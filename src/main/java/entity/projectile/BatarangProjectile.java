package entity.projectile;

import engine.tickManager.TickManager;
import entity.mobs.PlayerMob;
import entity.projectile.boomerangProjectile.BoomerangProjectile;
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

public class BatarangProjectile extends BoomerangProjectile {

    public void init() {
        super.init();
        this.setWidth(10.0F);
        this.height = 14.0F;
    }

    public Trail getTrail() {
        return new Trail(this, this.getLevel(), new Color(255, 0, 0), 16.0F, 200, 18.0F);
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (!this.removed()) {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y) - this.texture.getHeight() / 2;
            final TextureDrawOptions options = this.texture.initDraw().light(light).rotate(this.getAngle(), this.texture.getWidth() / 2, this.texture.getHeight() / 2).pos(drawX, drawY - (int)this.getHeight());
            list.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
            this.addShadowDrawables(tileList, drawX, drawY, light, this.getAngle(), this.texture.getHeight() / 2);
        }
    }
}