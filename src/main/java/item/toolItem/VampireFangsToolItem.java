package item.toolItem;

import inventory.item.toolItem.swordToolItem.CustomSwordToolItem;

// Extends CustomSwordToolItem
public class VampireFangsToolItem extends CustomSwordToolItem {

    // Weapon attack textures are loaded from resources/player/weapons/<itemStringID>

    public VampireFangsToolItem() {
        super( Rarity.COMMON,
                180,
                12,
                40,
                25,
                200);
    }

}
