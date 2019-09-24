package net.mcft.copy.hardcorebytesmod.content;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.mcft.copy.hardcorebytesmod.HardcoreBytesMod;
import net.mcft.copy.hardcorebytesmod.item.*;

import net.minecraft.item.Item;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class HBItems {

    public final List<Item> ALL_ITEMS = new ArrayList<>();

    public Item PLANT_FIBER;

    public Item PLANT_FIBER_MESH;
    public Item STRING_MESH;

    public Item PRIMITIVE_PLANT_FIBER_SIEVE;
    public Item PRIMITIVE_STRING_SIEVE;

    public Item FLINT_KNIFE_BLADE;
    public Item FLINT_HATCHET_HEAD;

    public Item PRIMITIVE_FLINT_KNIFE;
    public Item PRIMITIVE_FLINT_HATCHET;


    public HBItems() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void initialize() {

        PLANT_FIBER = new Item();

        PLANT_FIBER_MESH = new Item().setMaxStackSize(1);
        STRING_MESH      = new Item().setMaxStackSize(1);

        PRIMITIVE_PLANT_FIBER_SIEVE = new ItemCraftingTool(20);
        PRIMITIVE_STRING_SIEVE      = new ItemCraftingTool(64);

        FLINT_KNIFE_BLADE  = new Item().setMaxStackSize(4);
        FLINT_HATCHET_HEAD = new Item().setMaxStackSize(4);

        PRIMITIVE_FLINT_KNIFE   = new ItemKnife(ToolMaterials.FLINT);
        PRIMITIVE_FLINT_HATCHET = new ItemHatchet(ToolMaterials.FLINT);

        // Screw naming and registering everything by hand.
        // Let's just use reflection to do it for us!
        try {
            for (Field field : this.getClass().getFields())
            if (Item.class.isAssignableFrom(field.getType())) {
                Item item = (Item)field.get(this);
                String name = field.getName().toLowerCase();

                item.setUnlocalizedName(name);
                item.setRegistryName(name);
                item.setCreativeTab(HardcoreBytesMod.CREATIVE_TAB);

                ALL_ITEMS.add(item);
            }
        } catch (IllegalArgumentException |
                 IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @SubscribeEvent
    public void onRegisterItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        for (Item item : ALL_ITEMS) registry.register(item);
    }

}
