package com.tao.brokenworldbees.init;

import com.tao.brokenworldbees.BrokenWorldBees;
import com.tao.brokenworldbees.items.ItemHoneyComb;
import com.tao.brokenworldbees.items.types.EnumHoneyComb;

/**
 * Created by Tao on 12/15/2017.
 */
public class ItemRegister {

    public static void preInitItems() {
        registerProducts();
    }

    private static void registerProducts() {
        BrokenWorldBees.comb = new ItemHoneyComb();

        BrokenWorldBees.proxy.registerItem(BrokenWorldBees.comb);
        for (EnumHoneyComb comb : EnumHoneyComb.values()) {
            if (comb.isActive()) {
                BrokenWorldBees.proxy.registerModel(BrokenWorldBees.comb, comb.ordinal());
            }
        }
    }

}
