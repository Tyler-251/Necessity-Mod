package entity.mobs.friendly.critters;

import entity.mobs.friendly.critters.BirdMob;
import gfx.gameTexture.GameTexture;

public class BlueBalloonMob extends BirdMob {
    public static GameTexture texture;
    public BlueBalloonMob() {
    }

    protected GameTexture getTexture() {
        return texture;
    }
}
