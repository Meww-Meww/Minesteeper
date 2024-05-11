package by.meww_meww.minesteeper.utils;

import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

public class EffectSerialization {
    public static void toCompoundTag(List<CompoundTag> effects, CompoundTag tag){
        CompoundTag effectTags = new CompoundTag();
        for(int i = 0; i < effects.size(); i++){
            effectTags.put(String.valueOf(i), effects.get(i));
        }

        tag.put("Effects", effectTags);
    }

    public static List<MobEffectInstance> fromCompoundTag(CompoundTag tag){
        List<MobEffectInstance> effects = Lists.newArrayList();
        CompoundTag effectsTags = tag.getCompound("Effects");

        int index = 0;
        while(!effectsTags.getCompound(String.valueOf(index)).isEmpty()){
            CompoundTag effect = effectsTags.getCompound(String.valueOf(index));

            effects.add(MobEffectInstance.load(effect));
            index++;
        }

        return effects;
    }
}
