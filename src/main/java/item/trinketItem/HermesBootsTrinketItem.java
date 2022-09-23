//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package item.trinketItem;

import engine.localization.Localization;
import engine.registries.BuffRegistry;
import entity.mobs.PlayerMob;
import entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import gfx.gameTooltips.ListGameTooltips;
import inventory.InventoryItem;
import inventory.item.trinketItem.TrinketItem;

public class HermesBootsTrinketItem extends TrinketItem {
    public HermesBootsTrinketItem() {
        super(Rarity.RARE, 500);
    }

    public ListGameTooltips getTrinketTooltips(InventoryItem item, PlayerMob perspective, boolean equipped) {
        ListGameTooltips tooltips = super.getTrinketTooltips(item, perspective, equipped);
        tooltips.add(Localization.translate("itemtooltip", "zephyrbootstip"));
        tooltips.add(Localization.translate("itemtooltip", "staminausertip"));
        return tooltips;
    }

    public TrinketBuff[] getBuffs(InventoryItem item) {
        return new TrinketBuff[]{(TrinketBuff)BuffRegistry.getBuff("hermesbootstrinket")};
    }
}
