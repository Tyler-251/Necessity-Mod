package entity.mobs.summon;

import engine.tickManager.TickManager;
import engine.util.GameRandom;
import entity.mobs.GameDamage;
import entity.mobs.Mob;
import entity.mobs.MobDrawable;
import entity.mobs.MobHitCooldowns;
import entity.mobs.PlayerMob;
import entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import entity.mobs.ai.behaviourTree.trees.PlayerFlyingFollowerCollisionChaserAI;
import entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import entity.particle.FleshParticle;
import entity.particle.Particle.GType;
import gfx.camera.GameCamera;
import gfx.drawOptions.DrawOptions;
import gfx.drawables.OrderableDrawables;
import java.awt.Rectangle;
import java.util.List;

import gfx.gameTexture.GameTexture;
import level.maps.Level;
import level.maps.light.GameLight;

public class JusticeMob extends FlyingAttackingFollowingMob {
    private MobHitCooldowns hitCooldowns = new MobHitCooldowns();
    public static GameTexture texture;

    public JusticeMob() {
        super(10);
        this.accelerationMod = 1.0F;
        this.moveAccuracy = 10;
        this.setSpeed(100.0F);
        this.setFriction(1.0F);
        this.collision = new Rectangle(-18, -15, 36, 30);
        this.hitBox = new Rectangle(-18, -15, 36, 36);
        this.selectBox = new Rectangle(-20, -18, 40, 36);
    }

    public GameDamage getCollisionDamage(Mob target) {
        return this.damage;
    }

    public int getCollisionKnockback(Mob target) {
        return 15;
    }

    public void handleCollisionHit(Mob target, GameDamage damage, int knockback) {
        Mob owner = this.getAttackOwner();
        if (owner != null && target != null) {
            target.isServerHit(damage, target.x - owner.x, target.y - owner.y, (float) knockback, this);
            this.collisionHitCooldowns.startCooldown(target);
        }

    }

    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI(this, new PlayerFlyingFollowerCollisionChaserAI(576, (GameDamage) null, 15, 500, 640, 64), new FlyingAIMover());
    }

    public void setFacingDir(float deltaX, float deltaY) {
        if (deltaX < 0.0F) {
            this.dir = 0;
        } else if (deltaX > 0.0F) {
            this.dir = 1;
        }

    }

    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        for (int i = 0; i < 5; ++i) {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), texture, GameRandom.globalRandom.nextInt(4), 1, 32, this.x, this.y, 10.0F, knockbackX, knockbackY), GType.IMPORTANT_COSMETIC);
        }

    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 32;
        long time = level.getWorldEntity().getTime() % 350L;
        byte sprite;
        if (time < 100L) {
            sprite = 0;
        } else if (time < 200L) {
            sprite = 1;
        } else if (time < 300L) {
            sprite = 2;
        } else {
            sprite = 3;
        }

        float rotate = this.dx / 10.0F;
        DrawOptions options = texture.initDraw().sprite(sprite, 0, 32).size(64, 64).light(light).mirror(this.dir == 0, false).rotate(rotate, 32, 32).pos(drawX, drawY);
        topList.add((tm) -> {
            options.draw();
        });
    }
}
