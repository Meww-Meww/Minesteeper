package by.meww_meww.minesteeper.effects;

import com.google.common.collect.Lists;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class Caffeinated extends MobEffect {
    public Caffeinated(){
        super(MobEffectCategory.HARMFUL, 0x855413);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return Lists.newArrayList();
    }
}
