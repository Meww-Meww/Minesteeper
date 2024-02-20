package by.tealishteam.tealish.utils;

import com.google.common.collect.Lists;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

public class EffectSerialization {
    public static void toCompoundTag(List<MobEffectInstance> effects, CompoundTag tag){
        CompoundTag effectTags = new CompoundTag();
        for(int i = 0; i < effects.size(); i++){
            CompoundTag effectTag = new CompoundTag();
            effectTag.putString("MobEffect", BuiltInRegistries.MOB_EFFECT.getKey(effects.get(i).getEffect()).toString());
            effectTag.putInt("Duration", effects.get(i).getDuration());
            effectTag.putInt("Amplifier", effects.get(i).getAmplifier());
            effectTags.put(String.valueOf(i), effectTag);
        }

        tag.put("Effects", effectTags);
    }

    public static List<MobEffectInstance> fromCompoundTag(CompoundTag tag){
        List<MobEffectInstance> effects = Lists.newArrayList();
        CompoundTag effectsTags = tag.getCompound("Effects");

        int index = 0;
        while(!effectsTags.getCompound(String.valueOf(index)).isEmpty()){
            CompoundTag effect = effectsTags.getCompound(String.valueOf(index));
            ResourceLocation name = ResourceLocation.tryParse(effect.getString("MobEffect"));
            MobEffect result = BuiltInRegistries.MOB_EFFECT.get(name);

            effects.add(new MobEffectInstance(result, effect.getInt("Duration"), effect.getInt("Amplifier")));
            index++;
        }

        return effects;
    }
}
