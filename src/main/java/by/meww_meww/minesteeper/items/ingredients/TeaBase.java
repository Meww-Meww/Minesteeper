package by.meww_meww.minesteeper.items.ingredients;

import by.meww_meww.minesteeper.effects.MinesteeperEffects;
import net.minecraft.world.effect.MobEffectInstance;

public abstract class TeaBase extends Brewable {
    private final int effectDurationMultiplier;

    protected TeaBase(Properties properties, int color, int colorWeight, int effectDurationMultiplier, int caffeinatedEffectLength){
        super(properties, color, colorWeight, null);
        this.effectDurationMultiplier = effectDurationMultiplier;
        this.effect =  new MobEffectInstance(MinesteeperEffects.CAFFEINATED.get(), caffeinatedEffectLength, 0, false, false);
    }

    public int getEffectDurationMultiplier() {
        return effectDurationMultiplier;
    }
}
