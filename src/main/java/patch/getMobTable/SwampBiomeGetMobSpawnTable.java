package patch.getMobTable;

import engine.modLoader.annotations.ModMethodPatch;
import level.maps.Level;
import level.maps.biomes.SwampBiome;
import level.maps.biomes.MobSpawnTable;
import net.bytebuddy.asm.Advice;
import level.maps.biomes.Biome;
import patch.CloudMobTables;


@ModMethodPatch(target = SwampBiome.class, name = "getMobSpawnTable", arguments = {Level.class})
public class SwampBiomeGetMobSpawnTable {
    @Advice.OnMethodExit()
    static void onExit(@Advice.This SwampBiome biome, @Advice.Argument(0) Level level, @Advice.Return(readOnly = false) MobSpawnTable table) {
        if (level.getDimension() == 68) {
            table = new MobSpawnTable().add(20, "angel");
        }
    }
}
