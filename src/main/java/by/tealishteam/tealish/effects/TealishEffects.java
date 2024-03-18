package by.tealishteam.tealish.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static by.tealishteam.tealish.Tealish.MODID;

public class TealishEffects {
    private static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MODID);
    public static final RegistryObject<Caffeinated> CAFFEINATED = EFFECTS.register("caffeinated", Caffeinated::new);

    public static void register(IEventBus modEventBus){
        EFFECTS.register(modEventBus);
    }
}
