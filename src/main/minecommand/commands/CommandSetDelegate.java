/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecommand.commands;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import minecommand.MineCommandMod;
import modcmd.commands.CommandManager;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

/**
 *
 * @author Ryan
 */
public class CommandSetDelegate implements ICommand {

    public final String identifier;

    public CommandSetDelegate(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public boolean canCommandSenderUse(ICommandSender ics) {
        // Hackish...
        // "Probably true."
        return true;
    }

    @Override
    public List getAliases() {
        return new ArrayList(); //CommandManager.getCommandSet(identifier).getSubnodes().keySet());
    }

    @Override
    public String getName() {
        return this.identifier;
    }

    @Override
    public String getCommandUsage(ICommandSender ics) {
        return '/' + CommandManager.getCommandSet(identifier).getUsage();
    }

    @Override
    public boolean isUsernameIndex(String[] strings, int i) {
        // Uh... NOP.
        return false;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender ics, String[] strings, BlockPos pos) {
        ArrayDeque<String> args = new ArrayDeque<>(Arrays.asList(strings));
        return CommandManager.getCommandSet(identifier).getNearest(args).suggestCompletion(args);
    }

    @Override
    public void execute(ICommandSender ics, String[] args) {
        Deque<String> results = CommandManager.execute(ics, identifier, args, !MineCommandMod.IN_DEV);
        for (String line : results) {
            ics.addChatMessage(new ChatComponentText(line));
        }
    }

    @Override
    public int compareTo(Object o) {
        //Me is better.
        return o.equals(this) ? 0 : -1;
    }

}
