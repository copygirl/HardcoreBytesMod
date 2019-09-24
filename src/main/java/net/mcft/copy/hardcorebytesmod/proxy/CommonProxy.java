package net.mcft.copy.hardcorebytesmod.proxy;

import net.mcft.copy.hardcorebytesmod.HardcoreBytesMod;

import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

    public CommonProxy() {
        MinecraftForge.EVENT_BUS.register(this);
    }


    public void preInit() {
        HardcoreBytesMod.ITEMS.initialize();
    }

    public void init() {

    }

    public void postInit() {

    }

}
