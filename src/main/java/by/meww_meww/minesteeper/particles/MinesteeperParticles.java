package by.meww_meww.minesteeper.particles;

import by.meww_meww.minesteeper.Minesteeper;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MinesteeperParticles {
    private static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Minesteeper.MODID);

    public static final RegistryObject<SimpleParticleType> STEAM_PARTICLE = PARTICLES.register("steam_particle", () -> new SimpleParticleType(false));

    public static void register(IEventBus modEventBus){
        PARTICLES.register(modEventBus);
    }
}
