/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecommand.commands.converters;

import modcmd.converters.Converter;
import modcmd.converters.ConverterManager;
import modcmd.converters.exceptions.ConversionException;
import modcmd.converters.exceptions.ConverterException;
import net.minecraft.command.ICommandSender;

/**
 *
 * @author Ryan
 */
public class StaticMineConverters {

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

}
