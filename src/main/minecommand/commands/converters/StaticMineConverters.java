/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecommand.commands.converters;

import modcmd.converters.Converter;
import modcmd.converters.exceptions.ConversionException;
import modcmd.converters.exceptions.ConverterException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldSettings;

/**
 *
 * @author Ryan
 */
public class StaticMineConverters {

    @Converter("player")
    public static EntityPlayer getPlayer(Object user, String tag, String value) throws ConverterException {
        if (value.charAt(0) == '%') {
            if (user instanceof EntityPlayer) {
                return (EntityPlayer) user;
            } else {
                throw new ConversionException(tag, value, "Entity Player");
            }
        } else {
            throw new ConverterException("Bad user. Alternates not yet supported!");
        }
    }

    @Converter("usrpos")
    public static int getUserCoordinate(Object user, String tag, String value) throws ConverterException {
        if (value.toLowerCase().charAt(0) != '%' && !value.trim().isEmpty()) {
            try {
                return Integer.decode(value);
            } catch (NumberFormatException e) {
                throw new ConversionException(tag, value, "Integer Coordinate");
            }
        }

        if (!(user instanceof ICommandSender)) {
            throw new ConverterException("Bad user!");
        }

        ICommandSender sender = (ICommandSender) user;

        switch (tag.toLowerCase().charAt(0)) {
            case 'x':
                return (sender).getPlayerCoordinates().posX;
            case 'y':
                return (sender).getPlayerCoordinates().posY;
            case 'z':
                return (sender).getPlayerCoordinates().posZ;
        }

        throw new ConversionException(tag, value, "User Coordinate");
    }

    @Converter("gamemode")
    public static WorldSettings.GameType getGameType(Object user, String tag, String value) throws ConverterException {

        if (!(user instanceof EntityPlayer)) {
            throw new ConverterException("Bad user!");
        }

        EntityPlayer player = (EntityPlayer) user;

        switch (value.charAt(0)) {
            case '%':
                return player.capabilities.isCreativeMode ? WorldSettings.GameType.SURVIVAL : WorldSettings.GameType.CREATIVE;
            case 'S':
            case 's':
            case '0':
                return WorldSettings.GameType.SURVIVAL;
            case 'C':
            case 'c':
            case '1':
                return WorldSettings.GameType.CREATIVE;
            case 'A':
            case 'a':
            case '2':
                return WorldSettings.GameType.ADVENTURE;
        }

        throw new ConversionException(tag, value, "Game Mode");
    }
    
    @Converter("bool")
    public static boolean getBool(Object user, String tag, String value) throws ConverterException {

        switch (value.charAt(0)) {
            case 'F':
            case 'f':
            case '0':
                return false;
            case 'T':
            case 't':
            case '1':
                return true;
        }

        throw new ConversionException(tag, value, "Boolean");
    }

}
