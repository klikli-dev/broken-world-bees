package com.tao.brokenworldbees;

import com.tao.brokenworldbees.genetics.BrokenWorldBeeDefinition;
import com.tao.brokenworldbees.init.ItemRegister;
import com.tao.brokenworldbees.init.RecipeRegister;
import com.tao.brokenworldbees.items.types.EnumHoneyComb;
import com.tao.brokenworldbees.proxy.CommonProxy;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = BrokenWorldBees.MODID,
        version = BrokenWorldBees.VERSION,
        name = BrokenWorldBees.MODNAME,
        dependencies = "required-after:extrabees;" +
                "required-after:immersiveengineering;")
public class BrokenWorldBees {
    public static final String MODID = "brokenworldbees";
    public static final String MODNAME = "Broken World Bees";
    public static final String VERSION = "1.0.0";

    @Mod.Instance(MODID)
    public static BrokenWorldBees instance;

    @SidedProxy(clientSide = "com.tao.brokenworldbees.proxy.ClientProxy", serverSide = "com.tao.brokenworldbees.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static Item comb;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ItemRegister.preInitItems();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        this.proxy.init(event);
        EnumHoneyComb.addSubtypes();
        BrokenWorldBeeDefinition.doInit();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        RecipeRegister.postInitRecipes();
    }


}
