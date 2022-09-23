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

public class HeavenRuinsSmallPreset extends Preset {
    public HeavenRuinsSmallPreset() {
        super(6, 10);
        int floor = TileRegistry.getTileID("cloudstoneruinstile");
        int wall = ObjectRegistry.getObjectID("cloudstonewall");
        this.boxObject(0,0,6,10, wall);
        this.fillObject(1,1,4, 8, TileRegistry.getTileID("air"));
        this.fillTile(0,0,6,10, floor);
        if (new Random().nextInt(100) < 50) {
            this.fillObject(1, 1, 1, 1, ObjectRegistry.getObjectID("oakchest"));
            this.addInventory(new LootTable(new LootItemInterface[]{
                    new LootItem("moonore", 12),
                    new OneOfLootItems(
                            new LootItem("electrumsword"),
                            new LootItem("ambrosia", 8),
                            new LootItem("electrumstaff")
                    ),
                    LootTablePresets.basicCrate
            }), new GameRandom(), 1, 1);
        }

        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 10; y++) {
                int rand = new Random().nextInt(100);
                if (getTile(x, y) == TileRegistry.getTileID("cloudstoneruinstile") && rand < 20) {
                    this.fillTile(x,y,1,1, TileRegistry.getTileID("cloudtile"));
                }
                if (getObject(x,y) == ObjectRegistry.getObjectID("cloudstonewall") && rand < 30) {
                    this.fillObject(x,y,1,1,ObjectRegistry.getObjectID("air"));
                }
            }
        }
    }
}
