package item.toolItem;

import engine.Screen;
import engine.network.PacketReader;
import engine.network.gameNetworkData.GNDItemMap;
import engine.network.packet.PacketSpawnProjectile;
import engine.registries.ProjectileRegistry;
import engine.sound.SoundEffect;
import engine.util.GameRandom;
import entity.levelEvent.toolItemEvent.ToolItemEvent;
import entity.mobs.AttackAnimMob;
import entity.mobs.PlayerMob;
import entity.mobs.buffs.BuffModifiers;
import entity.projectile.Projectile;
import gfx.GameResources;
import inventory.InventoryItem;
import inventory.PlayerInventorySlot;
import inventory.enchants.ToolItemModifiers;
import inventory.item.toolItem.swordToolItem.CustomSwordToolItem;
import level.maps.Level;

import java.util.HashMap;

public class AlucardBaneToolItem extends CustomSwordToolItem {

    private final int velocity;

    public AlucardBaneToolItem() {
        super(Rarity.EPIC,
                300,
                28,
                85,
                40,
                1000);
//        this.attackXOffset = 4;
//        this.attackYOffset = 4;
        this.velocity = 400;
    }


//    public void setDrawAttackRotation(InventoryItem item, ItemAttackDrawOptions drawOptions, float attackDirX, float attackDirY, float attackProgress) {
//        if (this.animInverted) {
//            drawOptions.swingRotationInv(attackProgress);
//        } else {
//            drawOptions.swingRotation(attackProgress);
//        }
//
//    }

    public void showAttack(Level level, int x, int y, AttackAnimMob mob, int attackHeight, InventoryItem item, int seed, PacketReader contentReader) {
        if (level.isClientLevel()) {
            Screen.playSound(GameResources.swing2, SoundEffect.effect(mob));
        }
    }
    public int getVelocity(InventoryItem item, PlayerMob player) {
        GNDItemMap gndData = item.getGndData();
        int velocity = gndData.hasKey("velocity") ? gndData.getInt("velocity") : this.velocity;
        return Math.round((Float)this.getEnchantment(item).applyModifier(ToolItemModifiers.VELOCITY, ToolItemModifiers.VELOCITY.defaultBuffManagerValue) * (float)velocity * (Float)player.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY));
    }
    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        //sword attack
        if (animAttack == 0) {
            int animSpeed = this.getAnimSpeed(item, player);
            ToolItemEvent event = new ToolItemEvent(player, seed, item, x - player.getX(), y - player.getY() + attackHeight, animSpeed, animSpeed, new HashMap());
            level.entityManager.addLevelEventHidden(event);
        }
        //projectile attack
        Projectile projectile = ProjectileRegistry.getProjectile("bloodbolt", level, player.x, player.y, (float)x, (float)y, (float)this.getVelocity(item, player), this.getAttackRange(item) * 10, this.getDamage(item), this.getKnockback(item, player), player);
        projectile.resetUniqueID(new GameRandom((long)seed));
        level.entityManager.projectiles.addHidden(projectile);
        if (level.isServerLevel()) {
            level.getServer().network.sendToClientsAtExcept(new PacketSpawnProjectile(projectile), player.getServerClient(), player.getServerClient());
        }

        return item;
    }
}
