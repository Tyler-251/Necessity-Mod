//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package entity.levelEvent;

import engine.localization.message.GameMessage;
import engine.localization.message.LocalMessage;
import engine.network.Packet;
import engine.network.PacketReader;
import engine.network.PacketWriter;
import engine.util.GameMath;
import engine.util.GameRandom;
import engine.util.LineHitbox;
import entity.levelEvent.mobAbilityLevelEvent.MobAbilityLevelEvent;
import entity.mobs.Attacker;
import entity.mobs.DeathMessageTable;
import entity.mobs.GameDamage;
import entity.mobs.Mob;
import entity.particle.Particle.GType;
import entity.trails.LightningTrail;
import entity.trails.TrailVector;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.function.Function;
import level.maps.LevelObjectHit;

public class StormTrailEvent extends MobAbilityLevelEvent implements Attacker {
    private static final int totalPoints = 16;
    private static final int distance = 120;
    private static final float distanceMod = 2.0F;
    private static final int ticksToComplete = 10;
    private int startX;
    private int startY;
    private int targetX;
    private int targetY;
    private float xDir;
    private float yDir;
    private GameDamage damage;
    private int seed;
    private int tickCounter;
    private int pointCounter;
    private ArrayList<Point2D.Float> points;
    private ArrayList<Integer> hits;
    private LightningTrail trail;

    public StormTrailEvent() {
    }

    public StormTrailEvent(Mob owner, GameDamage damage, int startX, int startY, int targetX, int targetY, int seed) {
        super(owner, new GameRandom((long)seed));
        if (damage != null) {
            this.damage = damage;
        } else {
            this.damage = new GameDamage(0.0F);
        }

        this.startX = startX;
        this.startY = startY;
        this.targetX = targetX;
        this.targetY = targetY;
        this.seed = seed;
    }

    public void applySpawnPacket(PacketReader reader) {
        super.applySpawnPacket(reader);
        this.startX = reader.getNextInt();
        this.startY = reader.getNextInt();
        this.targetX = reader.getNextInt();
        this.targetY = reader.getNextInt();
        this.seed = reader.getNextInt();
        this.tickCounter = reader.getNextShortUnsigned();
    }

    public void setupSpawnPacket(PacketWriter writer) {
        super.setupSpawnPacket(writer);
        writer.putNextInt(this.startX);
        writer.putNextInt(this.startY);
        writer.putNextInt(this.targetX);
        writer.putNextInt(this.targetY);
        writer.putNextInt(this.seed);
        writer.putNextShortUnsigned(this.tickCounter);
    }

    public void init() {
        super.init();
        float l = (float)(new Point(this.startX, this.startY)).distance((double)this.targetX, (double)this.targetY) / 2;
        this.xDir = (float)(this.targetX - this.startX) / l;
        this.yDir = (float)(this.targetY - this.startY) / l;
        this.points = this.generatePoints();
        this.trail = new LightningTrail(new TrailVector((float)this.startX, (float)this.startY, this.xDir, this.yDir, 25.0F, 14.0F), this.level, new Color(225, 255, 0, 255));
        this.trail.addNewPoint(new TrailVector((Point2D.Float)this.points.get(0), this.xDir, this.yDir, this.trail.thickness, 14.0F));
        if (this.level.isClientLevel()) {
            this.level.entityManager.addTrail(this.trail);
        }

        this.hits = new ArrayList();
    }

