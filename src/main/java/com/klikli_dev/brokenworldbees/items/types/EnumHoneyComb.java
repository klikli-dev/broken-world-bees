package com.tao.brokenworldbees.items.types;

import binnie.core.util.I18N;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.items.types.IEBEnumItem;
import com.blakebr0.mysticalagriculture.items.ModItems;
import com.tao.brokenworldbees.BrokenWorldBees;
import forestry.api.recipes.RecipeManagers;
import forestry.apiculture.PluginApiculture;
import forestry.core.PluginCore;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

public enum EnumHoneyComb implements IEBEnumItem {

    PROSPEROUS(0x9dbbbb, 0x9ababa) {
        @Override
        protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
            copyProducts(binnie.extrabees.items.types.EnumHoneyComb.STONE);
            tryAddProduct(ModItems.itemProsperityShard, 1.0f);
        }
    },
    URANIOUS(2031360, 4303667) {
        @Override
        protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
            copyProducts(binnie.extrabees.items.types.EnumHoneyComb.STONE);
            tryAddProduct("dustUranium", 0.50f);
        }
    };

    public int[] colour;
    public Map<ItemStack, Float> products;
    public boolean active;
    public boolean deprecated;

    EnumHoneyComb() {
        this(16777215, 16777215);
        this.active = false;
        this.deprecated = true;
    }

    EnumHoneyComb(final int colour, final int colour2) {
        this.colour = new int[0];
        this.products = new LinkedHashMap<>();
        this.active = true;
        this.deprecated = false;
        this.colour = new int[]{colour, colour2};
    }

    public static EnumHoneyComb get(final ItemStack itemStack) {
        final int i = itemStack.getItemDamage();
        if (i >= 0 && i < values().length) {
            return values()[i];
        }
        return values()[0];
    }

    public static void addSubtypes() {
        OreDictionary.registerOre("beeComb", new ItemStack(ExtraBees.comb, 1, 32767));
        ItemStack beeswax = PluginCore.items.beeswax.getItemStack();
        ItemStack honeyDrop = PluginApiculture.items.honeyDrop.getItemStack();
        for (EnumHoneyComb comb : values()) {
            comb.addSubtypes(beeswax, honeyDrop);
        }
    }

    protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
    }

    public void addRecipe() {
        if (isActive()) {
            RecipeManagers.centrifugeManager.addRecipe(20, this.get(1), this.products);
        }
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public ItemStack get(final int size) {
        return new ItemStack(BrokenWorldBees.comb, size, this.ordinal());
    }

    @Override
    public String getName(final ItemStack stack) {
        return I18N.localise(BrokenWorldBees.MODID + ".item.comb." + this.name().toLowerCase());
    }

    public boolean addProduct(@Nullable Item item, final Float chance) {
        return addProduct(item == null ? null : new ItemStack(item), chance);
    }

    public boolean addProduct(final ItemStack item, final Float chance) {
        if (item == null) {
            return false;
        }
        this.products.put(item.copy(), chance);
        return true;
    }

    public void tryAddProduct(final ItemStack item, final Float chance) {
        this.active = this.addProduct(item, chance);
    }

    public void tryAddProduct(@Nullable final Item item, final Float chance) {
        this.active = this.addProduct(item, chance);
    }

    public void tryAddProduct(final String oreDict, final Float chance) {
        if (!OreDictionary.getOres(oreDict).isEmpty()) {
            this.tryAddProduct(OreDictionary.getOres(oreDict).get(0), chance);
        } else {
            this.active = false;
        }
    }

    public void tryAddProduct(final IEBEnumItem type, final Float chance) {
        this.tryAddProduct(type.get(1), chance);
        this.active = (this.active && type.isActive());
    }

    public void copyProducts(final binnie.extrabees.items.types.EnumHoneyComb comb) {
        this.products.putAll(comb.products);
    }

}
