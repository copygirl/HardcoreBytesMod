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
        super(material, 4.0F, 1.2F);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        Material blockMaterial = state.getMaterial();
        // Hack through leaves really quickly!
        if (blockMaterial == Material.LEAVES)
            return 30.0F + 15.0F * this.material.getEfficiency();
        // Break through wood blocks a little faster.
        else if (blockMaterial == Material.WOOD)
            return this.material.getEfficiency();
        else return 1.0F;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world,
                                    IBlockState state, BlockPos pos,
                                    EntityLivingBase entityLiving) {
        Material material = state.getMaterial();
        // Only sometimes damaged when breaking leaves.
        if (material == Material.LEAVES) {
            if (world.rand.nextDouble() < LEAVES_DAMAGE_CHANCE)
                stack.damageItem(1, entityLiving);
        // Apply 1 damage when breaking wood blocks.
        } else if (material == Material.WOOD)
            stack.damageItem(1, entityLiving);
        // Apply 2 damage when breaking anything doesn't break instantly.
        else if (state.getBlockHardness(world, pos) > 0.0)
            stack.damageItem(2, entityLiving);
        return true;
    }

}
