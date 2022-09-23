package patch.getMobTable;

import engine.modLoader.annotations.ModMethodPatch;
import level.maps.Level;
import level.maps.biomes.MobSpawnTable;
import net.bytebuddy.asm.Advice;
import level.maps.biomes.Biome;
import patch.CloudMobTables;


@ModMethodPatch(target = Biome.class, name = "getMobSpawnTable", arguments = {Level.class})
public class BiomeGetMobSpawnTable {
    @Advice.OnMethodExit()
    static void onExit(@Advice.This Biome biome, @Advice.Argument(0) Level level, @Advice.Return(readOnly = false) MobSpawnTable table) {
        if (level.getDimension() == 68) {
            table = new MobSpawnTable().addLimited(20, "angel", 5, 20);
        }
    }
}
