package item.toolItem;

import inventory.item.toolItem.swordToolItem.CustomSwordToolItem;

// Extends CustomSwordToolItem
public class DraculaBladeToolItem extends CustomSwordToolItem {

    // Weapon attack textures are loaded from resources/player/weapons/<itemStringID>

    public DraculaBladeToolItem() {
        super(Rarity.UNCOMMON,
                250,
                25,
                65,
                80,
                600);
    }

}
