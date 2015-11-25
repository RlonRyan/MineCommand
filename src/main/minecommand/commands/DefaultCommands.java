/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecommand.commands;

import minecommand.MineCommandMod;
import modcmd.commands.Command;
import modcmd.commands.CommandParameter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldSettings;

/**
 *
 * @author Ryan
 */
public class DefaultCommands {

    @Command("broadcast")
    public static void message(@CommandParameter(tag = "m", name = "message", description = "The message to broadcast.", type = "String") String message) {
        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("[Broadcast] " + message));
    }

    @Command("version")
    public static String getVersion() {
        return MineCommandMod.VERSION;
    }
    
    @Command("gm")
    public static void gameMode(
            @CommandParameter(tag = "p", name = "player", description = "The target player.", type = "player", defaultValue = "%") EntityPlayer user,
            @CommandParameter(tag = "m", name = "mode", description = "The gamemode.", type = "gamemode", defaultValue = "%") WorldSettings.GameType mode
    ) {
        user.setGameType(mode);
    }
    
    @Command("fly")
    public static void fly(
            @CommandParameter(tag = "p", name = "player", description = "The target player.", type = "player", defaultValue = "%") EntityPlayer user,
            @CommandParameter(tag = "f", name = "fly", description = "If the player can fly.", type = "bool") boolean canFly
    ) {
        user.addChatMessage(new ChatComponentText("Old flying ability: " + user.capabilities.allowFlying));
        user.capabilities.allowFlying = canFly;
        user.sendPlayerAbilities();
        user.addChatMessage(new ChatComponentText("Updated flying ability to: " + canFly));
    }
    
    @Command("speed")
    public static void speed(
            @CommandParameter(tag = "p", name = "player", description = "The target player.", type = "player", defaultValue = "%") EntityPlayer user,
            @CommandParameter(tag = "s", name = "speed", description = "Speed mulitiplier", type = "integer", defaultValue = "1") int speed
    ) {
        speed = speed < 1 ? 1 : speed;
        speed = speed > 10 ? 10 : speed;
        user.addChatMessage(new ChatComponentText("Old speed: " + user.capabilities.getWalkSpeed() + ", " + user.capabilities.getFlySpeed()));
        user.capabilities.setPlayerWalkSpeed(0.1f * speed);
        user.capabilities.setFlySpeed(0.05f * speed);
        user.sendPlayerAbilities();
        user.addChatMessage(new ChatComponentText("New speed: " + user.capabilities.getWalkSpeed() + ", " + user.capabilities.getFlySpeed()));
    }
    
    @Command("speed")
    public static void speed(
            @CommandParameter(tag = "p", name = "player", description = "The target player.", type = "player", defaultValue = "%") EntityPlayer user
    ) {
        user.addChatMessage(new ChatComponentText("Healing!"));
        user.heal(20);
        user.extinguish();
        user.getFoodStats().addStats(20, 1.0F);
        user.addChatMessage(new ChatComponentText("Healed!"));
    }
    
    @Command("locate")
    public static void locate(
            @CommandParameter(tag = "p", name = "player", description = "The target player.", type = "player", defaultValue = "%") EntityPlayer user
    ) {
        user.addChatMessage(new ChatComponentText(String.format("Location: [%1$d, %2$d, %3$d]", user.chunkCoordX, user.chunkCoordY, user.chunkCoordZ)));
    }
    
    @Command("uuid")
    public static void uuid(
            @CommandParameter(tag = "p", name = "player", description = "The target player.", type = "player", defaultValue = "%") EntityPlayer user
    ) {
        user.addChatMessage(new ChatComponentText("UUID: " + user.getPersistentID().toString()));
    }
    
    @Command("rename")
    public static void renameItem(
            @CommandParameter(tag = "i", name = "item", description = "The item.", type = "useritem", defaultValue = "%") ItemStack item,
            @CommandParameter(tag = "n", name = "name", description = "The new name.", type = "string") String name
    ) {
        item.setStackDisplayName(name);
    }

}
