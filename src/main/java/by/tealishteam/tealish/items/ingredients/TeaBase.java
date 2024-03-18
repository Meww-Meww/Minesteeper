package by.tealishteam.tealish.items.ingredients;

public abstract class TeaBase extends Brewable {
    private int effectDurationMultiplier;

    protected TeaBase(Properties properties, int color, int colorWeight, int effectDurationMultiplier){
        super(properties, color, colorWeight, null, 0, 0);
        this.effectDurationMultiplier = effectDurationMultiplier;
    }

    public int getEffectDurationMultiplier() {
        return effectDurationMultiplier;
    }
}
