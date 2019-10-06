package net.mcft.copy.hardcorebytesmod.network;

import io.netty.buffer.ByteBuf;

import net.mcft.copy.hardcorebytesmod.HardcoreBytesMod;
import net.mcft.copy.hardcorebytesmod.item.ItemHammer;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UseHammerMessage implements IMessage {

    public BlockPos pos;
    public EnumFacing face;

    public UseHammerMessage() {  }
    public UseHammerMessage(BlockPos pos, EnumFacing face) {
        this.pos  = pos;
        this.face = face;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.pos.getX());
        buf.writeInt(this.pos.getY());
        buf.writeInt(this.pos.getZ());
        buf.writeByte(this.face.getIndex());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        byte face = buf.readByte();

        this.pos  = new BlockPos(x, y, z);
        this.face = EnumFacing.VALUES[face];
    }


    public static class Handler
            implements IMessageHandler<UseHammerMessage, IMessage> {

        @Override
        public IMessage onMessage(UseHammerMessage message, MessageContext ctx) {
            if (ctx.side == Side.CLIENT) onMessageClient(message);
            else onMessageServer(message, ctx.getServerHandler().player);
            return null;
        }

        @SideOnly(Side.CLIENT)
        public void onMessageClient(UseHammerMessage message) {
            Minecraft.getMinecraft().addScheduledTask(() ->
                ItemHammer.playSoundAndSpawnParticles(
                    Minecraft.getMinecraft().world, message.pos, message.face));
        }

        public void onMessageServer(UseHammerMessage message, EntityPlayerMP player) {
            ItemStack stack = player.getHeldItemMainhand();
            if ((player.getHealth() <= 0.0F) || player.isSpectator()
             || !player.canPlayerEdit(message.pos, message.face, stack)) return;

            ItemHammer hammer = (stack.getItem() instanceof ItemHammer) ? (ItemHammer)stack.getItem() : null;
            if (hammer == null) return;

            double distanceSq = message.pos.distanceSqToCenter(player.posX, player.posY, player.posZ);
            double reachDistance = 1.0F + player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
            if (distanceSq > reachDistance * reachDistance) return;

            if (hammer.tryUse(stack, player, message.pos, message.face))
                HardcoreBytesMod.NETWORK.sendToAllAround(message, player.world, message.pos, 40, player);
        }

    }

}
