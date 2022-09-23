package entity.mobs.friendly.critters;

import entity.mobs.friendly.critters.BirdMob;
import gfx.gameTexture.GameTexture;

public class YellowBalloonMob extends BirdMob {
    public static GameTexture texture;
    public YellowBalloonMob() {
    }

    protected GameTexture getTexture() {
        return texture;
    }
}
