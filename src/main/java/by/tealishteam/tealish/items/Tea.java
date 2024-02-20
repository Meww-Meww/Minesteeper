package by.tealishteam.tealish.items;

import by.tealishteam.tealish.fluids.TeaFluid;
import by.tealishteam.tealish.fluids.TealishFluids;
import by.tealishteam.tealish.utils.EffectSerialization;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;

public class Tea extends Item {
    int DEFAULT_TEA_COLOR = 10511680;

    public Tea(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level finishUsingItem, LivingEntity entity) {
        super.finishUsingItem(itemStack, finishUsingItem, entity);
        return new ItemStack(TealishItems.MUG.get());
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
            stack.getTag().put("Effects", fluid.getTag().get("Effects").copy());
        }

        return stack;
    }

    public void appendHoverText(ItemStack item, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(item.hasTag()){
            List<MobEffectInstance> effects = EffectSerialization.fromCompoundTag(item.getTag());

            for(MobEffectInstance effect : effects) {
                components.add(Component.empty().append(effect.getEffect().getDisplayName()));
            }
        }
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
