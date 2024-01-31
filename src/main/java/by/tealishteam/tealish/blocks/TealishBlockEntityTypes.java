package by.tealishteam.tealish.blocks;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static by.tealishteam.tealish.Tealish.MODID;

public class TealishBlockEntityTypes {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    public static final RegistryObject<BlockEntityType<TeapotEntity>> TEAPOT = BLOCK_ENTITIES_REGISTER.register("teapot", () -> BlockEntityType.Builder.of(TeapotEntity::new, TealishBlocks.TEAPOT.get()).build(null));

    public static void register(IEventBus modEventBus){
        BLOCK_ENTITIES_REGISTER.register(modEventBus);
    }
}
