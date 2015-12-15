/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecommand.commands;

import modcmd.commands.Command;
import modcmd.commands.CommandParameter;
import modcmd.user.CommandUser;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.Teleporter;
import net.minecraftforge.common.DimensionManager;

/**
 *
 * @author RlonRyan
 */
public class DefaultTeleportCommands {

    @Command(
            name = "tp",
            about = "Teleports the user to a location."
    )
    public static void teleport(
            @CommandUser EntityPlayerMP player,
            @CommandParameter(tag = "x", name = "X-Pos", description = "The x-pos to teleport to", type = "coordinate", defaultValue = "%") int x,
            @CommandParameter(tag = "y", name = "Y-Pos", description = "The y-pos to teleport to", type = "coordinate", defaultValue = "%") int y,
            @CommandParameter(tag = "z", name = "Z-Pos", description = "The z-pos to teleport to", type = "coordinate", defaultValue = "%") int z,
            @CommandParameter(tag = "d", name = "Dimension", description = "The dimension to teleport to", type = "coordinate", defaultValue = "%") int dim
    ) {

        if (!DimensionManager.isDimensionRegistered(dim)) {
            player.addChatComponentMessage(new ChatComponentText("No dimenison: " + dim));
            return;
        }

        if (player.dimension != dim) {
            DimensionManager.initDimension(dim);
            MinecraftServer.getServer().getConfigurationManager()
                    .transferPlayerToDimension(player, dim, new Teleporter(MinecraftServer.getServer().worldServerForDimension(dim)));
        }

        if (player.isRiding()) {
            player.mountEntity(null);
        }
        player.playerNetServerHandler.setPlayerLocation(x, y, z, player.cameraPitch, player.cameraYaw);
    }

    @Command(
            name = "tpa",
            about = "Teleports the user to a location."
    )
    public static void teleportTo(
            @CommandUser EntityPlayerMP player,
            @CommandParameter(tag = "t", name = "Target", description = "The player to teleport to.", type = "player") EntityPlayerMP target
    ) {

        if (player.dimension != target.dimension) {
            DimensionManager.initDimension(target.dimension);
            MinecraftServer.getServer().getConfigurationManager()
                    .transferPlayerToDimension(player, target.dimension, new Teleporter(MinecraftServer.getServer().worldServerForDimension(target.dimension)));
        }

        if (player.isRiding()) {
            player.mountEntity(null);
        }
        player.playerNetServerHandler.setPlayerLocation(target.posX, target.posY, target.posZ, target.cameraPitch, target.cameraYaw);
    }

}
