package net.mcft.copy.hardcorebytesmod.item;

import blusunrize.immersiveengineering.api.MultiblockHandler;
import blusunrize.immersiveengineering.common.util.advancements.IEAdvancements;

import net.mcft.copy.hardcorebytesmod.HardcoreBytesMod;
import net.mcft.copy.hardcorebytesmod.network.UseHammerMessage;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemHammer extends ItemToolBase {

    public ItemHammer(ToolMaterial material) {
        super("pickaxe", material, 5.0F, 1.6F);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, IBlockState state) {
        return super.getHarvestLevel(stack, state) - 1;
    }

    @Override
    public float getEfficiency(ItemStack stack, IBlockState state) {
        return super.getEfficiency(stack, state) * 0.5F;
    }


    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return (int)(120 / this.material.getEfficiency());
    }

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (hand == EnumHand.MAIN_HAND) {
            player.setActiveHand(hand);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack onItemUseFinish(ItemStack stack, World world,
                                     EntityLivingBase entity) {
        if (!(entity instanceof EntityPlayer)) return stack;
        EntityPlayer player = (EntityPlayer)entity;

        RayTraceResult ray = Minecraft.getMinecraft().objectMouseOver;
        if (ray.typeOfHit != Type.BLOCK) return stack;
        BlockPos pos    = ray.getBlockPos();
        EnumFacing face = ray.sideHit;

        ItemStack newStack = stack.copy();
        if (!tryUse(newStack, player, pos, face)) return stack;
        ItemHammer.playSoundAndSpawnParticles(world, pos, face);
        HardcoreBytesMod.NETWORK.sendToServer(new UseHammerMessage(pos, face));
        return newStack;
    }

    public boolean tryUse(ItemStack stack, EntityPlayer player, BlockPos pos, EnumFacing face) {
        for (MultiblockHandler.IMultiblock mb : MultiblockHandler.getMultiblocks()) {
            if (!mb.isBlockTrigger(player.world.getBlockState(pos))
             || MultiblockHandler.fireMultiblockFormationEventPre(player, mb, pos, stack).isCanceled()
             || !mb.createStructure(player.world, pos, face, player)) continue;

            if (player instanceof EntityPlayerMP)
                IEAdvancements.TRIGGER_MULTIBLOCK.trigger((EntityPlayerMP)player, mb, stack);
            stack.damageItem(1, player);
            return true;
        }
        return false;
    }


    @SideOnly(Side.CLIENT)
    public static void playSoundAndSpawnParticles(World world, BlockPos pos, EnumFacing face) {
        int foX = face.getFrontOffsetX();
        int foY = face.getFrontOffsetY();
        int foZ = face.getFrontOffsetZ();

        double x = pos.getX() + 0.5 + foX * 0.51;
        double y = pos.getY() + 0.5 + foY * 0.51;
        double z = pos.getZ() + 0.5 + foZ * 0.51;
        world.playSound(x, y, z, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.PLAYERS,
                        0.5F, 0.4F + world.rand.nextFloat() * 0.1F, false);

        Axis axis = face.getAxis();
        for (int i = 0; i < 50; i++) {
            double px = x + ((axis != Axis.X) ? (world.rand.nextDouble() - 0.5) * 1.25 : 0.0);
            double py = y + ((axis != Axis.Y) ? (world.rand.nextDouble() - 0.5) * 1.25 : 0.0);
            double pz = z + ((axis != Axis.Z) ? (world.rand.nextDouble() - 0.5) * 1.25 : 0.0);
            double speed = world.rand.nextFloat() * 0.02;
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, px, py, pz,
                                foX * speed, foY * speed, foZ * speed);
        }
    }

}
