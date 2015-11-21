/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecommand.commands;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import modcmd.commands.CommandManager;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
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
    public boolean canCommandSenderUseCommand(ICommandSender ics) {
        // Hackish...
        return true;
    }

    @Override
    public List getCommandAliases() {
        return new ArrayList(); //CommandManager.getCommandSet(identifier).getSubnodes().keySet());
    }

    @Override
    public String getCommandName() {
        return this.identifier;
    }

    @Override
    public String getCommandUsage(ICommandSender ics) {
        return CommandManager.getCommandSet(identifier).getUsage();
    }

    @Override
    public boolean isUsernameIndex(String[] strings, int i) {
        // Uh... NOP.
        return false;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender ics, String[] strings) {
        ArrayDeque<String> args = new ArrayDeque<String>(Arrays.asList(strings));
        return CommandManager.getCommandSet(identifier).getNearest(args).suggestCompletion(args);
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args) {
        String result = CommandManager.execute(ics, identifier, args);
        if (result != null && !result.isEmpty()) {
            ics.addChatMessage(new ChatComponentText(result));
        }
    }

    @Override
    public int compareTo(Object o) {
        //Me is better.
        return 1;
    }

}
