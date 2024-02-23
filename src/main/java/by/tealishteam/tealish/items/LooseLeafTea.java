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
            CompoundTag compoundtag = itemStack.getTag();
            if(compoundtag == null){
                return 0xA2C66A;
            }

            return compoundtag.getInt("Color");
        }
    }
}
