import engine.localization.message.StaticMessage;
import engine.network.server.ServerClient;
import entity.buffs.*;
import engine.localization.Localization;
import engine.localization.message.LocalMessage;
import engine.modLoader.annotations.ModEntry;
import engine.modifiers.ModifierTooltip;
import engine.modifiers.ModifierValue;
import engine.network.server.Server;
import engine.registries.*;
import engine.world.WorldGenerator;
import entity.levelEvent.ShockTrailEvent;
import entity.levelEvent.StormTrailEvent;
import entity.mobs.HumanTexture;
import entity.mobs.Mob;
import entity.mobs.PlayerMob;
import entity.mobs.buffs.*;
import entity.mobs.friendly.critters.BlueBalloonMob;
import entity.mobs.friendly.critters.RedBalloonMob;
import entity.mobs.friendly.critters.YellowBalloonMob;
import entity.mobs.hostile.AngelMob;
import entity.mobs.hostile.VampireMob;
import entity.mobs.hostile.bosses.AncientVultureMob;
import entity.mobs.hostile.bosses.QueenSpiderMob;
import entity.mobs.hostile.bosses.SwampGuardianHead;
import entity.mobs.summon.JusticeMob;
import entity.projectile.*;
import gfx.GameColor;
import gfx.gameTexture.GameTexture;
import gfx.gameTooltips.ListGameTooltips;
import inventory.InventoryItem;
import inventory.item.Item;
import inventory.item.armorItem.*;
import inventory.item.matItem.MatItem;
import inventory.item.placeableItem.StonePlaceableItem;
import inventory.item.placeableItem.consumableItem.food.FoodConsumableItem;
import inventory.item.toolItem.ToolType;
import inventory.item.toolItem.axeToolItem.CustomAxeToolItem;
import inventory.item.toolItem.glaiveToolItem.CustomGlaiveToolItem;
import inventory.item.toolItem.pickaxeToolItem.CustomPickaxeToolItem;
import inventory.item.toolItem.projectileToolItem.bowProjectileToolItem.CustomBowProjectileToolItem;
import inventory.item.toolItem.shovelToolItem.CustomShovelToolItem;
import inventory.item.toolItem.spearToolItem.CustomSpearToolItem;
import inventory.item.toolItem.swordToolItem.CustomSwordToolItem;
import inventory.lootTable.LootItemInterface;
import inventory.lootTable.LootTable;
import inventory.lootTable.lootItem.ConditionLootItem;
import item.toolItem.*;
import item.trinketItem.HermesBootsTrinketItem;
import inventory.lootTable.lootItem.ChanceLootItem;
import inventory.lootTable.lootItem.LootItem;
import inventory.recipe.Ingredient;
import inventory.recipe.Recipe;
import inventory.recipe.Recipes;
import level.HeavenCaveLevel;
import level.HeavenLevel;
import level.gameObject.*;
import level.gameTile.CloudTile;
import level.gameTile.DarkCloudTile;
import level.gameTile.EmptyCloudTile;
import level.gameTile.SimpleTiledFloorTile;
import level.maps.Level;
import level.maps.levelData.settlementData.settler.Settler;

import java.awt.*;
import java.util.stream.Stream;

@ModEntry
public class NecessityMod {

