package net.mcft.copy.hardcorebytesmod.proxy;

import net.mcft.copy.hardcorebytesmod.HardcoreBytesMod;
import net.mcft.copy.hardcorebytesmod.item.ItemWorkKnapping;

import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

    public CommonProxy() {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(HardcoreBytesMod.ITEMS);
        MinecraftForge.EVENT_BUS.register(new ItemWorkKnapping.KnappingHandler());
    }


    public void preInit() {
        HardcoreBytesMod.ITEMS.initialize();
    }

    public void init() {

    }

    public void postInit() {

    }

}
