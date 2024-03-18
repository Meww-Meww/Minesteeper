package by.tealishteam.tealish.items.ingredients;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

public class Lavender extends Brewable {
    private static final int LAVENDER_COLOR = 0xE3CCEA;
    private static final int LAVENDER_COLOR_WEIGHT = 1;

    private static final MobEffect LAVENDER_EFFECT = MobEffects.REGENERATION;
    private static final int LAVENDER_BASE_EFFECT_DURATION = 200;
    private static final int LAVENDER_EFFECT_LEVEL = 0;

    public Lavender(Properties properties){
        super(properties, LAVENDER_COLOR, LAVENDER_COLOR_WEIGHT, LAVENDER_EFFECT, LAVENDER_BASE_EFFECT_DURATION, LAVENDER_EFFECT_LEVEL);
    }
}
