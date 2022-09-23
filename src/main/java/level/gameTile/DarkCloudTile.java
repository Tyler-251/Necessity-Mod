package level.gameTile;

import engine.util.GameRandom;
import gfx.gameTexture.GameTexture;
import gfx.gameTexture.GameTextureSection;
import level.maps.Level;

import java.awt.*;

public class DarkCloudTile extends TerrainSplatterTile {

    private GameTexture texture;
    private final GameRandom drawRandom; // Used only in draw function

    public DarkCloudTile() {
        super(false, "darkcloudtile");
        canBeMined = false;
        drawRandom = new GameRandom();
        roomProperties.add("outsidefloor");
        mapColor = new Color(215, 215, 180);
    }

    protected void loadTextures() {
        super.loadTextures();
        texture = GameTexture.fromFile("tiles/darkcloudtile");
    }

    public Point getTerrainSprite(GameTextureSection gameTextureSection, Level level, int tileX, int tileY) {
        int tile;
        synchronized (drawRandom) {
            tile = drawRandom.seeded(getTileSeed(tileX, tileY)).nextInt(texture.getHeight() / 32);
        }
        return new Point(0, tile);
    }

    public int getTerrainPriority() {
        return TerrainSplatterTile.PRIORITY_TERRAIN;
    }

}
