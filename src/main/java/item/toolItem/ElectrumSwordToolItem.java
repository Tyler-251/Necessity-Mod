package item.toolItem;

import entity.levelEvent.ShockTrailEvent;
import engine.network.PacketReader;
import engine.network.packet.PacketLevelEvent;
import entity.levelEvent.LevelEvent;
import entity.mobs.GameDamage;
import entity.mobs.PlayerMob;
import inventory.InventoryItem;
import inventory.PlayerInventorySlot;
import inventory.item.toolItem.swordToolItem.SwordToolItem;
import level.maps.Level;

public class ElectrumSwordToolItem extends SwordToolItem {
    public ElectrumSwordToolItem() {
        super(500);
        this.rarity = Rarity.UNCOMMON;
        this.animSpeed = 400;
        this.attackDmg = new GameDamage(GameDamage.DamageType.MELEE, 24.0F);
        this.attackRange = 70;
        this.knockback = 75;
    }

    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        item = super.onAttack(level, x, y, player, attackHeight, item, slot, animAttack, seed, contentReader);
        for (int i = 0; i < 2; i++) {
            LevelEvent event = new ShockTrailEvent(player, this.getDamage(item).addDamage(-12.0F), player.getX(), player.getY(), x, y + attackHeight, seed + i);
            level.entityManager.addLevelEventHidden(event);
            if (level.isServerLevel()) {
                level.getServer().network.sendToClientsAtExcept(new PacketLevelEvent(event), player.getServerClient(), player.getServerClient());
            }
        }

        return item;
    }
}
