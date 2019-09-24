package net.mcft.copy.hardcorebytesmod.item;

import net.minecraft.item.Item.ToolMaterial;

import net.minecraftforge.common.util.EnumHelper;

public final class ToolMaterials {
    private ToolMaterials() {  }

    public static ToolMaterial FLINT =
        EnumHelper.addToolMaterial("flint", 1, 56, 2.5F, 1.0F, 6);

}
