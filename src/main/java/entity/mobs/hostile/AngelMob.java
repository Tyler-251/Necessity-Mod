//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package entity.mobs.hostile;

import engine.Screen;
import engine.registries.BuffRegistry;
import engine.registries.MobRegistry.Textures;
import engine.sound.SoundEffect;
import engine.tickManager.TickManager;
import engine.util.GameRandom;
import entity.mobs.*;
import entity.mobs.ability.CoordinateMobAbility;
import entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import entity.mobs.ai.behaviourTree.decorators.FailerAINode;
import entity.mobs.ai.behaviourTree.leaves.TeleportOnProjectileHitAINode;
import entity.mobs.ai.behaviourTree.trees.PlayerChaserWandererAI;
import entity.particle.FleshParticle;
import entity.particle.SmokePuffParticle;
import entity.particle.Particle.GType;
import entity.projectile.AncientSkeletonMageProjectile;
import entity.projectile.SunRayProjectile;
import gfx.GameResources;
import gfx.camera.GameCamera;
import gfx.drawOptions.DrawOptions;
import gfx.drawOptions.human.HumanDrawOptions;
import gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import gfx.drawables.OrderableDrawables;
import inventory.lootTable.LootItemInterface;
import inventory.lootTable.LootTable;
import inventory.lootTable.lootItem.LootItem;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.function.Supplier;
import level.maps.Level;
import level.maps.light.GameLight;

public class AngelMob extends HostileMob {
    public static LootTable lootTable = new LootTable(new LootItemInterface[]{LootItem.between("sunstone", 1, 2)});
    public final CoordinateMobAbility teleportAbility;
    public static HumanTexture texture;

    public AngelMob() {
        super(150);
        this.attackAnimSpeed = 300;
        this.setSpeed(25.0F);
        this.setFriction(3.0F);
        this.setKnockbackModifier(0.4F);
        this.setArmor(10);
        this.collision = new Rectangle(-10, -7, 20, 14);
        this.hitBox = new Rectangle(-14, -12, 28, 24);
        this.selectBox = new Rectangle(-14, -41, 28, 48);
        this.teleportAbility = (CoordinateMobAbility)this.registerAbility(new CoordinateMobAbility() {
            protected void run(int x, int y) {
                if (AngelMob.this.getLevel().isClientLevel()) {
                    AngelMob.this.getLevel().entityManager.addParticle(new SmokePuffParticle(AngelMob.this.getLevel(), AngelMob.this.x, AngelMob.this.y, new Color(227, 203, 32)), GType.CRITICAL);
                    AngelMob.this.getLevel().entityManager.addParticle(new SmokePuffParticle(AngelMob.this.getLevel(), (float)x, (float)y, new Color(253, 175, 31)), GType.CRITICAL);
                }

                AngelMob.this.setPos((float)x, (float)y, true);
            }
        });
    }

    public void init() {
        super.init();
        PlayerChaserWandererAI<AngelMob> playerChaserAI = new PlayerChaserWandererAI<AngelMob>((Supplier)null, 640, 320, 40000, false, false) {
            public boolean shootTarget(AngelMob mob, Mob target) {
                if (mob.canAttack()) {
                    mob.attack(target.getX(), target.getY(), false);
                    mob.getLevel().entityManager.projectiles.add(new SunRayProjectile(mob.getLevel(), mob, mob.x, mob.y, target.x, target.y, 120.0F, 640, new GameDamage(60.0F), 50));
                    return true;
                } else {
                    return false;
                }
            }
        };
        playerChaserAI.addChildFirst(new FailerAINode(new TeleportOnProjectileHitAINode<AngelMob>(3000, 7) {
            public boolean teleport(AngelMob mob, int x, int y) {
                if (mob.getLevel().isServerLevel()) {
                    mob.teleportAbility.runAndSend(x, y);
                    this.getBlackboard().mover.stopMoving(mob);
                }

                return true;
            }
        }));
        this.ai = new BehaviourTreeAI(this, playerChaserAI);
    }

    public void clientTick() {
        super.clientTick();
        if (this.isAttacking) {
            this.getAttackAnimProgress();
        }
        Level level = this.getLevel();
        if (level.tickManager().getTotalTicks() % 5L == 0L) {
            level.entityManager.addParticle(this.x + (float)(GameRandom.globalRandom.nextGaussian() * 6.0), this.y + (float)(GameRandom.globalRandom.nextGaussian() * 8.0), GType.COSMETIC).movesConstant(this.dx / 10.0F, this.dy / 10.0F).color(new Color(249, 226, 117)).sizeFades(6, 10).givesLight(50.0F, 0.4F).height(16.0F);
        }
        level.lightManager.refreshParticleLightFloat(this.x, this.y, 50.0F, 0.4F);
    }

    public void serverTick() {
        super.serverTick();
        if (this.isAttacking) {
            this.getAttackAnimProgress();
        }

    }

    public LootTable getLootTable() {
        return lootTable;
    }

    public DeathMessageTable getDeathMessages() {
        return this.getDeathMessages("generic", 7);
    }

    public void playHitSound() {
        float pitch = (Float)GameRandom.globalRandom.getOneOf(new Float[]{0.95F, 1.0F, 1.05F});
        Screen.playSound(GameResources.crack, SoundEffect.effect(this).volume(1.6F).pitch(pitch));
    }

    protected void playDeathSound() {
        float pitch = (Float)GameRandom.globalRandom.getOneOf(new Float[]{0.95F, 1.0F, 1.05F});
        Screen.playSound(GameResources.crackdeath, SoundEffect.effect(this).volume(0.8F).pitch(pitch));
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        for(int i = 0; i < 4; ++i) {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), texture.body, GameRandom.globalRandom.nextInt(5), 8, 32, this.x, this.y, 20.0F, knockbackX, knockbackY), GType.IMPORTANT_COSMETIC);
        }

    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 22 - 10;
        int drawY = camera.getDrawY(y) - 44 - 7;
        Point sprite = this.getAnimSprite(x, y, this.dir);
        drawY += this.getBobbing(x, y);
        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(texture)).sprite(sprite).dir(this.dir).light(light);
        float animProgress = this.getAttackAnimProgress();
        if (this.isAttacking) {
            ItemAttackDrawOptions attackOptions = ItemAttackDrawOptions.start(this.dir).itemSprite(texture.body, 0, 9, 32).itemRotatePoint(4, 4).itemEnd().armSprite(texture.body, 0, 8, 32).swingRotation(animProgress).light(light);
            humanDrawOptions.attackAnim(attackOptions, animProgress);
        }

        final DrawOptions drawOptions = humanDrawOptions.pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        this.addShadowDrawables(tileList, x, y, light, camera);
    }

    public int getRockSpeed() {
        return 20;
    }

    public void showAttack(int x, int y, int seed, boolean showAllDirections) {
        super.showAttack(x, y, seed, showAllDirections);
        if (this.getLevel().isClientLevel()) {
            Screen.playSound(GameResources.swing2, SoundEffect.effect(this).volume(0.7F).pitch(1.2F));
        }

    }
}
