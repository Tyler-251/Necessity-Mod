//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package entity.buffs;

import engine.Screen;
import engine.localization.Localization;
import engine.network.Packet;
import engine.registries.BuffRegistry;
import engine.sound.SoundEffect;
import engine.util.GameMath;
import engine.util.GameRandom;
import engine.world.WorldEntity;
import entity.levelEvent.mobAbilityLevelEvent.CaveSpiderSpitEvent;
import entity.mobs.*;
import entity.mobs.buffs.ActiveBuff;
import entity.mobs.buffs.BuffAbility;
import entity.mobs.buffs.BuffModifiers;
import entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import entity.mobs.hostile.GiantCaveSpiderMob;
import entity.particle.Particle.GType;
import entity.projectile.IcicleStaffProjectile;
import entity.projectile.SunRayProjectile;
import gfx.GameResources;
import gfx.gameTooltips.ListGameTooltips;
import java.awt.Color;
import java.awt.geom.Point2D;

import inventory.InventoryItem;
import inventory.PlayerInventorySlot;
import inventory.item.toolItem.ToolItem;
import inventory.item.toolItem.projectileToolItem.throwToolItem.ThrowToolItem;
import level.maps.Level;

public class SunstoneHelmetSetBonusBuff extends SetBonusBuff {
    public SunstoneHelmetSetBonusBuff() {
    }

    @Override
    public void init(ActiveBuff activeBuff) {
        activeBuff.setModifier(BuffModifiers.MELEE_ATTACK_SPEED, 0.20F);
    }

    @Override
    public void clientTick(ActiveBuff buff) {
        if (buff.owner.getWorldEntity().getTimeOfDay() == WorldEntity.TimeOfDay.NIGHT) {
            buff.resetDefaultModifiers();
            buff.setModifier(BuffModifiers.EMITS_LIGHT,true);
        } else {
            buff.resetDefaultModifiers();
            buff.setModifier(BuffModifiers.MELEE_ATTACK_SPEED, 0.20F);
        }
    }

    //    public ListGameTooltips getTrinketTooltip() {
//        ListGameTooltips tooltips = super.getTrinketTooltip();
//        tooltips.add(Localization.translate("itemtooltip", "shinebelttip"));
//        return tooltips;
//    }
//
//    public void init(ActiveBuff buff) {
//    }
//
//
//
//    public void clientTick(ActiveBuff buff) {
//        super.clientTick(buff);
//        Level level = buff.owner.getLevel();
//        if (level.tickManager().getTotalTicks() % 5L == 0L) {
//            level.entityManager.addParticle(buff.owner.x + (float)(GameRandom.globalRandom.nextGaussian() * 6.0), buff.owner.y + (float)(GameRandom.globalRandom.nextGaussian() * 8.0), GType.COSMETIC).movesConstant(buff.owner.dx / 10.0F, buff.owner.dy / 10.0F).color(new Color(249, 226, 117)).sizeFades(6, 10).givesLight(50.0F, 0.4F).height(16.0F);
//        }
//
//        level.lightManager.refreshParticleLightFloat(buff.owner.x, buff.owner.y, 50.0F, 0.4F);
//    }
//
//    public void runAbility(PlayerMob player, ActiveBuff buff, Packet content) {
//        Mob owner = buff.owner;
//
////        onItemAttacked;
//        owner.buffManager.addBuff(new ActiveBuff(BuffRegistry.Debuffs.VOID_SET_COOLDOWN, owner, 30.0F, (Attacker)null), false);
//        if (owner.getLevel().isClientLevel()) {
//            Screen.playSound(GameResources.teleportfail, SoundEffect.effect(owner).pitch(1.3F));
//        }
//    }
//
//    public void onItemAttacked(ActiveBuff buff, int targetX, int targetY, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack) {
//        super.onItemAttacked(buff, targetX, targetY, player, attackHeight, item, slot, animAttack);
//        Level level = buff.owner.getLevel();
//                String shotTimeKey = "icicleshottime";
//                long shotTime = buff.getGndData().getLong(shotTimeKey);
//                int cooldown = Math.round(50.0F * (1.0F / (Float)player.buffManager.getModifier(BuffModifiers.ATTACK_SPEED)) * (1.0F / (Float)player.buffManager.getModifier(BuffModifiers.RANGED_ATTACK_SPEED)));
//                if (shotTime + (long)cooldown < level.getWorldEntity().getTime()) {
//                    buff.getGndData().setLong(shotTimeKey, level.getWorldEntity().getTime());
//                    GameRandom random = GameRandom.globalRandom;
//                    Point2D.Float dir = GameMath.normalize(player.x - (float)targetX, player.y - (float)targetY);
//                    int offsetDistance = random.getIntBetween(30, 50);
//                    Point2D.Float offset = new Point2D.Float(dir.x * (float)offsetDistance, dir.y * (float)offsetDistance);
//                    offset = GameMath.getPerpendicularPoint(offset, (float)random.getIntBetween(-50, 50), dir.x, dir.y);
//                    float velocity = 300.0F * (Float)player.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);
//                    float damage = 5.0F * (Float)player.buffManager.getModifier(BuffModifiers.ALL_DAMAGE) * (Float)player.buffManager.getModifier(BuffModifiers.RANGED_DAMAGE);
//                    int armorPen = (Integer)player.buffManager.getModifier(BuffModifiers.ARMOR_PEN_FLAT);
//                    GameDamage gameDamage = new GameDamage(damage, (float)armorPen);
//                    SunRayProjectile projectile = new SunRayProjectile(level, player, player.x + offset.x, player.y + offset.y, (float)targetX, (float)targetY, velocity, 500, gameDamage, 0);
//                    level.entityManager.projectiles.add(projectile);
//        }
//    }
//
//    public boolean canRunAbility(PlayerMob player, ActiveBuff buff, Packet content) {
//        return !buff.owner.buffManager.hasBuff(BuffRegistry.Debuffs.VOID_SET_COOLDOWN.getID());
//    }

}
