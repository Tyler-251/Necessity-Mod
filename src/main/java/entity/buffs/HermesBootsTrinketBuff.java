//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package entity.buffs;

import engine.Settings;
import engine.control.Control;
import engine.network.Packet;
import engine.network.PacketReader;
import engine.network.PacketWriter;
import engine.registries.BuffRegistry;
import entity.mobs.Attacker;
import entity.mobs.PlayerMob;
import entity.mobs.buffs.ActiveBuff;
import entity.mobs.buffs.ActiveBuffAbility;
import entity.mobs.buffs.staticBuffs.Buff;
import entity.mobs.buffs.staticBuffs.StaminaBuff;
import entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import gfx.camera.GameCamera;

public class HermesBootsTrinketBuff extends TrinketBuff implements ActiveBuffAbility {

    public HermesBootsTrinketBuff() {
    }

    public void init(ActiveBuff buff) {
    }

    public Packet getStartAbilityContent(PlayerMob player, ActiveBuff buff, GameCamera camera) {
        return this.getRunningAbilityContent(player, buff);
    }

    public Packet getRunningAbilityContent(PlayerMob player, ActiveBuff buff) {
        Packet content = new Packet();
        PacketWriter writer = new PacketWriter(content);
        StaminaBuff.writeStaminaData(player, writer);
        return content;
    }

    public boolean canRunAbility(PlayerMob player, ActiveBuff buff, Packet content) {
        if (buff.owner.isRiding()) {
            return false;
        } else {
            return player.getLevel().isServerLevel() && Settings.giveClientsPower ? true : StaminaBuff.canStartStaminaUsage(buff.owner);
        }
    }

    public void onActiveAbilityStarted(PlayerMob player, ActiveBuff buff, Packet content) {
        PacketReader reader = new PacketReader(content);
        if (!player.getLevel().isServerLevel() || Settings.giveClientsPower) {
            StaminaBuff.readStaminaData(player, reader);
        }

        player.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("hermesbootsactive"), player, 1.0F, (Attacker)null), false);
    }



    public boolean tickActiveAbility(PlayerMob player, ActiveBuff buff, boolean isRunningClient) {
        if (player.inLiquid()) {
            player.buffManager.removeBuff(BuffRegistry.getBuff("hermesbootsactive"), false);
        } else {
            ActiveBuff speedBuff = player.buffManager.getBuff(BuffRegistry.getBuff("hermesbootsactive"));
            if (speedBuff != null) {
                speedBuff.setDurationLeftSeconds(1.0F);
            } else {
                player.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("hermesbootsactive"), player, 1.0F, (Attacker)null), false);
            }

            if ((player.moveX != 0.0F || player.moveY != 0.0F) && (player.dx != 0.0F || player.dy != 0.0F)) {
                long msToDeplete = 4000L;
                float usage = 50.0F / (float)msToDeplete;
                if (!StaminaBuff.useStaminaAndGetValid(player, usage)) {
                    return false;
                }
            }
        }

        return !isRunningClient || Control.TRINKET_ABILITY.isDown();
    }

    public void onActiveAbilityUpdate(PlayerMob player, ActiveBuff buff, Packet content) {
    }

    public void onActiveAbilityStopped(PlayerMob player, ActiveBuff buff) {
        player.buffManager.removeBuff(BuffRegistry.getBuff("hermesbootsactive"), false);
    }
}
