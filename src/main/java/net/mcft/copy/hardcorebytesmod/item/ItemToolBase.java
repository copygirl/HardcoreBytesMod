package net.mcft.copy.hardcorebytesmod.item;

import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemToolBase extends Item {

    protected final String toolType;
    protected final ToolMaterial material;
    protected final boolean isWeapon;
    protected float attackDamage;
    protected float attackSpeed;

    public ItemToolBase(String toolType, ToolMaterial material,
                        float baseAttackDamage, float attackSpeed,
                        boolean isWeapon) {
        this.toolType = toolType;
        this.material = material;
        this.isWeapon = isWeapon;

        this.attackDamage = baseAttackDamage + this.material.getAttackDamage();
        this.attackSpeed  = attackSpeed;
        
        this.maxStackSize = 1;
        this.setMaxDamage(this.material.getMaxUses() - 1);
    }

    public int getHarvestLevel(ItemStack stack, IBlockState state) {
        return (this.toolType != null) ? this.material.getHarvestLevel() : -1;
    }

    public float getEfficiency(ItemStack stack, IBlockState state) {
        return this.material.getEfficiency();
    }

    public boolean isEffective(ItemStack stack, IBlockState state) {
        Block block = state.getBlock();
        int harvestLevel = this.getHarvestLevel(stack, this.toolType, null, state);
        return (harvestLevel >= 0)
            && (block.getHarvestLevel(state) <= harvestLevel)
            && block.isToolEffective(toolType, state);
    }


    @Override
    public final int getHarvestLevel(ItemStack stack, String toolType,
                                     EntityPlayer player, IBlockState state) {
        return this.getHarvestLevel(stack, state);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return this.isEffective(stack, state)
            ? this.getEfficiency(stack, state)
            : 1.0F;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world,
                                    IBlockState state, BlockPos pos,
                                    EntityLivingBase entityLiving) {
        // Apply 1 damage when breaking appropriate blocks.
        if (this.isEffective(stack, state))
            stack.damageItem(1, entityLiving);
        // Apply 2 damage when breaking anything doesn't break instantly.
        else if (state.getBlockHardness(world, pos) > 0.0)
            stack.damageItem(2, entityLiving);
        return true;
    }
    
    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target,
                             EntityLivingBase attacker) {
        stack.damageItem(isWeapon ? 1 : 2, attacker);
        return true;
    }


    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(
            EntityEquipmentSlot slot, ItemStack stack) {
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
