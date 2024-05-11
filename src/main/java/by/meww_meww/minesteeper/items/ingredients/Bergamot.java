package by.meww_meww.minesteeper.items.ingredients;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class Bergamot extends Brewable {
    private static final int BERGAMOT_COLOR = 0xEBDD52;
    private static final int BERGAMOT_COLOR_WEIGHT = 1;
    private static final MobEffectInstance BERGAMOT_EFFECT = new MobEffectInstance(MobEffects.ABSORPTION, 100, 0);

    public Bergamot(Properties properties) {
        super(properties, BERGAMOT_COLOR, BERGAMOT_COLOR_WEIGHT, BERGAMOT_EFFECT);
    }
}
