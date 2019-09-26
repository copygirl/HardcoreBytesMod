package net.mcft.copy.hardcorebytesmod.item;

import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemToolBase extends Item {

    protected final String toolType;
    protected final ToolMaterial material;
    protected float attackDamage;
    protected float attackSpeed;

    public ItemToolBase(String toolType, ToolMaterial material,
                        float baseAttackDamage, float attackSpeed) {
        this.toolType     = toolType;
        this.material     = material;
        this.maxStackSize = 1;
        this.setMaxDamage(this.material.getMaxUses() - 1);

        this.attackDamage = baseAttackDamage + this.material.getAttackDamage();
        this.attackSpeed  = attackSpeed;
    }

    public boolean isEffective(IBlockState state) {
        Block block = state.getBlock();
        return (toolType != null)
            && block.isToolEffective(toolType, state)
            && block.getHarvestLevel(state) <= this.material.getHarvestLevel();
    }


    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return this.isEffective(state)
            ? this.material.getEfficiency()
            : 1.0F;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world,
                                    IBlockState state, BlockPos pos,
                                    EntityLivingBase entityLiving) {
        // Apply 1 damage when breaking appropriate blocks.
        if (this.isEffective(state))
            stack.damageItem(1, entityLiving);
        // Apply 2 damage when breaking anything doesn't break instantly.
        else if (state.getBlockHardness(world, pos) > 0.0)
            stack.damageItem(2, entityLiving);
        return true;
    }


    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(
        EntityEquipmentSlot slot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        if (slot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(
                ATTACK_DAMAGE_MODIFIER, "Tool modifier", this.attackDamage - 1.0, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(
                ATTACK_SPEED_MODIFIER, "Tool modifier", this.attackSpeed - 4.0, 0));
        }
        return multimap;
    }

}
