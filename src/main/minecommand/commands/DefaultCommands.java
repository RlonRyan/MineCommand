/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecommand.commands;

import java.util.ArrayDeque;
import java.util.Arrays;
import minecommand.MineCommandMod;
import minecommand.utility.WorldPoint;
import modcmd.commands.Command;
import modcmd.commands.CommandManager;
import modcmd.commands.CommandParameter;
import modcmd.user.CommandUser;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;

/**
 *
 * @author Ryan
 */
public class DefaultCommands {

    @Command(
            name = "help",
            about = "Retrieves help for the command."
    )
    public static void help(
            @CommandUser ICommandSender user,
            @CommandParameter(tag = "s", name = "subject", description = "The subject to get help on.", type = "String", defaultValue = " ") String message
    ) {
        for (String line : CommandManager.getCommandSet(".").getHelp(new ArrayDeque<>(Arrays.asList(message.split("\\s+"))))) {
            user.addChatMessage(new ChatComponentText(line));
        }
    }

    @Command(
            name = "broadcast",
            about = "Brodcasts a message to the entire server."
    )
    public static void message(
            @CommandParameter(tag = "m", name = "message", description = "The message to broadcast.", type = "String") String message
    ) {
        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("[Broadcast] " + message));
    }

    @Command(
            name = "version",
            about = "Displays the MineCommand version."
    )
    public static String getVersion() {
        return MineCommandMod.VERSION;
    }

    @Command(
            name = "gm",
            about = "Changes the user's game mode."
    )
    public static void gameMode(
            @CommandParameter(tag = "p", name = "player", description = "The target player.", type = "player", defaultValue = "%") EntityPlayer user,
            @CommandParameter(tag = "m", name = "mode", description = "The gamemode.", type = "gamemode", defaultValue = "%") WorldSettings.GameType mode
    ) {
        user.setGameType(mode);
    }

    @Command(
            name = "fly",
            about = "Allows the user to fly. Make sure to land before turning off!"
    )
    public static void fly(
            @CommandParameter(tag = "p", name = "player", description = "The target player.", type = "player", defaultValue = "%") EntityPlayer user,
            @CommandParameter(tag = "f", name = "fly", description = "If the player can fly.", type = "bool") boolean canFly
    ) {
        user.addChatMessage(new ChatComponentText("Old flying ability: " + user.capabilities.allowFlying));
        user.capabilities.allowFlying = canFly;
        user.sendPlayerAbilities();
        user.addChatMessage(new ChatComponentText("Updated flying ability to: " + canFly));
    }

    @Command(
            name = "speed",
            about = "Changes the user's movement multiplier. Limited to range between 1 and 10."
    )
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

    @Command(
            name = "heal",
            about = "Heals the targeted player"
    )
    public static void heal(
            @CommandParameter(tag = "p", name = "player", description = "The target player.", type = "player", defaultValue = "%") EntityPlayer user
    ) {
        user.addChatMessage(new ChatComponentText("Healing!"));
        user.heal(20);
        user.extinguish();
        user.getFoodStats().addStats(20, 1.0F);
        user.addChatMessage(new ChatComponentText("Healed!"));
    }

    @Command(
            name = "locate",
            about = "Locates the targeted player."
    )
    public static void locate(
            @CommandParameter(tag = "p", name = "player", description = "The target player.", type = "player", defaultValue = "%") EntityPlayer user
    ) {
        user.addChatMessage(new ChatComponentText(String.format("Location: [%1$f, %2$f, %3$f]", user.posX, user.posY, user.posZ)));
    }

    @Command(
            name = "uuid",
            about = "Gets the UUID of the target player."
    )
    public static void uuid(
            @CommandParameter(tag = "p", name = "player", description = "The target player.", type = "player", defaultValue = "%") EntityPlayer user
    ) {
        user.addChatMessage(new ChatComponentText("UUID: " + user.getPersistentID().toString()));
    }

    @Command(
            name = "rename",
            about = "Renames the currently held item."
    )
    public static void renameItem(
            @CommandParameter(tag = "i", name = "item", description = "The item.", type = "item", defaultValue = "%") ItemStack item,
            @CommandParameter(tag = "n", name = "name", description = "The new name.", type = "string") String name
    ) {
        item.setStackDisplayName(name);
    }

    @Command(
            name = "smite",
            about = "Smites a location"
    )
    public static void smite(
            @CommandParameter(tag = "p", name = "point", description = "The point to smite", type = "point", defaultValue = "%") WorldPoint point
    ) {
        System.out.println(point.toString());
        WorldServer world = MinecraftServer.getServer().worldServerForDimension(point.dim);
        world.addWeatherEffect(new EntityLightningBolt(world, point.x, point.y, point.z));
    }

}
