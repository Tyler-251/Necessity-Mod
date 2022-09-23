//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package level.gameObject;

import engine.localization.Localization;
import engine.tickManager.TickManager;
import entity.mobs.PlayerMob;
import entity.objectEntity.CloudCaveExitObjectEntity;
import entity.objectEntity.DungeonExitObjectEntity;
import entity.objectEntity.ObjectEntity;
import entity.objectEntity.PortalObjectEntity;
import gfx.camera.GameCamera;
import gfx.drawOptions.texture.TextureDrawOptions;
import gfx.drawables.LevelSortedDrawable;
import gfx.drawables.OrderableDrawables;
import gfx.gameTexture.GameTexture;
import gfx.gameTooltips.GameTooltips;
import gfx.gameTooltips.StringTooltips;
import inventory.item.toolItem.ToolType;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.List;
import level.maps.Level;
import level.maps.light.GameLight;

public class CloudCaveExitObject extends GameObject {
    public GameTexture texture;

    public CloudCaveExitObject() {
        super(new Rectangle(32, 32));
        this.mapColor = new Color(53, 54, 59);
        this.drawDmg = false;
        this.lightLevel = 50;
        this.toolType = ToolType.UNBREAKABLE;
        this.isLightTransparent = true;
    }

    public void loadTextures() {
        super.loadTextures();
        this.texture = GameTexture.fromFile("objects/cloudcaveexit");
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        final TextureDrawOptions top = this.texture.initDraw().sprite(0, 0, 32).light(light).pos(drawX, drawY - 32);
        final TextureDrawOptions bot = this.texture.initDraw().sprite(0, 1, 32).light(light).pos(drawX, drawY);
        list.add(new LevelSortedDrawable(this, tileX, tileY) {
            public int getSortY() {
                return 16;
            }

            public void draw(TickManager tickManager) {
                top.draw();
                bot.draw();
            }
        });
    }

    public boolean canInteract(Level level, int x, int y, PlayerMob player) {
        return true;
    }

    public String getInteractTip(Level level, int x, int y, PlayerMob perspective, boolean debug) {
        return Localization.translate("controls", "usetip");
    }

    public void interact(Level level, int x, int y, PlayerMob player) {
        if (level.isServerLevel() && player.isServerClient()) {
            ObjectEntity objectEntity = level.entityManager.getObjectEntity(x, y);
            if (objectEntity instanceof PortalObjectEntity) {
                ((PortalObjectEntity)objectEntity).use(level.getServer(), player.getServerClient());
            }
        }

        super.interact(level, x, y, player);
    }

    public ObjectEntity getNewObjectEntity(Level level, int x, int y) {
        return new CloudCaveExitObjectEntity(level, x, y, x, y) {
            public boolean shouldDrawOnMap() {
                return true;
            }

            public Rectangle drawOnMapBox() {
                return new Rectangle(-8, -24, 16, 32);
            }

            public void drawOnMap(int x, int y) {
                CloudCaveExitObject.this.texture.initDraw().size(16, 32).draw(x - 8, y - 24);
            }

            public GameTooltips getMapTooltips() {
                return new StringTooltips(CloudCaveExitObject.this.getDisplayName());
            }
        };
    }
}