    public void clientTick() {
        if (!this.isOver()) {
            ++this.tickCounter;
            int expectedCounter = this.tickCounter * 16 / 5;

            while(this.pointCounter < expectedCounter) {
                ++this.pointCounter;
                if (this.pointCounter >= 16) {
                    this.over();
                    break;
                }

                Point2D.Float point = (Point2D.Float)this.points.get(this.pointCounter);
                this.trail.addNewPoint(new TrailVector(point, this.xDir, this.yDir, this.trail.thickness, 18.0F));
                Point2D.Float lastPoint = (Point2D.Float)this.points.get(this.pointCounter - 1);
                Point2D.Float midPoint = new Point2D.Float((point.x + lastPoint.x) / 2.0F, (point.y + lastPoint.y) / 2.0F);
                Point2D.Float norm = GameMath.normalize(point.x - lastPoint.x, point.y - lastPoint.y);
                float distance = (float)point.distance(lastPoint);

                int j;
                for(j = 0; j < 2; ++j) {
                    this.level.entityManager.addParticle(midPoint.x + norm.x * GameRandom.globalRandom.nextFloat() * distance, midPoint.y + norm.y * GameRandom.globalRandom.nextFloat() * distance, GType.COSMETIC).movesConstant((float)(GameRandom.globalRandom.nextGaussian() * 4.0), (float)(GameRandom.globalRandom.nextGaussian() * 4.0)).color(this.trail.col).height(18.0F);
                }

                if (this.pointCounter == 15) {
                    for(j = 0; j < 20; ++j) {
                        this.level.entityManager.addParticle(lastPoint.x + norm.x * 4.0F, lastPoint.y + norm.y * 4.0F, GType.COSMETIC).movesConstant((float)(GameRandom.globalRandom.nextGaussian() * 20.0), (float)(GameRandom.globalRandom.nextGaussian() * 20.0)).color(this.trail.col).height(18.0F).lifeTime(250);
                    }
                }

                Line2D line = new Line2D.Double(lastPoint.getX(), lastPoint.getY(), point.getX(), point.getY());
                LineHitbox hitbox = new LineHitbox(line, 20.0F);
                this.handleHits(hitbox, (m) -> {
                    return !this.hasHit(m);
                }, (Function)null);
            }

        }
    }

    public void serverTick() {
        if (!this.isOver()) {
            ++this.tickCounter;
            int expectedCounter = this.tickCounter * 16 / 5;

            while(this.pointCounter < expectedCounter) {
                ++this.pointCounter;
                if (this.pointCounter >= 16) {
                    this.over();
                    break;
                }

                Point2D p1 = (Point2D)this.points.get(this.pointCounter - 1);
                Point2D p2 = (Point2D)this.points.get(this.pointCounter);
                Line2D line = new Line2D.Double(p1.getX(), p1.getY(), p2.getX(), p2.getY());
                LineHitbox hitbox = new LineHitbox(line, 20.0F);
                this.handleHits(hitbox, (m) -> {
                    return !this.hasHit(m);
                }, (Function)null);
            }

        }
    }

    private ArrayList<Point2D.Float> generatePoints() {
        ArrayList<Point2D.Float> out = new ArrayList();
        GameRandom random = new GameRandom((long)this.seed);
        Point2D.Float perp = new Point2D.Float(-this.yDir, this.xDir);
        float lastDist = 0.0F;
        Point2D.Float lastPoint = new Point2D.Float((float)this.startX, (float)this.startY);
        out.add(lastPoint);

        for(int i = 0; i < 16; ++i) {
            float fluctuation = (random.nextFloat() - 0.5F) * lastDist * 2.0F;
            lastDist = (random.nextFloat() + 1.0F) * 7.5F;
            lastPoint = new Point2D.Float(lastPoint.x + this.xDir * lastDist - perp.x * fluctuation, lastPoint.y + this.yDir * lastDist - perp.y * fluctuation);
            out.add(lastPoint);
        }

        return out;
    }

    public void clientHit(Mob target, Packet content) {
        super.clientHit(target, content);
        this.hits.add(target.getUniqueID());
    }

    public void serverHit(Mob target, Packet content, boolean clientSubmitted) {
        super.serverHit(target, content, clientSubmitted);
        target.isServerHit(this.damage, 0.0F, 0.0F, 0.0F, this);
    }

    public void hit(LevelObjectHit hit) {
        super.hit(hit);
        hit.getLevelObject().attackThrough(this.damage, this);
    }

    public DeathMessageTable getDeathMessages() {
        return this.getDeathMessages("lightning", 2);
    }

    public GameMessage getAttackerName() {
        return (GameMessage)(this.owner != null ? this.owner.getAttackerName() : new LocalMessage("deaths", "unknownatt"));
    }

    public Mob getFirstAttackOwner() {
        return this.owner;
    }

    public boolean hasHit(Mob mob) {
        return this.hits.contains(mob.getUniqueID());
    }
}
