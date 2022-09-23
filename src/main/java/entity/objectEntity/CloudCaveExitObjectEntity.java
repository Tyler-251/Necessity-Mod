//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package entity.objectEntity;

import engine.network.server.Server;
import engine.network.server.ServerClient;
import java.util.function.Predicate;
import level.maps.Level;

public class CloudCaveExitObjectEntity extends PortalObjectEntity {
    public CloudCaveExitObjectEntity(Level level, int x, int y, int entranceX, int entranceY) {
        super(level, "cloudcaveexit", x, y, 0, 0, 69, entranceX, entranceY);
    }

    public void init() {
        super.init();
        if (this.getLevel() != null) {
            this.dIslandX = this.getLevel().getIslandX();
            this.dIslandY = this.getLevel().getIslandY();
        }

    }

    public void use(Server server, ServerClient client) {
        this.teleportClientToAroundDestination(client, (Predicate)null, true);
        this.runClearMobs(client);
    }
}
