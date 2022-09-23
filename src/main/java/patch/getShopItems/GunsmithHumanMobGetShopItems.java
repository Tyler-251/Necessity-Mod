package patch.getShopItems;

import engine.modLoader.annotations.ModMethodPatch;
import engine.network.server.ServerClient;
import engine.util.GameRandom;
import entity.mobs.friendly.human.humanShop.BlacksmithHumanMob;
import entity.mobs.friendly.human.humanShop.GunsmithHumanMob;
import inventory.InventoryItem;
import level.maps.levelData.villageShops.ShopItem;
import level.maps.levelData.villageShops.VillageShopsData;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bytecode.assign.Assigner;

import java.util.ArrayList;

import static entity.mobs.friendly.human.humanShop.HumanShop.conditionSection;


@ModMethodPatch(target = GunsmithHumanMob.class, name = "getShopItems", arguments = {VillageShopsData.class, ServerClient.class})
public class GunsmithHumanMobGetShopItems {
    @Advice.OnMethodExit()
    static void onExit(@Advice.This GunsmithHumanMob mob, @Advice.Argument(0) VillageShopsData data, @Advice.Argument(1) ServerClient client, @Advice.Return(readOnly = false) ArrayList<ShopItem> list) {
        ArrayList<ShopItem> tempArray = new ArrayList<ShopItem>();
        tempArray.add(ShopItem.item(new InventoryItem("grenade", 5), mob.getRandomHappinessPrice(new GameRandom(), 15, 55, 10)));
        tempArray.addAll(list);
        list = tempArray;
    }
}
