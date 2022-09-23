//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package level.presets;

import engine.registries.ItemRegistry;
import engine.registries.ObjectRegistry;
import engine.registries.TileRegistry;
import engine.util.GameRandom;
import inventory.InventoryItem;
import inventory.lootTable.LootItemInterface;
import inventory.lootTable.LootTable;
import inventory.lootTable.LootTablePresets;
import inventory.lootTable.lootItem.LootItem;
import inventory.lootTable.lootItem.OneOfLootItems;
import level.gameObject.GameObject;
import level.maps.presets.Preset;

import java.util.List;
import java.util.Random;

public class HeavenRuinsPreset extends Preset {
    public HeavenRuinsPreset() {
        super(28, 26);
        int floor = TileRegistry.getTileID("cloudstoneruinstile");
        int wall = ObjectRegistry.getObjectID("cloudstonewall");
        this.fillObject(12, 0, 4, 1, wall);
        this.fillObject(11, 1, 2, 1, wall); this.fillObject(15, 1, 2, 1, wall);
        this.fillObject(10, 2, 2, 1, wall); this.fillObject(16, 2, 2, 1, wall);
        this.fillObject(10, 2, 1, 4, wall); this.fillObject(17, 2, 1, 4, wall);
        this.fillObject(9, 5, 1, 3, wall); this.fillObject(18, 5, 1, 3, wall);
        this.fillObject(8, 7, 1, 6, wall); this.fillObject(19, 7, 1, 6, wall);
        this.fillObject(6, 12, 2, 1, wall); this.fillObject(20, 12, 2, 1, wall);
        this.fillObject(2, 13, 5, 1, wall); this.fillObject(21, 13, 5, 1, wall);
        this.fillObject(1, 14, 2, 1, wall); this.fillObject(25, 14, 2, 1, wall);
        this.fillObject(0, 15, 2, 1, wall); this.fillObject(26, 15, 2, 1, wall);
        this.fillObject(0, 17, 2, 1, wall); this.fillObject(26, 17, 2, 1, wall);
        this.fillObject(1, 18, 2, 1, wall); this.fillObject(25, 18, 2, 1, wall);
        this.fillObject(2, 19, 5, 1, wall); this.fillObject(21, 19, 5, 1, wall);
        this.fillObject(6, 20, 3, 1, wall); this.fillObject(19, 20, 3, 1, wall);
        this.fillObject(8, 21, 2, 1, wall); this.fillObject(18, 21, 2, 1, wall);
        this.fillObject(9, 22, 1, 1, wall); this.fillObject(18, 22, 1, 2, wall);
        this.fillObject(9, 23, 2, 1, wall); this.fillObject(17, 23, 2, 1, wall);
        this.fillObject(10, 24, 2, 1, wall); this.fillObject(16, 24, 2, 1, wall);
        this.fillObject(11, 25, 2, 1, wall); this.fillObject(15, 25, 2, 1, wall);

        this.fillObject(13, 25, 2, 1, ObjectRegistry.getObjectID("cloudstonedoor"), 2);
        this.fillObject(0, 16, 1, 1, ObjectRegistry.getObjectID("cloudstonedoor"), 1);
        this.fillObject(27, 16, 1, 1, ObjectRegistry.getObjectID("cloudstonedoor"), 3);

        this.fillTile(12, 0, 4, 26, floor);
        this.fillTile(11, 1, 6, 24, floor);
        this.fillTile(10, 2, 8, 22, floor);
        this.fillTile(9, 5, 10, 16, floor);
        this.fillTile(2, 13, 24, 7, floor);
        this.fillTile(0, 16, 28 , 1, floor);

        this.fillObject(12, 6, 1, 1, ObjectRegistry.getObjectID("cloudstonecolumn"));
        this.fillObject(15, 6, 1, 1, ObjectRegistry.getObjectID("cloudstonecolumn"));
        this.fillObject(8, 16, 1, 1, ObjectRegistry.getObjectID("cloudstonecolumn"));
        this.fillObject(19, 16, 1, 1, ObjectRegistry.getObjectID("cloudstonecolumn"));
        this.fillObject(13, 10, 1, 1, ObjectRegistry.getObjectID("cloudcaveentrance"));
        this.addSign("Pericula!\nIntra in pretium vitae tuae", 14, 10, ObjectRegistry.getObjectID("sign"));

        this.fillObject(20, 16, 1, 1, ObjectRegistry.getObjectID("oakchest"));
        this.addInventory(new LootTable(
                new LootItem("moonore", 12),
                new OneOfLootItems(
                        new LootItem("electrumsword"),
                        new LootItem("ambrosia", 8),
                        new LootItem("electrumstaff")
                ),
                LootTablePresets.basicCrate
        ), new GameRandom(), 20, 16);

        for (int x = 0; x < 28; x++) {
            for (int y = 0; y < 26; y++) {
                int rand = new Random().nextInt(100);
                if (getTile(x, y) == TileRegistry.getTileID("cloudstoneruinstile") && rand < 20) {
                    this.fillTile(x,y,1,1, TileRegistry.getTileID("cloudtile"));
                }
                if (getObject(x,y) == ObjectRegistry.getObjectID("cloudstonewall") && rand < 10) {
                    this.fillObject(x,y,1,1,ObjectRegistry.getObjectID("air"));
                }
            }
        }
    }

}
