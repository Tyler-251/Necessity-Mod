package entity.projectile;

import engine.network.PacketReader;
import engine.network.PacketWriter;
import engine.tickManager.TickManager;
import engine.util.GameMath;
import entity.mobs.GameDamage;
import entity.mobs.Mob;
import entity.mobs.PlayerMob;
import entity.projectile.Projectile;
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

public class FungalStaffProjectile extends Projectile {
    protected long spawnTime;
    protected float deltaHeight;
    protected float fungalHeight;

    public FungalStaffProjectile() {
    }

    public FungalStaffProjectile(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback) {
        this.setLevel(level);
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.setTarget(targetX, targetY);
        this.setDamage(damage);
        this.knockback = knockback;
        this.setDistance(distance);
        this.setOwner(owner);
        this.fungalHeight = 10.0F;
        this.deltaHeight = 25.0F;
    }

    public void setupPositionPacket(PacketWriter writer) {
        super.setupPositionPacket(writer);
        writer.putNextFloat(this.deltaHeight);
        writer.putNextFloat(this.fungalHeight);
    }

    public void applyPositionPacket(PacketReader reader) {
        super.applyPositionPacket(reader);
        this.deltaHeight = reader.getNextFloat();
        this.fungalHeight = reader.getNextFloat();
    }

    public void init() {
        super.init();
        this.spawnTime = this.getWorldEntity().getTime();
        this.isSolid = true;
        this.width = 32.0F;
        this.trailOffset = 0.0F;
        this.piercing = 2;
    }

//    public float tickMovement(float delta) {
//        float out = super.tickMovement(delta);
//        float heightChange = 50.0F * delta / 250.0F;
//        this.deltaHeight -= heightChange;
//        this.fungalHeight += this.deltaHeight * delta / 250.0F;
//        if (this.fungalHeight < 0.0F) {
//            if (this.getLevel().isClientLevel()) {
//                Screen.playSound(GameResources.punch, SoundEffect.effect(this).volume(0.5F).pitch(1.0F));
//                ExplosionEvent.spawnExplosionParticles(this.getLevel(), this.x, this.y, 20, 0.0F, 20.0F, (level, x, y, dirX, dirY, lifeTime) -> {
//                    level.entityManager.addParticle(x, y, Particle.GType.CRITICAL).movesConstant(dirX, dirY).color(new Color(50, 50, 50)).lifeTime(lifeTime);
//                });
//            }
//
//            this.deltaHeight = -this.deltaHeight * 0.95F;
//            this.fungalHeight = -this.fungalHeight;
//            if (Math.abs(this.deltaHeight) < heightChange * 2.0F) {
//                this.fungalHeight = -1.0F;
//                this.deltaHeight = 0.0F;
//            }
//        }
//
//        this.height = Math.max(this.fungalHeight, 0.0F);
//        return out;
//    }

    public Color getParticleColor() {
        return new Color(147, 203, 50);
    }

    public Trail getTrail() {
        Trail trail = new Trail(this, this.getLevel(), new Color(186, 203, 34), 0.0F, 250, this.getHeight());
        trail.sprite = new GameSprite(GameResources.smokeParticles, 7, 0, 32);
        return trail;
    }

    public float getTrailThickness() {
        return 20.0F;
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (!this.removed()) {
            GameLight light = level.getLightLevel(this.getX() / 32, this.getY() / 32);
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y) - this.texture.getHeight() / 2;
            float angle = (float)(this.getWorldEntity().getTime() - this.spawnTime) / 1.5F;
            if (this.dx < 0.0F) {
                angle = -angle;
            }

            final TextureDrawOptions options = this.texture.initDraw().light(light).rotate(angle, this.texture.getWidth() / 2, this.texture.getHeight() / 2).pos(drawX, drawY - (int)this.getHeight());
            list.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
            float shadowAlpha = Math.abs(GameMath.limit(this.height / 100.0F, 0.0F, 1.0F) - 1.0F);
            float sizeMod = Math.abs(GameMath.limit(this.height / 100.0F, 0.0F, 1.0F) - 1.0F);
            int shadowWidth = (int)((float)this.shadowTexture.getWidth() * sizeMod);
            int shadowHeight = (int)((float)this.shadowTexture.getHeight() * sizeMod);
            int shadowX = camera.getDrawX(this.x) - shadowWidth / 2;
            int shadowY = camera.getDrawY(this.y) - shadowHeight / 2;
            TextureDrawOptions shadowOptions = this.shadowTexture.initDraw().size(shadowWidth, shadowHeight).light(light).alpha(shadowAlpha).pos(shadowX, shadowY);
            tileList.add((tm) -> {
                shadowOptions.draw();
            });
        }
    }
}
