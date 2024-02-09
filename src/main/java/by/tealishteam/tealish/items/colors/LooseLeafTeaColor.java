package by.tealishteam.tealish.items.colors;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class LooseLeafTeaColor implements ItemColor {

    @Override
    public int getColor(ItemStack itemStack, int layer) {
        CompoundTag compoundtag = itemStack.getTagElement("Effects");
        if(compoundtag == null){
            return 0xFFFFFF;
        }

        int[] colors = compoundtag.getIntArray("Colors");

        int averageColor = 0xFFFFFF;
        int numColors =  colors.length;
        for(int color : colors){
            if(averageColor == 0xFFFFFF){
                averageColor = color / numColors;
            } else {
                averageColor += color / numColors;
            }
        }

        return averageColor;
    }
}
