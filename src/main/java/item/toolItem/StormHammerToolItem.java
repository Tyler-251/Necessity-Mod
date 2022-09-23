package item.toolItem;

import entity.levelEvent.StormTrailEvent;
import engine.network.PacketReader;
import engine.network.packet.PacketLevelEvent;
import entity.levelEvent.LevelEvent;
import entity.mobs.GameDamage;
import entity.mobs.PlayerMob;
import inventory.InventoryItem;
import inventory.PlayerInventorySlot;
import inventory.item.toolItem.swordToolItem.SwordToolItem;
import level.maps.Level;

public class StormHammerToolItem extends SwordToolItem {
    public StormHammerToolItem() {
            super(600);
            this.rarity = Rarity.RARE;
            this.animSpeed = 250;
            this.attackDmg = new GameDamage(GameDamage.DamageType.MELEE, 14.0F);
            this.attackRange = 70;
            this.knockback = 75;
        }

        public InventoryItem onAttack(Level
        level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
            item = super.onAttack(level, x, y, player, attackHeight, item, slot, animAttack, seed, contentReader);
            for (int i = 0; i < 3; i++) {
                LevelEvent event = new StormTrailEvent(player, this.getDamage(item), player.getX(), player.getY(), x, y + attackHeight, seed + i);
                level.entityManager.addLevelEventHidden(event);
                if (level.isServerLevel()) {
                    level.getServer().network.sendToClientsAtExcept(new PacketLevelEvent(event), player.getServerClient(), player.getServerClient());
                }
            }


            return item;
        }
}
