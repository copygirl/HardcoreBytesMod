package net.mcft.copy.hardcorebytesmod.network;

import java.util.List;
import java.util.function.Predicate;

import net.mcft.copy.hardcorebytesmod.HardcoreBytesMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class HBNetwork extends SimpleNetworkWrapper {

    public HBNetwork() {
        super(HardcoreBytesMod.MOD_ID);

        registerMessage(UseHammerMessage.Handler.class, UseHammerMessage.class, 0, Side.CLIENT);
        registerMessage(UseHammerMessage.Handler.class, UseHammerMessage.class, 0, Side.SERVER);
    }

    /** Sends a message from the server to a player. */
    public void sendTo(IMessage message, EntityPlayer player) {
        this.sendTo(message, (EntityPlayerMP)player);
    }

    /** Sends a message to everyone on the server, except to the specified player. */
    public void sendToAll(IMessage message, EntityPlayer except) {
        this.sendToAll(message, player -> (player != except));
    }
    /** Sends a message to everyone on the server, except to players not matching the specified filter. */
    public void sendToAll(IMessage message, Predicate<EntityPlayer> filter) {
        for (EntityPlayer player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
            if (filter.test(player)) this.sendTo(message, player);
    }

    /** Sends a message to everyone around a point. */
    public void sendToAllAround(IMessage message, World world, double x, double y, double z, double distance) {
        this.sendToAllAround(message, new TargetPoint(world.provider.getDimension(), x, y, z, distance));
    }
    /** Sends a message to everyone around a block position, except to the specific player. */
    public void sendToAllAround(IMessage message, World world, BlockPos pos,
                                double distance, EntityPlayer except) {
        this.sendToAllAround(message, world, pos, distance, player -> (player != except));
    }
    /** Sends a message to everyone around a block position, except to players not matching the specified filter. */
    public void sendToAllAround(IMessage message, World world, BlockPos pos,
                                double distance, Predicate<EntityPlayer> filter) {
        this.sendToAllAround(message, world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, distance, filter);
    }
    /** Sends a message to everyone around a point, except to the specific player. */
    public void sendToAllAround(IMessage message, World world, double x, double y, double z,
                                double distance, EntityPlayer except) {
        this.sendToAllAround(message, world, x, y, z, distance, player -> (player != except));
    }
    /** Sends a message to everyone around a point, except to players not matching the specified filter. */
    public void sendToAllAround(IMessage message, World world, double x, double y, double z,
                                double distance, Predicate<EntityPlayer> filter) {
        for (EntityPlayer player : (List<EntityPlayer>)world.playerEntities) {
            if (!filter.test(player)) continue;
            double dx = x - player.posX;
            double dy = y - player.posY;
            double dz = z - player.posZ;
            if ((dx * dx + dy * dy + dz * dz) < (distance * distance))
                this.sendTo(message, player);
        }
    }

    /** Sends a message to a everyone tracking an entity. If sendToEntity is
     *  true and the entity is a player, also sends the message to them. */
    public void sendToAllTracking(IMessage message, Entity entity, boolean sendToEntity) {
        ((WorldServer)entity.world).getEntityTracker()
            .sendToTracking(entity, this.getPacketFrom(message));
        if (sendToEntity && (entity instanceof EntityPlayer))
            this.sendTo(message, (EntityPlayer)entity);
    }

}
