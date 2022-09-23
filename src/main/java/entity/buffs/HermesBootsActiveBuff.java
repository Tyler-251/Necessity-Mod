//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package entity.buffs;

import engine.Screen;
import engine.network.gameNetworkData.GNDItemMap;
import engine.sound.SoundEffect;
import engine.util.GameRandom;
import entity.mobs.Mob;
import entity.mobs.buffs.ActiveBuff;
import entity.mobs.buffs.BuffModifiers;
import entity.mobs.buffs.MovementTickBuff;
import entity.mobs.buffs.staticBuffs.Buff;
import entity.particle.Particle.GType;
import gfx.GameResources;
import java.awt.Color;

public class HermesBootsActiveBuff extends Buff implements MovementTickBuff {
    public HermesBootsActiveBuff() {
        this.shouldSave = false;
        this.isVisible = false;
    }

    public void init(ActiveBuff buff) {
        buff.setModifier(BuffModifiers.SPEED_FLAT, 12.5F);
        buff.setModifier(BuffModifiers.SPEED, 0.5F);
    }

    public void tickMovement(ActiveBuff buff, float delta) {
        Mob owner = buff.owner;
        if (owner.getLevel().isClientLevel() && (owner.dx != 0.0F || owner.dy != 0.0F)) {
            float speed = owner.getCurrentSpeed() * delta / 250.0F;
            GNDItemMap gndData = buff.getGndData();
            float particleBuffer = gndData.getFloat("particleBuffer") + speed;
            float xOffset;
            if (particleBuffer >= 15.0F) {
                particleBuffer -= 15.0F;
                xOffset = GameRandom.globalRandom.floatGaussian() * 2.0F;
                float yOffset = GameRandom.globalRandom.floatGaussian() * 2.0F;
                boolean alternate = gndData.getBoolean("particleAlternate");
                gndData.setBoolean("particleAlternate", !alternate);
                if (owner.dir != 0 && owner.dir != 2) {
                    yOffset += alternate ? 4.0F : -4.0F;
                } else {
                    xOffset += alternate ? 4.0F : -4.0F;
                }

                float var10001 = owner.x + xOffset;
                float var10002 = owner.y + yOffset - 2.0F;
                owner.getLevel().entityManager.addParticle(var10001, var10002, GType.IMPORTANT_COSMETIC).color(new Color(246, 228, 197)).sizeFadesInAndOut(10, 16, 50, 200).movesConstant(owner.dx / 10.0F, owner.dy / 10.0F).lifeTime(300).height(2.0F);
            }

            gndData.setFloat("particleBuffer", particleBuffer);
            xOffset = gndData.getFloat("soundBuffer") + Math.min(speed, 80.0F * delta / 250.0F);
            if (xOffset >= 45.0F) {
                xOffset -= 45.0F;
                Screen.playSound(GameResources.run, SoundEffect.effect(owner).volume(1.0F).pitch(1.2F));
            }

            gndData.setFloat("soundBuffer", xOffset);
        }

    }
}
