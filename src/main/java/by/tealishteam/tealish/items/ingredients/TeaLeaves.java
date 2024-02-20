package by.tealishteam.tealish.items.ingredients;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class TeaLeaves extends Brewable {
    private static final int TEA_LEAVES_COLOR = 0xA2C66A;
    private static final int TEA_LEAVES_COLOR_WEIGHT = 5;
    private static final MobEffectInstance TEA_LEAVES_EFFECT = new MobEffectInstance(MobEffects.REGENERATION, 10, 1);

    public TeaLeaves(Properties properties){
        super(properties, TEA_LEAVES_COLOR, TEA_LEAVES_COLOR_WEIGHT, TEA_LEAVES_EFFECT);
    }
}
