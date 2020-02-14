package com.tao.brokenworldbees.init;

import com.tao.brokenworldbees.items.types.EnumHoneyComb;

/**
 * Created by Tao on 12/15/2017.
 */
public class RecipeRegister {
    public static void postInitRecipes() {
        for (final EnumHoneyComb info : EnumHoneyComb.values()) {
            info.addRecipe();
        }
    }
}

