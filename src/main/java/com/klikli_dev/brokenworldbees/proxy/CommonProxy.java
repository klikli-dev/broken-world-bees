package com.tao.brokenworldbees.proxy;

import com.tao.brokenworldbees.BrokenWorldBees;
import com.tao.brokenworldbees.ForgeEventHandlers;
import net.minecraft.item.Item;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandlers());
    }

    public Item registerItem(Item item) {
        return GameRegistry.register(item);
    }

    @SuppressWarnings("deprecation")
    public String localise(String s) {
        return I18n.translateToLocal(BrokenWorldBees.MODID + "." + s);
    }

    @SuppressWarnings("deprecation")
    public String localiseWithOutPrefix(String s) {
        return I18n.translateToLocal(s);
    }

    public void registerModel(Item item, int meta) {
    }
}
