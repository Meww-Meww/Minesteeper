package by.tealishteam.tealish.items.ingredients;

import by.tealishteam.tealish.effects.TealishEffects;
import net.minecraft.world.effect.MobEffectInstance;

public abstract class TeaBase extends Brewable {
    private final int effectDurationMultiplier;

    protected TeaBase(Properties properties, int color, int colorWeight, int effectDurationMultiplier, int caffeinatedEffectLength){
        super(properties, color, colorWeight, null);
        this.effectDurationMultiplier = effectDurationMultiplier;
        this.effect =  new MobEffectInstance(TealishEffects.CAFFEINATED.get(), caffeinatedEffectLength, 0, false, false);
    }

    public int getEffectDurationMultiplier() {
        return effectDurationMultiplier;
    }
}
