package com.tao.brokenworldbees.items;

import com.tao.brokenworldbees.BrokenWorldBees;
import com.tao.brokenworldbees.items.types.EnumHoneyComb;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Tao on 12/15/2017.
 */
public class ItemHoneyComb extends binnie.extrabees.items.ItemHoneyComb {
    public ItemHoneyComb() {
        super();
        this.types = EnumHoneyComb.values();
    }

    public static boolean isInvalidComb(ItemStack stack) {
        if (stack == null) {
            return true;
        }
        if (stack.getItem() != BrokenWorldBees.comb) {
            return false;
        }
        EnumHoneyComb honeyComb = EnumHoneyComb.get(stack);
        return !honeyComb.isActive();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        EnumHoneyComb honeyComb = EnumHoneyComb.get(stack);
        if (tintIndex == 1) {
            return honeyComb.colour[0];
        }
        return honeyComb.colour[1];
    }
}
