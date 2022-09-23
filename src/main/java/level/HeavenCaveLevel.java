package level;

import engine.network.server.Server;
import engine.registries.ObjectRegistry;
import engine.registries.TileRegistry;
import engine.util.GameRandom;
import level.maps.generationModules.CaveGeneration;
import level.maps.Level;
import level.maps.generationModules.PresetGeneration;
import level.maps.light.GameLight;
import level.presets.HeavenRuinsCavePreset;
import level.presets.HeavenRuinsSmallPreset;

import java.awt.*;

public class HeavenCaveLevel extends Level {
    public HeavenCaveLevel(int islandX, int islandY, int dimesion, Server server) {
        super(300,300, islandX, islandY, dimesion, false, server);
        generateLevel();
    }
    public void generateLevel(){
        CaveGeneration caveGen = new CaveGeneration(this, "darkcloudtile","cloudrock");
        PresetGeneration presetGen = new PresetGeneration(this);
        caveGen.generateLevel();
        for (int i = 0; i < 20; i++) presetGen.findRandomValidPositionAndApply(new GameRandom(), new HeavenRuinsCavePreset(), 0, true, true);
        caveGen.generateSmoothOreVeins(0.1f, 5, 10, ObjectRegistry.getObjectID("moonorecloud"));
        this.isCave = true;
    }

}
