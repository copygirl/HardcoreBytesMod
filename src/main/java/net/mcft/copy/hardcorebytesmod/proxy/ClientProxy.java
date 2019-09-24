package net.mcft.copy.hardcorebytesmod.proxy;

import net.mcft.copy.hardcorebytesmod.HardcoreBytesMod;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @SubscribeEvent
    public void onRegisterModels(ModelRegistryEvent event) {
        for (Item item : HardcoreBytesMod.ITEMS.ALL_ITEMS)
            ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

}
