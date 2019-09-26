package net.mcft.copy.hardcorebytesmod.item;

import com.google.common.collect.Multimap;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemWorkKnapping extends Item {

    protected float workSpeed = 2.0F;
    protected Material[] validWorkSurfaces = { Material.ROCK, Material.IRON };
    protected SoundEvent workSound = new SoundEvent(new ResourceLocation("block.stone.hit"));


    public ItemWorkKnapping(int workSteps) {
        this.setMaxStackSize(1);
        this.setMaxDamage(workSteps);
    }

    public boolean isValidKnappingSurface(ItemStack stack, World world,
                                          BlockPos pos, EnumFacing face) {
        IBlockState state = world.getBlockState(pos);
        return state.isSideSolid(world, pos, face)
            && ArrayUtils.contains(this.validWorkSurfaces, state.getMaterial());
    }

    public void workItem(ItemStack stack, World world,
                         BlockPos pos, EnumFacing face) {
        stack.setItemDamage(stack.getItemDamage() - 1);

        float x = pos.getX() + face.getFrontOffsetX() * 0.5F;
        float y = pos.getY() + face.getFrontOffsetY() * 0.5F;
        float z = pos.getZ() + face.getFrontOffsetZ() * 0.5F;
        world.playSound(x, y, z, this.workSound, SoundCategory.PLAYERS, 0.5F, 2.0F, false);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(
            EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        if (slot == EntityEquipmentSlot.MAINHAND)
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(
                ATTACK_SPEED_MODIFIER, "Tool modifier", this.workSpeed - 4.0, 0));
        return multimap;
    }


    public static class KnappingHandler {

        @SubscribeEvent
        public void onLeftClickBlock(LeftClickBlock event) {
            EntityPlayer player = event.getEntityPlayer();
            ItemStack stack     = player.getHeldItemMainhand();
            if (!(stack.getItem() instanceof ItemWorkKnapping)) return;
            ItemWorkKnapping item = (ItemWorkKnapping)stack.getItem();
            if ((stack.getItemDamage() > 0)                 // Work item isn't already finished.
             && (player.getCooledAttackStrength(0) >= 0.9F) // Cooldown period is over.
             && item.isValidKnappingSurface(stack, event.getWorld(), event.getPos(), event.getFace())) {
                item.workItem(stack, event.getWorld(), event.getPos(), event.getFace());
                player.resetCooldown();
                event.setCanceled(true);
            }
        }
    }

}
