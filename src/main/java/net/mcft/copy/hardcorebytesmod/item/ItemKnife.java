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
        super(material, 2.0F, 2.4F);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        Material blockMaterial = state.getMaterial();
        // Slash through leaves really quickly!
        if (blockMaterial == Material.LEAVES)
            return 30.0F + 10.0F * this.material.getEfficiency();
        // Break through plants and vines a little faster.
        else if ((blockMaterial == Material.PLANTS) ||
                 (blockMaterial == Material.VINE))
            return this.material.getEfficiency();
        // Break cobweb half as fast as a sword.
        else if (blockMaterial == Material.WEB)
            return 7.5F;
        else return 1.0F;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world,
                                    IBlockState state, BlockPos pos,
                                    EntityLivingBase entityLiving) {
        // NOTE: Unlike other tools, knives will be damaged when
        //       destroying blocks that break instantly.

        Material material = state.getMaterial();
        // Only sometimes damaged when breaking leaves.
        if (material == Material.LEAVES) {
            if (world.rand.nextDouble() < LEAVES_DAMAGE_CHANCE)
                stack.damageItem(1, entityLiving);
        // Apply 1 damage when breaking plants or vines.
        } else if ((material == Material.PLANTS) ||
                   (material == Material.VINE))
            stack.damageItem(1, entityLiving);
        // Apply 2 damage when breaking any other block.
        else stack.damageItem(2, entityLiving);

        return true;
    }

}
