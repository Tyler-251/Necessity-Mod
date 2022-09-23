package level;

import engine.network.server.Server;
import engine.registries.ObjectRegistry;
import engine.registries.TileRegistry;
import engine.util.GameRandom;
import level.maps.generationModules.IslandGeneration;
import level.maps.Level;
import level.maps.generationModules.PresetGeneration;
import level.maps.presets.ElderHousePreset;
import level.maps.presets.Preset;
import level.presets.HeavenRuinsPreset;
import level.presets.HeavenRuinsSmallPreset;

import java.awt.*;

public class HeavenLevel extends Level {
    public HeavenLevel(int islandX, int islandY, int dimension, Server server) {
        super(300,300, islandX, islandY, dimension, false, server);
        generateLevel(150);
    }
    public void generateLevel(int islandSize){
        IslandGeneration islandGen = new IslandGeneration(this, islandSize);
        PresetGeneration presetGen = new PresetGeneration(this);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                setTile(x, y, TileRegistry.getTileID("cloudtile"));
            }
        }
        islandGen.generateLakes(0.03f, TileRegistry.waterID, TileRegistry.getTileID("cloudtile"), TileRegistry.getTileID("darkcloudtile"));
        for (int i = 0; i < 15; i++) {
            if (i < 2) presetGen.findRandomValidPositionAndApply(new GameRandom(), new HeavenRuinsPreset(), 4, true, true);
            presetGen.findRandomValidPositionAndApply(new GameRandom(), new HeavenRuinsSmallPreset(), 4, true, true);
        }
        islandGen.generateObjects(ObjectRegistry.getObjectID("livingtree"), TileRegistry.getTileID("cloudtile"), .01f);
        islandGen.generateObjects(ObjectRegistry.getObjectID("cloudsurfacerock"), TileRegistry.getTileID("cloudtile"), .007f);
        islandGen.generateFruitGrowerSingle("ambrosiabush", 15f, TileRegistry.getTileID("darkcloudtile"));
    }
}
