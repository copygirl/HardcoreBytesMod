package net.mcft.copy.hardcorebytesmod.content;

import java.util.ArrayList;
import java.util.List;

import net.mcft.copy.hardcorebytesmod.HardcoreBytesMod;
import net.mcft.copy.hardcorebytesmod.item.*;

import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class HBItems {

    public final List<Item> ALL_ITEMS = new ArrayList<>();

    public Item TWINE_MESH;
    public Item STRING_MESH;

    public Item PRIMITIVE_TWINE_SIEVE;
    public Item PRIMITIVE_STRING_SIEVE;

    public Item FLINT_KNIFE_BLADE;
    public Item FLINT_SPADE_HEAD;
    public Item FLINT_HATCHET_HEAD;
    public Item FLINT_HOE_HEAD;
    public Item FLINT_HAMMER_HEAD; // TODO: Flint hammer doesn't make sense.

    public Item PRIMITIVE_FLINT_KNIFE;
    public Item PRIMITIVE_FLINT_SPADE;
    public Item PRIMITIVE_FLINT_HATCHET;
    public Item PRIMITIVE_FLINT_HOE;
    public Item PRIMITIVE_FLINT_HAMMER;


    public void initialize() {

        TWINE_MESH  = add(new Item().setMaxStackSize(1), "twine_mesh");
        STRING_MESH = add(new Item().setMaxStackSize(1), "string_mesh");

        PRIMITIVE_TWINE_SIEVE  = add(new ItemCraftingTool(24), "primitive_twine_sieve");
        PRIMITIVE_STRING_SIEVE = add(new ItemCraftingTool(64), "primitive_string_sieve");

        FLINT_KNIFE_BLADE  = add(new ItemWorkKnapping(), "flint_knife_blade");
        FLINT_SPADE_HEAD   = add(new ItemWorkKnapping(), "flint_spade_head");
        FLINT_HATCHET_HEAD = add(new ItemWorkKnapping(), "flint_hatchet_head");
        FLINT_HOE_HEAD     = add(new ItemWorkKnapping(), "flint_hoe_head");
        FLINT_HAMMER_HEAD  = add(new ItemWorkKnapping(), "flint_hammer_head");

        PRIMITIVE_FLINT_KNIFE   = add(new ItemKnife(ToolMaterials.FLINT), "primitive_flint_knife");
        PRIMITIVE_FLINT_SPADE   = add(new ItemSpade(ToolMaterials.FLINT), "primitive_flint_spade");
        PRIMITIVE_FLINT_HATCHET = add(new ItemHatchet(ToolMaterials.FLINT), "primitive_flint_hatchet");
        PRIMITIVE_FLINT_HOE     = add(new ItemHoe(ToolMaterials.FLINT), "primitive_flint_hoe");
        PRIMITIVE_FLINT_HAMMER  = add(new ItemHammer(ToolMaterials.FLINT), "primitive_flint_hammer");

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
