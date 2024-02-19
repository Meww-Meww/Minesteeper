package by.tealishteam.tealish.items;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class Tea extends Item {
    int DEFAULT_TEA_COLOR = 10511680;

    public Tea(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level finishUsingItem, LivingEntity entity) {
        super.finishUsingItem(itemStack, finishUsingItem, entity);
        return new ItemStack(Items.GLASS_BOTTLE);
    }

    public SoundEvent getDrinkingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    public SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    int getColor(ItemStack itemStack) {
        CompoundTag compoundtag = itemStack.getTagElement("display");
        return compoundtag != null && compoundtag.contains("color", 99) ? compoundtag.getInt("color") : 10511680;
    }

    public static class TeaColor implements ItemColor {
        @Override
        public int getColor(ItemStack itemStack, int layer) {
            if(layer == 1){
                CompoundTag compoundtag = itemStack.getTagElement("Effects");
                if(compoundtag == null){
                    return 0xFFFFFF;
                }

                int[] colors = compoundtag.getIntArray("Colors");

                int averageColor = 0xFFFFFF;
                int numColors =  colors.length;
                for(int color : colors){
                    if(averageColor == 0xFFFFFF){
                        averageColor = color / numColors;
                    } else {
                        averageColor += color / numColors;
                    }
                }

                return averageColor;
            }

            return 0xFFFFFF;
        }
    }
}
