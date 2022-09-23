package item.toolItem;

import engine.localization.Localization;
import engine.registries.ProjectileRegistry;
import entity.projectile.ElectrumStaffProjectile;
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

public class ElectrumStaffProjectileToolItem extends MagicProjectileToolItem {

    public ElectrumStaffProjectileToolItem() {
        super(400);

        this.rarity = Rarity.UNCOMMON;
        this.animSpeed = 200;
        this.cooldown = 300;
        this.attackDmg = new GameDamage(GameDamage.DamageType.MAGIC, 15.0F);
        this.velocity = 100;
        this.attackXOffset = 12;
        this.attackYOffset = 22;
        this.attackRange = 5000;
        this.knockback = 30;
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "electrumstafftip"));
        tooltips.add(this.getAttackDamageTip(item, perspective));
        tooltips.add(this.getAttackSpeedTip(item, perspective));
        this.addCritChanceTip(tooltips, item, perspective);
        return tooltips;
    }

    public void showAttack(Level level, int x, int y, AttackAnimMob mob, int attackHeight, InventoryItem item, int seed, PacketReader contentReader) {
        if (level.isClientLevel()) {
            Screen.playSound(GameResources.magicbolt2, SoundEffect.effect(mob).pitch(1.3F));
        }

    }

    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        GameRandom random = new GameRandom((long)seed);

        for(int i = 0; i < 2; ++i) {
            Projectile projectile = ProjectileRegistry.getProjectile("electrumstaffprojectile", level, player.x, player.y, (float)x, (float)y, (float)this.getVelocity(item, player), this.getAttackRange(item), this.getDamage(item), this.getKnockback(item, player), player);
            projectile.resetUniqueID(random);
            level.entityManager.projectiles.addHidden(projectile);
            projectile.moveDist(20.0);
            projectile.setAngle(projectile.getAngle() + (float)(10 * i));
            if (level.isServerLevel()) {
                level.getServer().network.sendToClientsAtExcept(new PacketSpawnProjectile(projectile), player.getServerClient(), player.getServerClient());
            }
        }

       return item;
   }
}