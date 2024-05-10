package by.tealishteam.tealish.loot;

import com.google.common.base.Supplier;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;

public class GrassDropModifier extends LootModifier {
    private final Item item;

    public static final Supplier<Codec<GrassDropModifier>> CODEC = () -> RecordCodecBuilder.create(
                    inst -> codecStart(inst)
                            .and(BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(m -> m.item))
                            .apply(inst, GrassDropModifier::new));

    protected GrassDropModifier(LootItemCondition[] conditionsIn, Item item)
    {
        super(conditionsIn);
        this.item = item;
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.add(new ItemStack(item));
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec()
    {
        return CODEC.get();
    }
}
