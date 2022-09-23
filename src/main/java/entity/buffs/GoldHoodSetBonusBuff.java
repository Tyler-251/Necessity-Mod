package entity.buffs;

import engine.localization.Localization;
import engine.modifiers.ModifierValue;
import entity.mobs.buffs.ActiveBuff;
import entity.mobs.buffs.BuffModifiers;
import entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SimpleSetBonusBuff;
import gfx.gameTooltips.ListGameTooltips;

public class GoldHoodSetBonusBuff extends SimpleSetBonusBuff {
    public GoldHoodSetBonusBuff() {
        super(new ModifierValue[]{new ModifierValue(BuffModifiers.ARMOR_FLAT, 2)});
    }

    public ListGameTooltips getTooltip(ActiveBuff ab) {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "goldhoodset"));
        return tooltips;
    }
}