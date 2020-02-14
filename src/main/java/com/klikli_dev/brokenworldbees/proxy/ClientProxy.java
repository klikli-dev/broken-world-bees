package com.tao.brokenworldbees.proxy;

import binnie.extrabees.items.IItemModelProvider;
import forestry.core.models.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nonnull;

public class ClientProxy extends CommonProxy {

    private static final ModelManager modelManager = ModelManager.getInstance();

    @Override
    public Item registerItem(Item item) {
        modelManager.registerItemClient(item);
        if (item instanceof IItemModelProvider) {
            ((IItemModelProvider) item).registerModel(item);
        }
        return super.registerItem(item);
    }

    @Override
    @SuppressWarnings("all")
    public void registerModel(@Nonnull Item item, int meta) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