    public void init() {
//   TILES   //
        TileRegistry.registerTile("cloudtile", new CloudTile(),0,false);
        TileRegistry.registerTile("darkcloudtile", new DarkCloudTile(), 0, false);
        TileRegistry.registerTile("emptycloudtile", new EmptyCloudTile(),0,false);
        TileRegistry.registerTile("cloudstoneruinstile", new SimpleTiledFloorTile("cloudstoneruinsfloor", new Color(32, 137, 138)), 0, false);
//   OBJECTS   //
        //   ENVIRONMENT   //
        ObjectRegistry.registerObject("livingtree", new TreeObject("livingtree", "livinglog", "livingsapling", new Color(86, 69, 40), 45, 60, 110, "livingleaves"), 0.0F, false);
        ObjectRegistry.getObject("livingtree").lightLevel = 100; ObjectRegistry.getObject("livingtree").lightHue = 10f; ObjectRegistry.getObject("livingtree").lightSat = 140f;
        ObjectRegistry.registerObject("livingsapling", new SaplingObject("livingsapling", ObjectRegistry.getObjectID("livingtree"), 1800, 2700, true,"cloudtile"), 10.0F, true);
        RockObject cloudrock;
        ObjectRegistry.registerObject("cloudrock", cloudrock = new RockObject("cloudrock", new Color(150, 150, 150), "cloudstone"), 0.0F, false);
        ObjectRegistry.registerObject("darkcloudrock", new RockObject("darkcloudrock", new Color(140, 140, 140), "cloudstone"), 0.0F, false);
        ObjectRegistry.registerObject("moonorecloud", new RockOreObject(cloudrock, "oremask", "moonore", new Color(65, 0, 65), "moonore"), 0.0F, false);
        SurfaceRockObject.registerSurfaceRock("cloudsurfacerock", "cloudstone", new Color(88, 121, 112));
        ObjectRegistry.registerObject("ambrosiabush", (new FruitBushObject("ambrosiabush", "ambrosiasapling", 900.0F, 1800.0F, "ambrosia", 1.0F, 2, new Color(255, 226, 60))).setDebrisColor(new Color(174, 255, 240)), 0.0F, false);
        ObjectRegistry.registerObject("ambrosiasapling", new SaplingObject("ambrosiasapling", ObjectRegistry.getObjectID("ambrosiaBush"), 1200, 2100, false, "darkcloudtile"), 30.0F, true);

        //   PLACABLES   //
        ObjectRegistry.registerObject("cloudcaveentrance", new CloudCaveEntranceObject(), 0, false);
        ObjectRegistry.registerObject("cloudcaveexit", new CloudCaveExitObject(), 0, false);
        LadderDownObject.registerLadderPair("cloudladder", 69, new Color(255, 255, 255), Item.Rarity.RARE, 10);
        int[] cloudStoneWallIDs = WallObject.registerWallObjects("cloudstone", "cloudstonewall", 0, new Color(106, 227, 238), ToolType.PICKAXE, 2.0F, 6.0F);
        WallObject cloudStoneWall = (WallObject)ObjectRegistry.getObject(cloudStoneWallIDs[0]);
        ObjectRegistry.registerObject("cloudstonecolumn", new ColumnObject("cloudstonecolumn", new Color(0, 124, 124), ToolType.PICKAXE), 0, true);

//   BUFFS   //
        BuffRegistry.registerBuff("electrumhelmetsetbonus", new ElectrumHelmetSetBonusBuff());
        BuffRegistry.registerBuff("electrumhoodsetbous", new ElectrumHoodSetBonusBuff());
        BuffRegistry.registerBuff("goldhoodsetbonus", new GoldHoodSetBonusBuff());
        BuffRegistry.registerBuff("vampirebuff",new VampireSetBonusBuff());
        BuffRegistry.registerBuff("hermesbootstrinket",new HermesBootsTrinketBuff());
        BuffRegistry.registerBuff("hermesbootsactive",new HermesBootsActiveBuff());
        BuffRegistry.registerBuff("sunstonehelmetsetbonusbuff", new SunstoneHelmetSetBonusBuff());
        BuffRegistry.registerBuff("sunstonehoodsetbonusbuff", new SunstoneHoodSetBonusBuff());
        BuffRegistry.registerBuff("moonstonehatsetbonusbuff", new MoonstoneHatSetBonusBuff());
        BuffRegistry.registerBuff("moonstonemasksetbonusbuff", new MoonstoneMaskSetBonusBuff());


//   ITEMS   //
        //   VAMPIRE ARMOR   //
        ItemRegistry.registerItem("vampirehelmet", new SetHelmetArmorItem(5,300,Item.Rarity.UNCOMMON,"vampirehelmet","vampirechestplate","vampireboots","vampirebuff") {
            public ArmorModifiers getArmorModifier(InventoryItem item, Mob mob) {
                return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.CRIT_CHANCE,.05F)});
            }
        }, 50.0F, true);
        ItemRegistry.registerItem("vampirechestplate", new ChestArmorItem(6, 300, Item.Rarity.UNCOMMON, "vampirechest", "vampirearms") {
            public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
                return new ArmorModifiers(new ModifierValue[]{(new ModifierValue(BuffModifiers.MAGIC_ATTACK_SPEED,0.1F))});
            }
            public Stream<ModifierTooltip> getArmorModifierTooltips(InventoryItem item, Mob mob) {
                return Stream.of(new ModifierTooltip(GameColor.WHITE, new LocalMessage("itemtooltip", "vampirechest")));
            }
        }, 75.0F, true);
        ItemRegistry.registerItem("vampireboots", new BootsArmorItem(4, 300, Item.Rarity.UNCOMMON, "vampireboots") {
            public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
                return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.SPEED, 0.1F)});
            }
        }, 40.0F, true);

        //   GOLD ARMOR   //
        ItemRegistry.registerItem("goldhood", new SetHelmetArmorItem(4,300,Item.Rarity.NORMAL,"goldhood","goldchestplate","goldboots","goldhoodsetbonus") {
            public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
                return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.PROJECTILE_VELOCITY,.10F),new ModifierValue(BuffModifiers.RANGED_ATTACK_SPEED,.05F)});
            }
        },75.0F,true);

        ItemRegistry.replaceItem("goldhelmet", new SetHelmetArmorItem(6, 300, "goldhelmet", "goldchestplate", "goldboots", "goldsetbonus") {
            public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
                return  new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.MELEE_ATTACK_SPEED,.05F)});
            }
        }, 50.0F, true);

        //   DEMONIC ARMOR   //
        ItemRegistry.replaceItem("demonichelmet", new SetHelmetArmorItem(11, 600, Item.Rarity.COMMON, "demonichelmet", "demonicchestplate", "demonicboots", "demonicsetbonus") {
            public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
                return  new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.MELEE_ATTACK_SPEED,.05F), new ModifierValue(BuffModifiers.MELEE_DAMAGE,.10F), new ModifierValue(BuffModifiers.ARMOR_FLAT,3)});
            }
        }, 50.0F, true);
        ItemRegistry.registerItem("demonichood", new SetHelmetArmorItem(8,600,Item.Rarity.COMMON,"demonichood","demonicchestplate","demonicboots","demonicsetbonus") {
            public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
                return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.PROJECTILE_VELOCITY,.15F),new ModifierValue(BuffModifiers.RANGED_ATTACK_SPEED,.10F), new ModifierValue(BuffModifiers.ARMOR_FLAT,2)});
            }
        },50.0F,true);

        //   ELECTRUM ARMOR   //
        ItemRegistry.registerItem("electrumhelmet", new SetHelmetArmorItem(15, 600, Item.Rarity.COMMON, "electrumhelmet", "electrumchestplate", "electrumboots", "electrumhelmetsetbonus") {
            public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
                return  new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.ARMOR_PEN,.15F), new ModifierValue(BuffModifiers.MELEE_DAMAGE,.10F)});
            }
        }, 50.0F, true);

        ItemRegistry.registerItem("electrumhood", new SetHelmetArmorItem(11,600,Item.Rarity.COMMON,"electrumhood","electrumchestplate","electrumboots","electrumhoodsetbonus") {
            public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
                return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.PROJECTILE_VELOCITY,.15F),new ModifierValue(BuffModifiers.RANGED_ATTACK_SPEED,.10F)});
            }
        },50.0F,true);
        ItemRegistry.registerItem("electrumchestplate", new ChestArmorItem(15, 300, Item.Rarity.UNCOMMON, "electrumchestplate", "electrumarms") {
            public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
                return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.CRIT_CHANCE,0.1F)});
            }
            public Stream<ModifierTooltip> getArmorModifierTooltips(InventoryItem item, Mob mob) {
                return Stream.of(new ModifierTooltip(GameColor.WHITE, new LocalMessage("itemtooltip", "electrumchest")));
            }
        }, 75.0F, true);
        ItemRegistry.registerItem("electrumboots", new BootsArmorItem(9, 300, Item.Rarity.UNCOMMON, "electrumboots") {
            public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
                return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.SPEED, 0.15F)});
            }
        }, 40.0F, true);

        //   SUNSTONE ARMOR   //
        ItemRegistry.registerItem("sunstonehelmet", new SetHelmetArmorItem(14,900,Item.Rarity.UNCOMMON,"sunstonehelmet","sunstonechestplate","sunstoneboots","sunstonehelmetsetbonusbuff") {
            public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
                return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.CRIT_CHANCE,0.15F), new ModifierValue(BuffModifiers.ARMOR_PEN,.1F)});
            }
        },100.0F,true);

        ItemRegistry.registerItem("sunstonehood", new SetHelmetArmorItem(12,900,Item.Rarity.UNCOMMON,"sunstonehood","sunstonechestplate","sunstoneboots","sunstonehoodsetbonusbuff") {
            public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
                return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.RANGED_ATTACK_SPEED,0.15F)});
            }
        },100.0F,true);

        ItemRegistry.registerItem("sunstonechestplate",new ChestArmorItem(18, 900, Item.Rarity.UNCOMMON,"sunstonechestplate","sunstonearms"){
            public ArmorModifiers getArmorModifier(InventoryItem item, Mob mob) {
                return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.REGEN,0.5F), new ModifierValue(BuffModifiers.ARMOR_PEN,0.99F)});
            }
        },100.0F,true);

        ItemRegistry.registerItem("sunstoneboots", new BootsArmorItem(10, 300, Item.Rarity.UNCOMMON, "sunstoneboots") {
            public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
                return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.SPEED, 0.15F)});
            }
        }, 40.0F, true);

        //   MOONSTONE ARMOR   //
        SetHelmetArmorItem moonstonehat = new SetHelmetArmorItem(10,900,Item.Rarity.UNCOMMON,"moonstonehat","moonstonechestplate","moonstoneboots","moonstonehatsetbonusbuff") {
            public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
                return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.CRIT_CHANCE,0.15F), new ModifierValue(BuffModifiers.ARMOR_PEN,.1F)});
            }
        };
        ItemRegistry.registerItem("moonstonehat", moonstonehat,100.0F,true);

        ItemRegistry.registerItem("moonstonemask", new SetHelmetArmorItem(12,900,Item.Rarity.UNCOMMON,"moonstonemask","moonstonechestplate","moonstoneboots","moonstonemasksetbonusbuff") {
            public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
                return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.CRIT_CHANCE,0.15F), new ModifierValue(BuffModifiers.ARMOR_PEN,.1F)});
            }
        },100.0F,true);

        ItemRegistry.registerItem("moonstonechestplate",new ChestArmorItem(18, 900, Item.Rarity.UNCOMMON,"moonstonechestplate","moonstonearms"){
            public ArmorModifiers getArmorModifier(InventoryItem item, Mob mob) {
                return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.REGEN,0.5F), new ModifierValue(BuffModifiers.ARMOR_PEN,0.99F)});
            }
        },100.0F,true);

        ItemRegistry.registerItem("moonstoneboots", new BootsArmorItem(10, 300, Item.Rarity.UNCOMMON, "moonstoneboots") {
            public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
                return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.SPEED, 0.18F)});
            }
        }, 40.0F, true);

        //   NIGHTMARE ARMOR   //
        ItemRegistry.registerItem("nightmarehelmet", new SetHelmetArmorItem(18,900,Item.Rarity.UNCOMMON,"nightmarehelmet","nightmarechestplate","nightmareboots","nightmarehelmetsetbonus") {
            public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
                return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.CRIT_CHANCE,0.15F), new ModifierValue(BuffModifiers.ARMOR_PEN,.1F)});
            }
        },100.0F,true);

        ItemRegistry.registerItem("nightmarechestplate",new ChestArmorItem(15, 900, Item.Rarity.UNCOMMON,"moonstonechestplate","moonstonearms"){
            public ArmorModifiers getArmorModifier(InventoryItem item, Mob mob) {
                return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.REGEN,0.5F), new ModifierValue(BuffModifiers.ARMOR_PEN,0.99F)});
            }
        },100.0F,true);

        ItemRegistry.registerItem("nightmareboots", new BootsArmorItem(9, 300, Item.Rarity.UNCOMMON, "moonstoneboots") {
            public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
                return new ArmorModifiers(new ModifierValue[]{new ModifierValue(BuffModifiers.SPEED, 0.2F)});
            }
        }, 40.0F, true);

        //   ETC   //
        ItemRegistry.registerItem("alucardbane", new AlucardBaneToolItem(), 500,true);
        ItemRegistry.registerItem("ambrosia", (new FoodConsumableItem(100, Item.Rarity.UNCOMMON, Settler.FOOD_FINE, 20, 240, new ModifierValue[]{new ModifierValue(BuffModifiers.REGEN, 0.1F), new ModifierValue(BuffModifiers.COMBAT_REGEN, 0.1f), new ModifierValue(BuffModifiers.MAX_HEALTH, 0.1f)})).addGlobalIngredient(new String[]{"anycompostable", "anyfruit"}).setItemCategory(new String[]{"consumable", "rawfood"}), 20.0F, true);
        ItemRegistry.registerItem("batarang", new BatarangToolItem(),80,true);
        ItemRegistry.registerItem("cloudstone", new StonePlaceableItem(500), 0.1F, true);
        ItemRegistry.registerItem("draculablade", new DraculaBladeToolItem(),150,true);
        ItemRegistry.registerItem("earthenpowertome", new EarthenPowerTomeProjectileToolItem(), 250, true);
        ItemRegistry.registerItem("electrumbar",(new MatItem(100)).setItemCategory(new String[]{"materials","bars"}),25.0F,true);
        ItemRegistry.registerItem("electrumbow", new CustomBowProjectileToolItem(Item.Rarity.COMMON, 400, 23, 850, 220, 800, 8, 26), 150.0F, true);
        ItemRegistry.registerItem("electrumbattleaxe", new CustomSwordToolItem(Item.Rarity.COMMON, 500, 56, 70, 100, 600), 180.0F, true);
        ItemRegistry.registerItem("electrumglaive", new CustomGlaiveToolItem(Item.Rarity.COMMON, 400, 22, 140, 75, 102, 450), 160.0F, true);
        ItemRegistry.registerItem("electrumspear", new CustomSpearToolItem(Item.Rarity.COMMON, 360 , 24, 120, 25, 600), 150.0F, true);
        ItemRegistry.registerItem("electrumstaff", new ElectrumStaffProjectileToolItem(),150,true);
        ItemRegistry.registerItem("electrumsword",new ElectrumSwordToolItem(),100,true);
        ItemRegistry.registerItem("fungalstaff", new FungalStaffProjectileToolItem(),100,true);
        ItemRegistry.registerItem("grenade", new GrenadeToolItem(), 0, true);
        ItemRegistry.registerItem("hermesboots", new HermesBootsTrinketItem(), 300.0F, true);
        ItemRegistry.registerItem("justicestick", new JusticeStickToolItem(),200,true);
        ItemRegistry.registerItem("lisamemora", new LisaMemoraToolItem(), 250,true);
        ItemRegistry.registerItem("livinglog", (new MatItem(250, new String[]{"anylog"})).setItemCategory(new String[]{"materials", "logs"}), 4.0F, true);
        ItemRegistry.registerItem("moonore", (new MatItem(250)).setItemCategory(new String[]{"materials","ore"}), 10.0F, true);
        ItemRegistry.registerItem("silversilk", (new MatItem(100)).setItemCategory(new String[]{"materials","mobdrops"}),15.0F,true);
        ItemRegistry.registerItem("stormhammer", new StormHammerToolItem(),1200,true);
        ItemRegistry.registerItem("vampirefangs", new VampireFangsToolItem(), 100, true);
        ItemRegistry.registerItem("sunstone", (new MatItem(250)).setItemCategory(new String[]{"materials","ore"}),20.0F,true);

        //   TOOLS   //
        ItemRegistry.registerItem("electrumpickaxe", new CustomPickaxeToolItem(450, 120, 1, 18, 50, 50, 600, Item.Rarity.COMMON) {
            public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective) {
                ListGameTooltips tooltips = super.getTooltips(item, perspective);
                tooltips.add(Localization.translate("itemtooltip", "electrumpickaxetooltip"));
                return tooltips;
            }
        }, 120.0F, true);
        ItemRegistry.registerItem("electrumshovel", new CustomShovelToolItem(450, 120, 1, 18, 50, 50, 600, Item.Rarity.COMMON), 120.0F, true);
        ItemRegistry.registerItem("electrumaxe", new CustomAxeToolItem(450, 120, 1, 18, 50, 50, 600, Item.Rarity.COMMON), 120.0F, true);

