package by.tealishteam.tealish.items;

import by.tealishteam.tealish.utils.EffectSerialization;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public class LooseLeafTea extends Item {
    public LooseLeafTea(Properties properties) {
        super(properties);
    }

    public static List<MobEffectInstance> getEffects(ItemStack itemstack){
        List<MobEffectInstance> effects = Lists.newArrayList();

        if(itemstack.hasTag()){
            effects = EffectSerialization.fromCompoundTag(itemstack.getTag());
        }

        return effects;
    }

    public static class LooseLeafTeaColor implements ItemColor {

        @Override
        public int getColor(ItemStack itemStack, int layer) {
            CompoundTag compoundtag = itemStack.getTag();
            if(compoundtag == null){
                return 0xA2C66A;
            }

            return compoundtag.getInt("Color");
        }
    }
}
