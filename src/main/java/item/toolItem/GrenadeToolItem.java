package item.toolItem;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import engine.localization.Localization;
import engine.network.PacketReader;
import engine.network.packet.PacketSpawnProjectile;
import engine.util.GameRandom;
import entity.mobs.GameDamage;
import entity.mobs.PlayerMob;
import gfx.gameTooltips.ListGameTooltips;
import inventory.InventoryItem;
import inventory.PlayerInventorySlot;

import java.awt.Point;
import entity.projectile.GrenadeProjectile;

import inventory.item.toolItem.projectileToolItem.throwToolItem.ThrowToolItem;
import level.maps.Level;

public class GrenadeToolItem extends ThrowToolItem {
    public GrenadeToolItem() {
        this.stackSize = 100;
        this.animSpeed = 500;
        this.attackRange = 300;
        this.attackDmg = new GameDamage(60.0F);
        this.velocity = 100;
        this.rarity = Rarity.COMMON;
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "destructivetip"));
        return tooltips;
    }

    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        GameRandom random = new GameRandom((long)seed);
        Point targetPos = this.controlledRangePosition(random, x, y, player, item, 0, 40);
        int newRange = (int)player.getDistance((float)targetPos.x, (float)targetPos.y);
        GrenadeProjectile projectile = new GrenadeProjectile(player.x, player.y, (float)targetPos.x, (float)targetPos.y, this.getThrowingVelocity(item, player), newRange, this.getDamage(item), player);
        projectile.setLevel(level);
        projectile.resetUniqueID(random);
        level.entityManager.projectiles.addHidden(projectile);
        if (level.isServerLevel()) {
            level.getServer().network.sendToClientsAtExcept(new PacketSpawnProjectile(projectile), player.getServerClient(), player.getServerClient());
        }

        item.setAmount(item.getAmount() - 1);
        return item;
    }
}
