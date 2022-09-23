//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package entity.objectEntity;

import engine.network.packet.PacketChangeObject;
import engine.network.server.Server;
import engine.network.server.ServerClient;
import engine.registries.ObjectRegistry;
import java.awt.Point;

import engine.registries.TileRegistry;
import inventory.item.toolItem.ToolType;
import level.gameObject.GameObject;
import level.maps.DungeonBossLevel;
import level.maps.DungeonLevel;
import level.maps.Level;

public class CloudCaveEntranceObjectEntity extends PortalObjectEntity {
    public CloudCaveEntranceObjectEntity(Level level, int x, int y) {
        super(level, "cloudcaveentrance", x, y, 0, 0, 69, 50, 50);
    }

    public void init() {
        super.init();
        if (this.getLevel() != null) {
            this.dIslandX = this.getLevel().getIslandX();
            this.dIslandY = this.getLevel().getIslandY();
            Point destination;
            this.dDimension = 68;
            this.dX = (int)this.x;
            this.dY = (int)this.y;
        }

    }

    public void use(Server server, ServerClient client) {
        this.teleportClientToAroundDestination(client, (level) -> {
            GameObject exit = ObjectRegistry.getObject(ObjectRegistry.getObjectID("cloudcaveexit"));
            if (exit != null) {
                exit.placeObject(level, this.dX, this.dY, 0);
                PortalObjectEntity exitEntity = (PortalObjectEntity)level.entityManager.getObjectEntity(this.dX, this.dY);
                if (exitEntity != null) {
                    exitEntity.dX = this.getX();
                    exitEntity.dY = this.getY();
                    exitEntity.dDimension = this.getLevel().getDimension();
                }
            }
            for(int i = -1; i < 2; ++i) {
                int tileX = this.dX + i;

                for(int j = -1; j < 2; ++j) {
                    int tileY = this.dY + j;
                    level.setTile(tileX, tileY, TileRegistry.getTileID("cloudstoneruinstile"));
                    if (i == 0 && j == 0) {
                        ObjectRegistry.getObject("cloudcaveexit").placeObject(level, tileX, tileY, 0);

                        server.network.sendToClientsAt(new PacketChangeObject(tileX, tileY, ObjectRegistry.getObjectID("cloudcaveexit")), level);
                    } else {
                        if (level.getObjectID(tileX, tileY) != ObjectRegistry.getObjectID("cloudcaveexit") && level.getObjectID(tileX, tileY) != ObjectRegistry.getObjectID("cloudcaveexit") && level.getObject(tileX, tileY).toolType != ToolType.UNBREAKABLE) {
                            level.setObject(tileX, tileY, 0);
                            server.network.sendToClientsAt(new PacketChangeObject(tileX, tileY, 0), level);
                        }

                        if (level.getTile(tileX, tileY).isLiquid) {
                            level.sendTileChangePacket(server, tileX, tileY, TileRegistry.dirtID);
                        }
                    }
                }
            }

            return true;
        }, true);
        this.runClearMobs(client);
    }
}
