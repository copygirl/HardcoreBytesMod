package net.mcft.copy.hardcorebytesmod.item;

import com.google.common.collect.Multimap;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemToolBase extends Item {

    protected final ToolMaterial material;
    protected float attackDamage;
    protected float attackSpeed;

    public ItemToolBase(ToolMaterial material, float baseAttackDamage, float attackSpeed) {
        this.material     = material;
        this.maxStackSize = 1;
        this.setMaxDamage(this.material.getMaxUses() - 1);

        this.attackDamage = baseAttackDamage + this.material.getAttackDamage();
        this.attackSpeed  = attackSpeed;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(
        EntityEquipmentSlot equipmentSlot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot, stack);
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
                         new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", (double)this.attackDamage - 1.0, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
                         new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double)this.attackSpeed - 4.0, 0));
        }
        return multimap;
    }

}
