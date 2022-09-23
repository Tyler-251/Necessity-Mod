package patch.getCritterSpawnTable;

import engine.modLoader.annotations.ModMethodPatch;
import level.maps.Level;
import level.maps.biomes.MobSpawnTable;
import net.bytebuddy.asm.Advice;
import level.maps.biomes.DesertBiome;
import patch.CloudMobTables;

@ModMethodPatch(target = DesertBiome.class, name = "getCritterSpawnTable", arguments = {Level.class})
public class DesertBiomeGetCritterSpawnTable {
    @Advice.OnMethodExit()
    static void onExit(@Advice.This DesertBiome biome, @Advice.Argument(0) Level level, @Advice.Return(readOnly = false) MobSpawnTable table) {
        if (level.getDimension() == 69) {
            table = new MobSpawnTable().add(100, "redballoon").add(75, "blueballoon").add(50, "yellowballoon");
        } else if (level.getDimension() == 68) {
            table = biome.defaultCaveCritters;
        }
    }
}
