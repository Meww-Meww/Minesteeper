package by.tealishteam.tealish.items.ingredients;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

public class Bergamot extends Brewable {
    private static final int BERGAMOT_COLOR = 0xEBDD52;
    private static final int BERGAMOT_COLOR_WEIGHT = 1;
    private static final MobEffect BERGAMOT_EFFECT = MobEffects.ABSORPTION;
    private static final int BERGAMOT_BASE_EFFECT_DURATION = 100;
    private static final int BERGAMOT_EFFECT_LEVEL = 0;

    public Bergamot(Properties properties) {
        super(properties, BERGAMOT_COLOR, BERGAMOT_COLOR_WEIGHT, BERGAMOT_EFFECT, BERGAMOT_BASE_EFFECT_DURATION, BERGAMOT_EFFECT_LEVEL);
    }
}
