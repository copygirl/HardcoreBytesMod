package net.mcft.copy.hardcorebytesmod.content;

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
    public Item FLINT_SPADE_HEAD;
    public Item FLINT_HATCHET_HEAD;

    public Item PRIMITIVE_FLINT_KNIFE;
    public Item PRIMITIVE_FLINT_SPADE;
    public Item PRIMITIVE_FLINT_HATCHET;


    public HBItems() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void initialize() {

        PLANT_FIBER = add(new Item(), "plant_fiber");

        PLANT_FIBER_MESH = add(new Item().setMaxStackSize(1), "plant_fiber_mesh");
        STRING_MESH      = add(new Item().setMaxStackSize(1), "string_mesh");

        PRIMITIVE_PLANT_FIBER_SIEVE = add(new ItemCraftingTool(20), "primitive_plant_fiber_sieve");
        PRIMITIVE_STRING_SIEVE      = add(new ItemCraftingTool(64), "primitive_string_sieve");

        FLINT_KNIFE_BLADE  = add(new Item().setMaxStackSize(4), "flint_knife_blade");
        FLINT_SPADE_HEAD   = add(new Item().setMaxStackSize(4), "flint_spade_head");
        FLINT_HATCHET_HEAD = add(new Item().setMaxStackSize(4), "flint_hatchet_head");

        PRIMITIVE_FLINT_KNIFE   = add(new ItemKnife(ToolMaterials.FLINT), "primitive_flint_knife");
        PRIMITIVE_FLINT_SPADE   = add(new ItemSpade(ToolMaterials.FLINT), "primitive_flint_spade");
        PRIMITIVE_FLINT_HATCHET = add(new ItemHatchet(ToolMaterials.FLINT), "primitive_flint_hatchet");

    }

    private Item add(Item item, String name) {
        this.ALL_ITEMS.add(item);
        item.setUnlocalizedName(name);
        item.setRegistryName(name);
        item.setCreativeTab(HardcoreBytesMod.CREATIVE_TAB);
        return item;
    }

    @SubscribeEvent
    public void onRegisterItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        for (Item item : this.ALL_ITEMS) registry.register(item);
    }

}
