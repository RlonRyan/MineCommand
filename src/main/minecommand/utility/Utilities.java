/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecommand.utility;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

/**
 *
 * @author Ryan
 */
public class Utilities {
    
    public static void sendMessage(ICommandSender reciever, String... lines) {
        for (String line : lines) {
            reciever.addChatMessage(new ChatComponentText(line));
        }
    }
    
    public static String getDimensionName(int dim) {
        return MinecraftServer.getServer().worldServerForDimension(dim).provider.getDimensionName();
    }
    
}
