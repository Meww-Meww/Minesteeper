package by.tealishteam.tealish.items.ingredients;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;

public abstract class Brewable extends Item {
    public static final int DEFAULT_COLOR = 0xA2C66A;
    public static final int DEFAULT_COLOR_WEIGHT = 1;
    protected int color = DEFAULT_COLOR;
    protected int colorWeight = DEFAULT_COLOR_WEIGHT;
    protected MobEffect effect;
    protected int effectLevel;
    protected int baseEffectDuration;

    protected Brewable(Properties properties, int color, int colorWeight, MobEffect effect, int baseEffectDuration, int effectLevel){
        super(properties);
        this.color = color;
        this.colorWeight = colorWeight;
        this.effect = effect;
        this.baseEffectDuration = baseEffectDuration;
        this.effectLevel = effectLevel;
    }

    public int getColor(){
        return color;
    }
    public int getColorWeight(){
        return colorWeight;
    }
    public MobEffectInstance getEffect(int durationModifier){
        if(effect == null){
            return null;
        }

        return new MobEffectInstance(effect, durationModifier * baseEffectDuration, effectLevel);
    }
}
