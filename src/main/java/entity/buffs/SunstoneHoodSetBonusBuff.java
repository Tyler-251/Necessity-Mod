//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package entity.buffs;

import engine.localization.Localization;
import engine.util.GameMath;
import engine.util.GameRandom;
import engine.world.WorldEntity;
import entity.mobs.GameDamage;
import entity.mobs.PlayerMob;
import entity.mobs.GameDamage.DamageType;
import entity.mobs.buffs.ActiveBuff;
import entity.mobs.buffs.BuffModifiers;
import entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import entity.particle.Particle;
import entity.projectile.IcicleStaffProjectile;
import entity.projectile.SunRayProjectile;
import gfx.gameTooltips.ListGameTooltips;
import inventory.InventoryItem;
import inventory.PlayerInventorySlot;
import inventory.item.toolItem.ToolItem;
import inventory.item.toolItem.projectileToolItem.throwToolItem.ThrowToolItem;

import java.awt.*;
import java.awt.geom.Point2D;
import level.maps.Level;

public class SunstoneHoodSetBonusBuff extends SetBonusBuff {
    public SunstoneHoodSetBonusBuff() {
    }

    @Override
    public void init(ActiveBuff activeBuff) {
        activeBuff.setModifier(BuffModifiers.RANGED_ATTACK_SPEED, 0.20F);
    }

    @Override
    public void clientTick(ActiveBuff buff) {
        if (buff.owner.getWorldEntity().getTimeOfDay() == WorldEntity.TimeOfDay.NIGHT) {
            buff.resetDefaultModifiers();
            buff.setModifier(BuffModifiers.EMITS_LIGHT,true);
        } else {
            buff.resetDefaultModifiers();
            buff.setModifier(BuffModifiers.RANGED_ATTACK_SPEED, 0.20F);
        }
    }
}
