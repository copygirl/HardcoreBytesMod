package net.mcft.copy.hardcorebytesmod;

import net.mcft.copy.hardcorebytesmod.content.HBItems;
import net.mcft.copy.hardcorebytesmod.item.ItemWorkKnapping.KnappingHandler;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.logging.log4j.Logger;

@Mod(modid   = HardcoreBytesMod.MODID,
     name    = HardcoreBytesMod.NAME,
     version = HardcoreBytesMod.VERSION)
public class HardcoreBytesMod {

    public static final String MODID   = "hardcorebytesmod";
    public static final String NAME    = "Hardcore Bytes Mod";
    public static final String VERSION = "@VERSION@";

    public static Logger LOG;

    // Feature Handlers
    public static KnappingHandler KNAPPING;

    // Content Classes
    public static HBItems ITEMS;


    public static CreativeTabs CREATIVE_TAB = new CreativeTabs(MODID) {
        @Override
        public ItemStack getTabIconItem()
            { return new ItemStack(ITEMS.PRIMITIVE_FLINT_KNIFE); }
    };


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOG = event.getModLog();

        KNAPPING = new KnappingHandler();

        ITEMS = new HBItems();

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(HardcoreBytesMod.ITEMS);
        MinecraftForge.EVENT_BUS.register(HardcoreBytesMod.KNAPPING);

        HardcoreBytesMod.ITEMS.initialize();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRegisterModels(ModelRegistryEvent event) {
        for (Item item : ITEMS.ALL_ITEMS)
            ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

}
