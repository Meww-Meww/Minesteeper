package by.tealishteam.tealish.items.ingredients;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class Lavender extends Brewable {
    private static final int LAVENDER_COLOR = 0xE3CCEA;
    private static final int LAVENDER_COLOR_WEIGHT = 1;

    private static final MobEffectInstance LAVENDER_EFFECT = new MobEffectInstance(MobEffects.REGENERATION, 200, 0);

    public Lavender(Properties properties){
        super(properties, LAVENDER_COLOR, LAVENDER_COLOR_WEIGHT, LAVENDER_EFFECT);
    }
}
