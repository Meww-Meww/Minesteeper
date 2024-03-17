package by.tealishteam.tealish.items.ingredients;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class Mint extends Brewable {
    private static final int MINT_COLOR = 0x73CC6C;

    private static final int MINT_COLOR_WEIGHT = 1;
    private static final MobEffectInstance MINT_EFFECT = null;

    public Mint(Properties properties) {
        super(properties, MINT_COLOR, MINT_COLOR_WEIGHT, MINT_EFFECT);
    }
}
