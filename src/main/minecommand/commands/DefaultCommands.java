/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecommand.commands;

import modcmd.commands.Command;
import modcmd.commands.CommandParameter;

/**
 *
 * @author Ryan
 */
public class DefaultCommands {

    @Command("message")
    public static void print(@CommandParameter(tag = "m", name = "message", description = "The message to print.", type = "String") String message) {
        System.out.println(message);
    }

}
