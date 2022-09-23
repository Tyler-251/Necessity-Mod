package entity.buffs;

import engine.util.GameRandom;
import engine.world.WorldEntity;
import entity.mobs.buffs.ActiveBuff;
import entity.mobs.buffs.BuffModifiers;
import entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import entity.particle.Particle;
import level.maps.light.GameLight;

import java.awt.*;

public class MoonstoneMaskSetBonusBuff extends SetBonusBuff {
    public MoonstoneMaskSetBonusBuff() {

    }

    @Override
    public void init(ActiveBuff activeBuff) {
        activeBuff.setModifier(BuffModifiers.SUMMONS_SPEED, 0.20F);
    }

    @Override
    public void clientTick(ActiveBuff buff) {
        if (buff.owner.getWorldEntity().getTimeOfDay() == WorldEntity.TimeOfDay.NIGHT) {
            buff.resetDefaultModifiers();
            buff.setModifier(BuffModifiers.SUMMONS_SPEED, 0.20F);
        } else {
            buff.resetDefaultModifiers();
            if (buff.owner.getLevel().tickManager().getTotalTicks() % 5L == 0L) {
                buff.owner.getLevel().entityManager.addParticle(buff.owner.x + (float)(GameRandom.globalRandom.nextGaussian() * 6.0), buff.owner.y + (float)(GameRandom.globalRandom.nextGaussian() * 8.0), Particle.GType.COSMETIC).movesConstant(buff.owner.dx / 10.0F, buff.owner.dy / 10.0F).color(new Color(0, 0, 0)).sizeFades(6, 10).height(16.0F);
            }

            buff.owner.getLevel().lightManager.refreshParticleLightFloat(buff.owner.x, buff.owner.y, new Color(63, 0, 61), .9f);
        }
    }
}
