package by.tealishteam.tealish.fluids;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static by.tealishteam.tealish.Tealish.MODID;

public class TealishFluids {
    private static final DeferredRegister<Fluid> FLUID = DeferredRegister.create(ForgeRegistries.FLUIDS, MODID);
    private static final DeferredRegister<FluidType> FLUID_TYPE = DeferredRegister.create(ForgeRegistries.FLUID_TYPES, MODID);

    public static final RegistryObject<FluidType> TEA_FLUID_TYPE = FLUID_TYPE.register("tea_fluid", () -> new TeaFluid.TeaFluidType(FluidType.Properties.create().descriptionId("block.tealish.tea_fluid")));

    public static final RegistryObject<Fluid> TEA_FLUID = FLUID.register("tea_fluid", TeaFluid::new);

    public static void register(IEventBus modEventBus){
        FLUID.register(modEventBus);
        FLUID_TYPE.register(modEventBus);
    }
}
