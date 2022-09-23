package item.toolItem;

import engine.network.PacketReader;
import entity.levelEvent.GlaiveShowAttackEvent;
import entity.mobs.AttackAnimMob;
import entity.mobs.GameDamage;
import entity.particle.Particle.GType;
import inventory.InventoryItem;

import java.awt.Color;
import java.awt.geom.Point2D;

import inventory.item.toolItem.glaiveToolItem.GlaiveToolItem;
import level.maps.Level;



public class LisaMemoraToolItem extends GlaiveToolItem {
    public LisaMemoraToolItem() {
        super(1600);
        this.rarity = Rarity.RARE;
        this.animSpeed = 340;
        this.attackDmg = new GameDamage(GameDamage.DamageType.MELEE, 18);
        this.attackRange = 160;
        this.knockback = 90;
        this.width = 40.0F;
        this.attackXOffset = 54;
        this.attackYOffset = 54;
    }

    public void showAttack(Level level, int x, int y, AttackAnimMob mob, int attackHeight, InventoryItem item, int seed, PacketReader contentReader) {
        super.showAttack(level, x, y, mob, attackHeight, item, seed, contentReader);
        if (level.isClientLevel()) {
            level.entityManager.addLevelEventHidden(new GlaiveShowAttackEvent(mob, x, y, seed, 10.0F) {
                public void tick(float angle) {
                    Point2D.Float angleDir = this.getAngleDir(angle);
                    this.level.entityManager.addParticle(this.attackMob.x + angleDir.x * 75.0F + (float)this.attackMob.getCurrentAttackDrawXOffset(), this.attackMob.y + angleDir.y * 75.0F + (float)this.attackMob.getCurrentAttackDrawYOffset(), GType.COSMETIC).color(new Color(200, 0, 0)).minDrawLight(150).givesLight(45.0F, 1.0F).lifeTime(400);
                    this.level.entityManager.addParticle(this.attackMob.x - angleDir.x * 75.0F + (float)this.attackMob.getCurrentAttackDrawXOffset(), this.attackMob.y - angleDir.y * 75.0F + (float)this.attackMob.getCurrentAttackDrawYOffset(), GType.COSMETIC).color(new Color(150, 0, 0)).minDrawLight(150).givesLight(45.0F, 1.0F).lifeTime(400);
                }
            });
        }
    }
}
