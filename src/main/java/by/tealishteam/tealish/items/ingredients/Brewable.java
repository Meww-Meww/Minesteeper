package by.tealishteam.tealish.items.ingredients;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;

public abstract class Brewable extends Item {
    public static final int DEFAULT_COLOR = 0xA2C66A;
    public static final int DEFAULT_COLOR_WEIGHT = 1;
    protected int color = DEFAULT_COLOR;
    protected int colorWeight = DEFAULT_COLOR_WEIGHT;
    protected MobEffectInstance effect;

    protected Brewable(Properties properties, int color, int colorWeight, MobEffectInstance effect){
        super(properties);
        this.color = color;
        this.colorWeight = colorWeight;
        this.effect = effect;
    }

    public int getColor(){
        return color;
    }
    public MobEffectInstance getEffect(){
        return this.effect;
    }
}