//   MOBS   //
        MobRegistry.registerMob("justice", JusticeMob.class, false);
        MobRegistry.registerMob("redballoon", RedBalloonMob.class, false);
        MobRegistry.registerMob("blueballoon", BlueBalloonMob.class, false);
        MobRegistry.registerMob("yellowballoon", YellowBalloonMob.class, false);
        MobRegistry.registerMob("angel", AngelMob.class,true);
//        MobRegistry.registerMob("angelarcher", AngelArcherMob.class,true)

//   PROJECTILES   //
        ProjectileRegistry.registerProjectile("batarangprojectile", BatarangProjectile.class, "batarang", "batarang_shadow");
        ProjectileRegistry.registerProjectile("grenadeprojectile", GrenadeProjectile.class, "grenade", "grenade_shadow");
        ProjectileRegistry.registerProjectile("fungalstaffprojectile", FungalStaffProjectile.class,"fungalstaffprojectile","fungalstaffprojectile");
        ProjectileRegistry.registerProjectile("electrumstaffprojectile", ElectrumStaffProjectile.class,"balllightningprojectile","balllightningprojectile_shadow");
        ProjectileRegistry.registerProjectile("sunrayprojectile", SunRayProjectile.class,"sunrayprojectile","balllightningprojectile_shadow");

