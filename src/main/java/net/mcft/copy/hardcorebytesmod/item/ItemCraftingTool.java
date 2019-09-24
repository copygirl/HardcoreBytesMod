package net.mcft.copy.hardcorebytesmod.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCraftingTool extends Item {

    public ItemCraftingTool(int maxDamage) {
        this.setMaxStackSize(1);
        this.setMaxDamage(maxDamage - 1);
    }

    @Override
    public boolean hasContainerItem() { return true; }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        ItemStack result = stack.copy();
        result.setItemDamage(stack.getItemDamage() + 1);
        return result;
    }

}