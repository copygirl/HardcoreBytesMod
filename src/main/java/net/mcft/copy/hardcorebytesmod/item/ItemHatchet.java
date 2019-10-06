package net.mcft.copy.hardcorebytesmod.item;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemHatchet extends ItemToolBase {

    public static final double LEAVES_DAMAGE_CHANCE = 0.1;

    public ItemHatchet(ToolMaterial material) {
        super("axe", material, 4.0F, 1.2F);
    }

    @Override
    public boolean isEffective(ItemStack stack, IBlockState state) {
        return (state.getMaterial() == Material.LEAVES)
            || super.isEffective(stack, state);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        if (state.getMaterial() == Material.LEAVES)
            return 1.5F * this.material.getEfficiency();
        return super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world,
                                    IBlockState state, BlockPos pos,
                                    EntityLivingBase entityLiving) {
        // Only apply damage sometimes when breaking leaves.
        if ((state.getMaterial() == Material.LEAVES)
         && (world.rand.nextDouble() >= LEAVES_DAMAGE_CHANCE))
            return true;

        return super.onBlockDestroyed(stack, world, state, pos, entityLiving);
    }

}
