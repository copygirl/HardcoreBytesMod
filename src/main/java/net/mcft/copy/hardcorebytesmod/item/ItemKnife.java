package net.mcft.copy.hardcorebytesmod.item;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemKnife extends ItemToolBase {

    public static final double LEAVES_DAMAGE_CHANCE = 0.2;


    public ItemKnife(ToolMaterial material) {
        super(null, material, 2.0F, 2.6F);
    }

    @Override
    public boolean isEffective(ItemStack stack, IBlockState state) {
        Material material = state.getMaterial();
        return (material == Material.LEAVES)
            || (material == Material.PLANTS)
            || (material == Material.VINE);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        if (state.getMaterial() == Material.WEB)
            return 7.5F;
        else if (this.isEffective(stack, state))
            return 2.0F * this.material.getEfficiency();
        else return super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world,
                                    IBlockState state, BlockPos pos,
                                    EntityLivingBase entityLiving) {
        // Only apply damage sometimes when breaking leaves.
        if ((state.getMaterial() == Material.LEAVES)
         && (world.rand.nextDouble() >= LEAVES_DAMAGE_CHANCE))
            return true;

        // Since knives are effective on leaves, 1 damage should be applied.
        return super.onBlockDestroyed(stack, world, state, pos, entityLiving);
    }

}
