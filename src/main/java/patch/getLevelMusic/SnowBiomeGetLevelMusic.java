package patch.getLevelMusic;

import engine.localization.message.LocalMessage;
import engine.localization.message.StaticMessage;
import engine.modLoader.annotations.ModMethodPatch;
import engine.registries.MusicRegistry;
import entity.mobs.PlayerMob;
import gfx.GameMusic;
import level.maps.Level;
import level.maps.biomes.SnowBiome;
import net.bytebuddy.asm.Advice;
import level.maps.biomes.Biome;

import java.awt.*;
import java.util.Collections;
import java.util.List;


@ModMethodPatch(target = SnowBiome.class, name = "getLevelMusic", arguments = {Level.class, PlayerMob.class})
public class SnowBiomeGetLevelMusic {
    @Advice.OnMethodExit()
    static void onExit(@Advice.This SnowBiome biome, @Advice.Argument(0) Level level, @Advice.Argument(1) PlayerMob perspective, @Advice.Return(readOnly = false) List<GameMusic> music) {
        if (level.getDimension() == 68) {
            music = Collections.singletonList(MusicRegistry.getMusic("cloudcave"));
        } else if (level.getDimension() == 69) {
            music = Collections.singletonList(MusicRegistry.getMusic("cloudsurface"));
        }
    }
}
