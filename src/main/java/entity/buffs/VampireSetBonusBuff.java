package entity.buffs;

import engine.localization.Localization;
import engine.modifiers.ModifierTooltip;
import entity.mobs.buffs.ActiveBuff;
import entity.mobs.buffs.BuffModifiers;
import entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import gfx.gameTooltips.ListGameTooltips;

public class VampireSetBonusBuff extends SetBonusBuff {

    public void init(ActiveBuff buff) {
        buff.setMaxModifier(BuffModifiers.REGEN, 0.1F);
    }

    public ListGameTooltips getTooltip(ActiveBuff ab) {
        ListGameTooltips tooltips = new ListGameTooltips();
        ModifierTooltip tooltip = BuffModifiers.MAX_SUMMONS.getTooltip(ab.getModifier(BuffModifiers.MAX_SUMMONS), BuffModifiers.MAX_SUMMONS.defaultBuffValue);
        if (tooltip != null) {
            tooltips.add(tooltip.message);
        }

        tooltips.add(Localization.translate("itemtooltip", "vampireset"));
        return tooltips;
    }
}
