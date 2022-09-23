package entity.projectile;

import engine.network.PacketReader;
import engine.network.PacketWriter;
import engine.tickManager.TickManager;
import engine.util.GameMath;
import entity.mobs.GameDamage;
import entity.mobs.Mob;
import entity.mobs.PlayerMob;
import entity.projectile.Projectile;
import entity.projectile.followingProjectile.FollowingProjectile;
import entity.trails.Trail;
import gfx.GameResources;
import gfx.camera.GameCamera;
import gfx.drawOptions.texture.TextureDrawOptions;
import gfx.drawables.EntityDrawable;
import gfx.drawables.LevelSortedDrawable;
import gfx.drawables.OrderableDrawables;
import gfx.gameTexture.GameSprite;
import level.maps.Level;
import level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

//F8FB29FF

public class ElectrumStaffProjectile extends FollowingProjectile {
    public ElectrumStaffProjectile() {
    }

    public void init() {
        super.init();
        this.turnSpeed = .75F;
        this.givesLight = true;
        this.height = 18.0F;
        this.piercing = 2;
        this.bouncing = 3;
    }

    protected int getExtraSpinningParticles() {
        return super.getExtraSpinningParticles() + 1;
    }

    public Color getParticleColor() {
        return new Color(248, 251, 41);
    }

    public Trail getTrail() {
        return new Trail(this, this.getLevel(), new Color(248, 251, 41), 6.0F, 500, 18.0F);
    }

    public void updateTarget() {
        if (this.traveledDistance > 100.0F) {
            this.findTarget((m) -> {
                return m.isHostile;
            }, 160.0F, 160.0F);
        }

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