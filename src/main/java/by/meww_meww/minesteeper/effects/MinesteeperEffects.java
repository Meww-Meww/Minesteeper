package by.meww_meww.minesteeper.effects;

import by.meww_meww.minesteeper.Minesteeper;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MinesteeperEffects {
    private static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Minesteeper.MODID);
    public static final RegistryObject<Caffeinated> CAFFEINATED = EFFECTS.register("caffeinated", Caffeinated::new);

    public static void register(IEventBus modEventBus){
        EFFECTS.register(modEventBus);
    }
}
