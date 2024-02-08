package by.tealishteam.tealish.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class TeaLeaves extends Brewable {
    private static final int TEA_LEAVES_COLOR = 0xA2C66A;
    private static final int TEA_LEAVES_COLOR_WEIGHT = 5;

    public TeaLeaves(Properties properties){
        super(properties, TEA_LEAVES_COLOR, TEA_LEAVES_COLOR_WEIGHT);
    }

    public void appendHoverText(ItemStack p_41252_, @Nullable Level p_41253_, List<Component> components, TooltipFlag p_41255_) {
        components.add(Component.empty().append("Test"));

    }
}
