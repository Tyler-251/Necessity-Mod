package item.toolItem;

import entity.projectile.FungalStaffProjectile;
import engine.Screen;
import engine.network.PacketReader;
import engine.network.packet.PacketSpawnProjectile;
import engine.sound.SoundEffect;
import engine.util.GameRandom;
import entity.mobs.AttackAnimMob;
import entity.mobs.GameDamage;
import entity.mobs.PlayerMob;
import entity.projectile.Projectile;
import gfx.GameResources;
import gfx.gameTooltips.ListGameTooltips;
import inventory.InventoryItem;
import inventory.PlayerInventorySlot;
import inventory.item.toolItem.projectileToolItem.magicProjectileToolItem.MagicProjectileToolItem;
import level.maps.Level;

public class FungalStaffProjectileToolItem extends MagicProjectileToolItem {

    public FungalStaffProjectileToolItem() {
        super(400);

        this.rarity = Rarity.COMMON;
        this.animSpeed = 500;
        this.cooldown = 500;
        this.attackDmg = new GameDamage(GameDamage.DamageType.MAGIC, 20.0F);
        this.velocity = 100;
        this.attackXOffset = 12;
        this.attackYOffset = 22;
        this.attackRange = 500;
        this.knockback = 30;
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective);
        tooltips.add(this.getAttackDamageTip(item, perspective));
        return tooltips;
    }

    public void showAttack(Level level, int x, int y, AttackAnimMob mob, int attackHeight, InventoryItem item, int seed, PacketReader contentReader) {
        if (level.isClientLevel()) {
            Screen.playSound(GameResources.magicbolt1, SoundEffect.effect(mob).pitch(1.3F));
        }

    }

    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
//        super.onAttack(level, x, y, player, attackHeight, item, slot, animAttack, seed, contentReader);

        Projectile projectile = new FungalStaffProjectile(
                level, player,
                player.x, player.y,
                x, y,
                getVelocity(item, player),
                getAttackRange(item),
                getDamage(item),
                getKnockback(item, player)
        );

        GameRandom random = new GameRandom(seed);
        projectile.resetUniqueID(random);
        projectile.moveDist(40);
        level.entityManager.projectiles.addHidden(projectile);

        if (level.isServerLevel()) {
            level.getServer().network.sendToClientsAtExcept(new PacketSpawnProjectile(projectile), player.getServerClient(), player.getServerClient());
        }

        return item;
    }
}