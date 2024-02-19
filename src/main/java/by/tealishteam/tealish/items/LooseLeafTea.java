package by.tealishteam.tealish.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LooseLeafTea extends Item {
    public LooseLeafTea(Properties properties) {
        super(properties);
    }

    public static int getColor(ItemStack itemStack) {
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