//   LEVEL EVENT   //
        LevelEventRegistry.registerEvent("stormtrail", StormTrailEvent.class);
        LevelEventRegistry.registerEvent("shocktrail", ShockTrailEvent.class);

//   WORLD GEN   //
        WorldGenerator.registerGenerator(new WorldGenerator() {
            @Override
            public Level getNewLevel(int islandX, int islandY, int dimension, Server server) {
                HeavenLevel newHeaven = new HeavenLevel(islandX, islandY, dimension, server);
                if (dimension == 69) { // HEAVEN
                    return newHeaven;
                }
                if (dimension == 68) { // HEAVEN CAVE
                    return new HeavenCaveLevel(islandX, islandY, dimension, server);
                }
                return null;
            }

        });
//   MUSIC   //
        MusicRegistry.registerMusic("cloudsurface", "music/cloudhaven",  new LocalMessage("sound/music", "cloudsurface"), new StaticMessage("Cloud Haven"), Color.YELLOW, Color.WHITE);
        MusicRegistry.registerMusic("cloudcave", "music/cloudmisbehaven",  new LocalMessage("sound/music", "cloudcave"), new StaticMessage("Cloud Misbehaven"), Color.YELLOW, Color.BLUE);
    }

    public void initResources() {
        JusticeMob.texture = GameTexture.fromFile("mobs/justice");
        RedBalloonMob.texture = GameTexture.fromFile("mobs/redballoon");
        BlueBalloonMob.texture = GameTexture.fromFile("mobs/blueballoon");
        YellowBalloonMob.texture = GameTexture.fromFile("mobs/yellowballoon");
        AngelMob.texture = new HumanTexture(GameTexture.fromFile("mobs/angel"), GameTexture.fromFile("mobs/angelarms_left"), GameTexture.fromFile("mobs/angelarms_right"));
    }

    public void postInit() {
//   LOOT TABLES   //
        VampireMob.lootTable.items.add(new ChanceLootItem(.03f,"justicestick"));
        VampireMob.lootTable.items.add(new ChanceLootItem(.03F,"vampirefangs"));
        QueenSpiderMob.lootTable.items.add(new LootItem("silversilk",12));
        SwampGuardianHead.privateLootTable.items.add(new LootTable(new LootItemInterface[]{(new ConditionLootItem("cloudladderdown", (r, o) -> {
            ServerClient client = (ServerClient)LootTable.expectExtra(ServerClient.class, o, 1);
            return client != null && client.characterStats().mob_kills.getKills("swampguardian") == 0;
        }))}));
        AncientVultureMob.lootTable.items.add(new LootItem("sunstone",12));

//   RECIPES   //
        Recipe goldhoodrecipe = new Recipe(
                "goldhood",
                1,
                RecipeTechRegistry.IRON_ANVIL,
                new Ingredient[]{
                        new Ingredient("goldbar", 12)
                }
        );
        goldhoodrecipe.showAfter("goldhelmet");
        Recipes.registerModRecipe(goldhoodrecipe);

        Recipe vampirehelmetrecipe = new Recipe(
                "vampirehelmet",
                1,
                RecipeTechRegistry.IRON_ANVIL,
                new Ingredient[]{
                        new Ingredient("batwing", 12),
                        new Ingredient("rawmutton", 1)
                }
        );
        vampirehelmetrecipe.showBefore("vampirechestplate");
        Recipes.registerModRecipe(vampirehelmetrecipe);

        Recipe vampirechestplaterecipe = new Recipe(
                "vampirechestplate",
                1,
                RecipeTechRegistry.IRON_ANVIL,
                new Ingredient[]{
                        new Ingredient("batwing",15),
                        new Ingredient("rawmutton", 2)
                }
        );
        vampirechestplaterecipe.showBefore("vampireboots");
        Recipes.registerModRecipe(vampirechestplaterecipe);

        Recipe vampirebootsrecipe = new Recipe(
                "vampireboots",
                1,
                RecipeTechRegistry.IRON_ANVIL,
                new Ingredient[]{
                        new Ingredient("batwing", 6),
                        new Ingredient("rawmutton",1)
                }
        );
        vampirebootsrecipe.showBefore("vampirefangs");
        Recipes.registerModRecipe(vampirebootsrecipe);

        Recipe vampirefangsrecipe = new Recipe(
                "vampirefangs",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("ironbar", 2),
                        new Ingredient("batwing", 10),
                        new Ingredient("rawmutton", 1)
                }
        );
        vampirefangsrecipe.showBefore("batarang");
        Recipes.registerModRecipe(vampirefangsrecipe);

        Recipe batarangrecipe = new Recipe(
                "batarang",
                1,
                RecipeTechRegistry.IRON_ANVIL,
                new Ingredient[]{
                        new Ingredient("batwing",10),
                        new Ingredient("rawmutton",1)
                }
        );
        batarangrecipe.showBefore("frosthelmet");
        Recipes.registerModRecipe(batarangrecipe);

        Recipe electrumbarrecipe = new Recipe(
                "electrumbar",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("silversilk",1),
                        new Ingredient("goldbar",1),
                }
        );
        electrumbarrecipe.showAfter("bloodvolley");
        Recipes.registerModRecipe(electrumbarrecipe);

        Recipe electrumpickaxerecipe = new Recipe(
                "electrumpickaxe",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("electrumbar",10)
                }
        );
        electrumpickaxerecipe.showAfter("electrumbar");
        Recipes.registerModRecipe(electrumpickaxerecipe);

        Recipe electrumaxerecipe = new Recipe(
                "electrumaxe",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("electrumbar",10)
                }
        );
        electrumaxerecipe.showAfter("electrumpickaxe");
        Recipes.registerModRecipe(electrumaxerecipe);

        Recipe electrumshovelrecipe = new Recipe(
                "electrumshovel",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("electrumbar",10)
                }
        );
        electrumshovelrecipe.showAfter("electrumaxe");
        Recipes.registerModRecipe(electrumshovelrecipe);

        Recipe electrumswordrecipe = new Recipe(
                "electrumsword",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("electrumbar",12)
                }
        );
        electrumswordrecipe.showAfter("electrumshovel");
        Recipes.registerModRecipe(electrumswordrecipe);

        Recipe electrumspearrecipe = new Recipe(
                "electrumspear",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("electrumbar",18)
                }
        );
        electrumshovelrecipe.showAfter("electrumsword");
        Recipes.registerModRecipe(electrumspearrecipe);

        Recipe electrumglaiverecipe = new Recipe(
                "electrumglaive",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("electrumbar",20)
                }
        );
        electrumglaiverecipe.showAfter("electrumspear");
        Recipes.registerModRecipe(electrumglaiverecipe);

        Recipe electrumbowrecipe = new Recipe(
                "electrumbow",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("electrumbar",12)
                }
        );
        electrumbowrecipe.showAfter("electrumglaive");
        Recipes.registerModRecipe(electrumbowrecipe);

        Recipe electrumstaffrecipe = new Recipe(
                "electrumstaff",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("electrumbar",12),
                        new Ingredient("voidshard",12)
                }
        );
        electrumstaffrecipe.showAfter("electrumbow");
        Recipes.registerModRecipe(electrumstaffrecipe);

        Recipe electrumhoodrecipe = new Recipe(
                "electrumhood",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("electrumbar",12)
                }
        );
        electrumhoodrecipe.showAfter("electrumstaff");
        Recipes.registerModRecipe(electrumhoodrecipe);

        Recipe electrumhelmetrecipe = new Recipe(
                "electrumhelmet",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("electrumbar",12)
                }
        );
        electrumhelmetrecipe.showAfter("electrumhood");
        Recipes.registerModRecipe(electrumhelmetrecipe);

        Recipe electrumchestplaterecipe = new Recipe(
                "electrumchestplate",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("electrumbar",12)
                }
        );
        electrumchestplaterecipe.showAfter("electrumhelmet");
        Recipes.registerModRecipe(electrumchestplaterecipe);

        Recipe electrumbootsrecipe = new Recipe(
                "electrumboots",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("electrumbar",12)
                }
        );
        electrumbootsrecipe.showAfter("electrumchestplate");
        Recipes.registerModRecipe(electrumbootsrecipe);

        Recipe hermesbootsrecipe = new Recipe(
                "hermesboots",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("zephyrboots",1),
                        new Ingredient("electrumbar",8),
                        new Ingredient("voidshard",12)
                }
        );
        hermesbootsrecipe.showAfter("electrumboots");
        Recipes.registerModRecipe(hermesbootsrecipe);

        Recipe draculabladerecipe = new Recipe(
                "draculablade",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("vampirefangs",1),
                        new Ingredient("batwing", 15),
                        new Ingredient("demonicbar",10)
                }
        );
        draculabladerecipe.showAfter("hermesboots");
        Recipes.registerModRecipe(draculabladerecipe);

        Recipe lisamemorarecipe = new Recipe(
                "lisamemora",
                1,
                RecipeTechRegistry.DEMONIC,
                new Ingredient[]{
                        new Ingredient("draculablade",1),
                        new Ingredient("batwing",20),
                        new Ingredient("voidshard",10),
                        new Ingredient("quartz",8),
                        new Ingredient("ivybar",8)
                }
        );
        lisamemorarecipe.showAfter("draculablade");
        Recipes.registerModRecipe(lisamemorarecipe);

        Recipe alucardbanerecipe = new Recipe(
                "alucardbane",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("lisamemora",1),
                        new Ingredient("batwing",25),
                        new Ingredient("tungstenbar",12),
                        new Ingredient("lifequartz", 4)
                }
        );
        alucardbanerecipe.showAfter("lisamemora");
        Recipes.registerModRecipe(alucardbanerecipe);

        Recipe stormhammerrecipe = new Recipe(
                "stormhammer",
                1,
                RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[]{
                        new Ingredient("lightninghammer",1),
                        new Ingredient("tungstenbar",15),
                        new Ingredient("quartz",8)

                }
        );
        stormhammerrecipe.showAfter("tungstenspear");
        Recipes.registerModRecipe(stormhammerrecipe);

        Recipe fungalstaffrecipe = new Recipe(
                "fungalstaff",
                1,
                RecipeTechRegistry.IRON_ANVIL,
                new Ingredient[]{
                        new Ingredient("mushroom",5),
                        new Ingredient("clay",10)
                }
        );
        fungalstaffrecipe.showAfter("ironsword");
        Recipes.registerModRecipe(fungalstaffrecipe);

        Recipe cloudstonecolumnrecipe = new Recipe(
                "cloudstonecolumn",
                1,
                RecipeTechRegistry.CARPENTER,
                new Ingredient[]{
                        new Ingredient("cloudstone", 20)
                }
        );
        cloudstonecolumnrecipe.showAfter("deepsandstonecolumn");
        Recipes.registerModRecipe(cloudstonecolumnrecipe);

        Recipe cloudstonewallrecipe = new Recipe(
                "cloudstonewall",
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("cloudstone", 5)
                }
        );
        cloudstonewallrecipe.showAfter("deepstonetiledfloor");
        Recipes.registerModRecipe(cloudstonewallrecipe);

        Recipe cloudstonedoorrecipe = new Recipe(
            "cloudstonedoor",
            RecipeTechRegistry.WORKSTATION,
            new Ingredient[]{
                    new Ingredient("cloudstone", 15)
            }
        );
        cloudstonedoorrecipe.showAfter("cloudstonewall");
        Recipes.registerModRecipe(cloudstonedoorrecipe);
    }
}

