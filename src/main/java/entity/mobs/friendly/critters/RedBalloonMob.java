package entity.mobs.friendly.critters;

import entity.mobs.friendly.critters.BirdMob;
import gfx.gameTexture.GameTexture;

public class RedBalloonMob extends BirdMob {
    public static GameTexture texture;
    public RedBalloonMob() {
    }

    protected GameTexture getTexture() {
        return texture;
    }
}
