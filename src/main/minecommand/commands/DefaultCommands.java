/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecommand.commands;

import minecommand.MineCommandMod;
import modcmd.commands.Command;
import modcmd.commands.CommandParameter;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

/**
 *
 * @author Ryan
 */
public class DefaultCommands {

    @Command("broadcast")
    public static void message(@CommandParameter(tag = "m", name = "message", description = "The message to broadcast.", type = "String") String message) {
        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(message));
    }

    @Command("version")
    public static String getVersion() {
        return MineCommandMod.VERSION;
    }

    @Command("me")
    public static void me(
            @CommandParameter(tag = "u", name = "user", description = "The user.", type = "User", defaultValue = "%user%") ICommandSender user,
            @CommandParameter(tag = "m", name = "message", description = "The message to print.", type = "String") String message) {

        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(user.getCommandSenderName() + " " + message));
    }

    @Command("here")
    public static void here(
            @CommandParameter(tag = "u", name = "user", description = "The user.", type = "User", defaultValue = "%user%") ICommandSender user,
            @CommandParameter(tag = "x", name = "x-pos", description = "The x-pos.", type = "usrpos", defaultValue = "%") int x,
            @CommandParameter(tag = "y", name = "y-pos", description = "The y-pos.", type = "usrpos", defaultValue = "%") int y,
            @CommandParameter(tag = "z", name = "z-pos", description = "The z-pos.", type = "usrpos", defaultValue = "%") int z
    ) {
        user.addChatMessage(new ChatComponentText(String.format("Position: [%1$d,%2$d,%3$d]", x, y, z)));
    }

}
