package by.tealishteam.tealish.items;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class LooseLeafTea extends Item {
    public LooseLeafTea(Properties properties) {
        super(properties);
    }

    public static class LooseLeafTeaColor implements ItemColor {

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
}
