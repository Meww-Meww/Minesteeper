package by.tealishteam.tealish.items;

import by.tealishteam.tealish.fluids.TeaFluid;
import by.tealishteam.tealish.fluids.TealishFluids;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

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

    public static ItemStack fromFluid(FluidStack fluid, int amount){
        ItemStack stack = new ItemStack(TealishItems.TEA.get(), amount);

        if(fluid.hasTag()){
            stack.getOrCreateTag().putInt("Color", fluid.getTag().getInt("Color"));
        }

        return stack;
    }

    int getColor(ItemStack itemStack) {
        CompoundTag compoundtag = itemStack.getTagElement("display");
        return compoundtag != null && compoundtag.contains("color", 99) ? compoundtag.getInt("color") : 10511680;
    }

    public static class TeaColor implements ItemColor {
        @Override
        public int getColor(ItemStack itemStack, int layer) {
            if(layer == 0){
                CompoundTag compoundtag = itemStack.getTag();
                if(compoundtag == null){
                    return 0xFFA2C66A;
                }

                return 0xFF000000 | compoundtag.getInt("Color");
            }

            return 0xFFFFFFFF;
        }
    }
}
