package by.tealishteam.tealish.fluids;

import by.tealishteam.tealish.items.LooseLeafTea;
import by.tealishteam.tealish.items.Tea;
import by.tealishteam.tealish.utils.EffectSerialization;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;

import java.util.function.Consumer;

import static by.tealishteam.tealish.Tealish.MODID;

public class TeaFluid extends Fluid {

    public static FluidStack getFluidStackWithEffects(ItemStack inputItem, FluidStack inputFluid, int fluidAmount)
    {
        FluidStack stack = new FluidStack(TealishFluids.TEA_FLUID.get(), fluidAmount);
        CompoundTag tag = new CompoundTag();

        if(inputItem.getTag() != null){
            tag.merge(inputItem.getTag());
        }

        if(inputFluid.getTag() != null){
            tag.merge(inputFluid.getTag());
        }

        if(inputItem.getItem() == Items.MILK_BUCKET){
            tag.putBoolean("Milk", true);
            if(stack.getTag().getBoolean("Milk")){
                int color = tag.getInt("Color");
                int rAvg = (((color & 0xFF0000) >> 16) * 3 + 0xFF) / 4;
                int gAvg = (((color & 0xFF00) >> 8) * 3 + 0xFF) / 4;
                int bAvg = ((color & 0xFF) * 3 + 0xFF) / 4;
                color = (rAvg << 16) | (gAvg << 8) | bAvg;

                tag.putInt("Color", color);
            }
        }

        stack.setTag(tag);
        return stack;
    }

    public static ItemStack getDrink(FluidStack fluid, int amount){
        return Tea.fromFluid(fluid, amount);
    }

    @Override
    public FluidType getFluidType(){
        return TealishFluids.TEA_FLUID_TYPE.get();
    }

    @Override
    public Item getBucket() {
        return null;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState p_76127_, BlockGetter p_76128_, BlockPos p_76129_, Fluid p_76130_, Direction p_76131_) {
        return false;
    }

    @Override
    protected Vec3 getFlow(BlockGetter p_76110_, BlockPos p_76111_, FluidState p_76112_) {
        return null;
    }

    @Override
    public int getTickDelay(LevelReader p_76120_) {
        return 0;
    }

    @Override
    protected float getExplosionResistance() {
        return 0;
    }

    @Override
    public float getHeight(FluidState p_76124_, BlockGetter p_76125_, BlockPos p_76126_) {
        return 0;
    }

    @Override
    public float getOwnHeight(FluidState p_76123_) {
        return 0;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState p_76136_) {
        return null;
    }

    @Override
    public boolean isSource(FluidState p_76140_) {
        return false;
    }

    @Override
    public int getAmount(FluidState p_76141_) {
        return 0;
    }

    @Override
    public VoxelShape getShape(FluidState p_76137_, BlockGetter p_76138_, BlockPos p_76139_) {
        return null;
    }

    public static class TeaFluidType extends FluidType {
        private static final ResourceLocation TEXTURE_STILL = new ResourceLocation(MODID, "block/tea_still");
        private static final ResourceLocation TEXTURE_FLOW = new ResourceLocation(MODID,"block/tea_flow");
        public TeaFluidType(Properties properties) {
            super(properties);
        }

        @Override
        public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
        {
            consumer.accept(new IClientFluidTypeExtensions()
            {
                @Override
                public ResourceLocation getStillTexture()
                {
                    return TEXTURE_STILL;
                }

                @Override
                public ResourceLocation getFlowingTexture()
                {
                    return TEXTURE_FLOW;
                }

                @Override
                public int getTintColor(FluidStack stack)
                {
                    if(!stack.hasTag()) return 0xFFA2C66A;
                    return 0xFF000000 | stack.getTag().getInt("Color");
                }
            });
        }
    }
}
