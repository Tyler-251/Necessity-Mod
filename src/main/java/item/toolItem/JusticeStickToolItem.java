package item.toolItem;

import engine.localization.Localization;
import engine.network.PacketReader;
import engine.network.server.FollowPosition;
import engine.network.server.ServerClient;
import engine.registries.MobRegistry;
import entity.mobs.PlayerMob;
import entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import gfx.gameTooltips.ListGameTooltips;
import inventory.InventoryItem;
import inventory.PlayerInventorySlot;
import inventory.item.toolItem.summonToolItem.SummonToolItem;
import level.maps.Level;
public class JusticeStickToolItem extends SummonToolItem {
    public JusticeStickToolItem() {
        super("justice", FollowPosition.FLYING_CIRCLE, 4, 0.0F, Rarity.UNCOMMON, 0.25F, 400);
    }

    public void runSummon(Level level, int x, int y, ServerClient client, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        AttackingFollowingMob mob1 = (AttackingFollowingMob)MobRegistry.getMob("justice", level);
        this.summonMob(client, mob1, x, y, attackHeight, item);
        AttackingFollowingMob mob2 = (AttackingFollowingMob)MobRegistry.getMob("justice", level);
        this.summonMob(client, mob2, x, y, attackHeight, item);
        AttackingFollowingMob mob3 = (AttackingFollowingMob)MobRegistry.getMob("justice", level);
        this.summonMob(client, mob3, x, y, attackHeight, item);
        AttackingFollowingMob mob4 = (AttackingFollowingMob)MobRegistry.getMob("justice", level);
        this.summonMob(client, mob4, x, y, attackHeight, item);
    }
    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "justicsticktip"));
        return tooltips;
    }
}
