//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package level.gameObject;

import engine.localization.Localization;
import engine.tickManager.TickManager;
import entity.mobs.PlayerMob;
import entity.objectEntity.CloudCaveEntranceObjectEntity;
import entity.objectEntity.DungeonEntranceObjectEntity;
import entity.objectEntity.ObjectEntity;
import entity.objectEntity.PortalObjectEntity;
import gfx.camera.GameCamera;
import gfx.drawOptions.texture.TextureDrawOptions;
import gfx.drawables.LevelSortedDrawable;
import gfx.drawables.OrderableDrawables;
import gfx.gameTexture.GameTexture;
import inventory.item.toolItem.ToolType;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.List;
import level.maps.Level;
import level.maps.light.GameLight;

public class CloudCaveEntranceObject extends GameObject {
    public GameTexture texture;

    public CloudCaveEntranceObject() {
        super(new Rectangle(32, 32));
        this.mapColor = new Color(53, 54, 59);
        this.displayMapTooltip = true;
        this.drawDmg = false;
        this.toolType = ToolType.UNBREAKABLE;
        this.isLightTransparent = true;
    }

    public void loadTextures() {
        super.loadTextures();
        this.texture = GameTexture.fromFile("objects/cloudcaveentrance");
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        final TextureDrawOptions options = this.texture.initDraw().sprite(0, 0, 32).light(light).pos(drawX, drawY);
        list.add(new LevelSortedDrawable(this, tileX, tileY) {
            public int getSortY() {
                return 16;
            }

            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
    }

    public String getInteractTip(Level level, int x, int y, PlayerMob perspective, boolean debug) {
        return Localization.translate("controls", "usetip");
    }

    public boolean canInteract(Level level, int x, int y, PlayerMob player) {
        return true;
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
        return new CloudCaveEntranceObjectEntity(level, x, y);
    }
}
