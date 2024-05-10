package by.tealishteam.tealish.loot;

import by.tealishteam.tealish.Tealish;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TealishLootModifiers {
    private static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Tealish.MODID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> GRASS_DROPS = LOOT_MODIFIERS.register("grass_drop", GrassDropModifier.CODEC);

    public static void register(IEventBus modEventBus){
        LOOT_MODIFIERS.register(modEventBus);
    }
}
