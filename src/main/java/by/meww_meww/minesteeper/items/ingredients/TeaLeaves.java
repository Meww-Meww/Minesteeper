package by.meww_meww.minesteeper.items.ingredients;

public class TeaLeaves extends TeaBase {
    private static final int TEA_LEAVES_COLOR = 0xA2C66A;
    private static final int TEA_LEAVES_COLOR_WEIGHT = 3;
    private static final int TEA_LEAVES_EFFECT_MULTIPLIER = 2;

    public TeaLeaves(Properties properties){
        super(properties, TEA_LEAVES_COLOR, TEA_LEAVES_COLOR_WEIGHT, TEA_LEAVES_EFFECT_MULTIPLIER, 6000);
    }
}
