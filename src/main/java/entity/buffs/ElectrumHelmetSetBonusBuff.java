//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package entity.buffs;

import engine.localization.Localization;
import engine.registries.BuffRegistry;
import entity.mobs.GameDamage;
import entity.mobs.MobHitEvent;
import entity.mobs.buffs.ActiveBuff;
import entity.mobs.buffs.BuffModifiers;
import entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import gfx.gameTooltips.ListGameTooltips;

public class ElectrumHelmetSetBonusBuff extends SetBonusBuff {
    public ElectrumHelmetSetBonusBuff() {
        this.shouldSave = false;
    }

    public void init(ActiveBuff buff) {
        buff.setModifier(BuffModifiers.MELEE_ATTACK_SPEED, 0.15F);
    }

    public void onAttacked(ActiveBuff buff, MobHitEvent hitEvent) {
        super.onAttacked(buff, hitEvent);
        if (!hitEvent.isPrevented() && hitEvent.damageType == GameDamage.DamageType.MELEE) {
            hitEvent.mob.buffManager.addBuff(new ActiveBuff(BuffRegistry.Debuffs.ABLAZE, hitEvent.mob, 5.0F, hitEvent.attacker), hitEvent.mob.getLevel().isServerLevel());
        }

    }

    public ListGameTooltips getTooltip(ActiveBuff ab) {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "electrumhelmetset"));
        return tooltips;
    }
}
