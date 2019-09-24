package net.mcft.copy.hardcorebytesmod;

import net.mcft.copy.hardcorebytesmod.content.HBItems;
import net.mcft.copy.hardcorebytesmod.proxy.CommonProxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;

@Mod(modid   = HardcoreBytesMod.MODID,
     name    = HardcoreBytesMod.NAME,
     version = HardcoreBytesMod.VERSION)
public class HardcoreBytesMod {

    public static final String MODID   = "hardcorebytesmod";
    public static final String NAME    = "Hardcore Bytes Mod";
    public static final String VERSION = "@VERSION@";

    public static Logger LOG;

    @SidedProxy(clientSide = "net.mcft.copy.hardcorebytesmod.proxy.ClientProxy",
                serverSide = "net.mcft.copy.hardcorebytesmod.proxy.CommonProxy")
	public static CommonProxy PROXY;

    public static HBItems ITEMS;

    public static CreativeTabs CREATIVE_TAB = new CreativeTabs(MODID) {
        @Override public ItemStack getTabIconItem() {
            return new ItemStack(ITEMS.PRIMITIVE_FLINT_KNIFE); } };


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOG = event.getModLog();

        ITEMS = new HBItems();

        PROXY.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        PROXY.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        PROXY.postInit();
    }

}
