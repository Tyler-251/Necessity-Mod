package item.toolItem;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import engine.Screen;
import engine.localization.Localization;
import engine.network.PacketReader;
import engine.network.packet.PacketSpawnProjectile;
import engine.sound.SoundEffect;
import engine.util.GameRandom;
import entity.mobs.AttackAnimMob;
import entity.mobs.GameDamage;
import entity.mobs.PlayerMob;
import entity.mobs.GameDamage.DamageType;
import entity.projectile.Projectile;
import entity.projectile.SwampTomeProjectile;
import gfx.GameResources;
import gfx.gameTooltips.ListGameTooltips;
import inventory.InventoryItem;
import inventory.PlayerInventorySlot;
import inventory.item.toolItem.projectileToolItem.magicProjectileToolItem.MagicProjectileToolItem;
import level.maps.Level;

public class EarthenPowerTomeProjectileToolItem extends MagicProjectileToolItem {
    public EarthenPowerTomeProjectileToolItem() {
        super(600);
        this.rarity = Rarity.RARE;
        this.animSpeed = 600;
        this.attackDmg = new GameDamage(DamageType.MAGIC, 8.0F);
        this.velocity = 50;
        this.attackXOffset = 10;
        this.attackYOffset = 10;
        this.attackRange = 2500;
        this.knockback = 10;
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "earthenpowertometip"));
        tooltips.add(this.getAttackDamageTip(item, perspective));
        tooltips.add(this.getAttackSpeedTip(item, perspective));
        this.addCritChanceTip(tooltips, item, perspective);
        return tooltips;
    }

    public void showAttack(Level level, int x, int y, AttackAnimMob mob, int attackHeight, InventoryItem item, int seed, PacketReader contentReader) {
        if (level.isClientLevel()) {
            Screen.playSound(GameResources.magicbolt1, SoundEffect.effect(mob).volume(0.7F).pitch(GameRandom.globalRandom.getFloatBetween(1.0F, 1.1F)));
        }

    }

    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        GameRandom random = new GameRandom((long)seed);

        for(int i = -1; i < 2; ++i) {
            Projectile projectile = new SwampTomeProjectile(level, player, player.x, player.y, (float)x, (float)y, (float)this.getVelocity(item, player), this.getAttackRange(item), this.getDamage(item), this.getKnockback(item, player));
            projectile.resetUniqueID(random);
            level.entityManager.projectiles.addHidden(projectile);
            projectile.moveDist(20.0);
            projectile.setAngle(projectile.getAngle() + (float)(30 * i));
            if (level.isServerLevel()) {
                level.getServer().network.sendToClientsAtExcept(new PacketSpawnProjectile(projectile), player.getServerClient(), player.getServerClient());
            }
        }
        Projectile projectile = new SwampTomeProjectile(level, player, player.x, player.y, (float)x, (float)y, (float)this.getVelocity(item, player), this.getAttackRange(item), this.getDamage(item), this.getKnockback(item, player));
        projectile.resetUniqueID(random);
        level.entityManager.projectiles.addHidden(projectile);
        projectile.moveDist(20.0);
        projectile.setAngle(projectile.getAngle() + 180);
        if (level.isServerLevel()) {
            level.getServer().network.sendToClientsAtExcept(new PacketSpawnProjectile(projectile), player.getServerClient(), player.getServerClient());
        }

        return item;
    }
}
