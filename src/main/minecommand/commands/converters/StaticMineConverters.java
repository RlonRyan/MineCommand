/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecommand.commands.converters;

import java.util.List;
import javax.vecmath.Point3d;
import modcmd.converters.Converter;
import modcmd.converters.exceptions.ConversionException;
import modcmd.converters.exceptions.ConverterException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldSettings;

/**
 *
 * @author RlonRyan
 */
public class StaticMineConverters {

    @Converter("userPlayer")
    public static EntityPlayer getUserPlayer(Object user, String tag, String value) throws ConverterException {
        if (user instanceof EntityPlayer) {
            return (EntityPlayer) user;
        } else {
            throw new ConversionException(tag, value, "Entity Player");
        }
    }

    @Converter("player")
    public static EntityPlayerMP getPlayer(Object user, String tag, String value) throws ConverterException {
        if (value.charAt(0) == '%') {
            if (user instanceof EntityPlayerMP) {
                return (EntityPlayerMP) user;
            } else {
                throw new ConversionException(tag, value, "Entity Player");
            }
        } else {
            for (EntityPlayerMP p : (List<EntityPlayerMP>) MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
                if (p.getCommandSenderName().equalsIgnoreCase(value)) {
                    return p;
                }
            }
            throw new ConverterException("User: " + value + " not found");
        }
    }

    @Converter("userpos")
    public static int getUserCoordinate(Object user, String tag, String value) throws ConverterException {
        if (value.toLowerCase().charAt(0) != '%' && !value.trim().isEmpty()) {
            try {
                return Integer.decode(value);
            } catch (NumberFormatException e) {
                throw new ConversionException(tag, value, "Integer Coordinate");
            }
        }

        if (!(user instanceof EntityPlayer)) {
            throw new ConverterException("Bad user!");
        }

        EntityPlayer sender = (EntityPlayer) user;

        int delta = 0;

        if (value.length() > 1) {
            try {
                delta = Integer.decode(value.substring(1));
            } catch (NumberFormatException e) {
                throw new ConversionException(tag, value, "Integer Coordinate");
            }
        }

        switch (tag.toLowerCase().charAt(0)) {
            case 'x':
                return (int) sender.getPlayerCoordinates().posX + delta;
            case 'y':
                return (int) sender.getPlayerCoordinates().posY + delta;
            case 'z':
                return (int) sender.posZ + delta;
            case 'd':
                return sender.dimension + delta;
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

    @Converter("useritem")
    public static ItemStack getUserItem(Object user, String tag, String value) throws ConverterException {
        if (!(user instanceof EntityPlayer)) {
            throw new ConverterException("Bad user!");
        }

        EntityPlayer player = (EntityPlayer) user;

        ItemStack is = player.inventory.getCurrentItem();

        if (is == null) {
            throw new ConverterException("You are not holding a valid item.");
        }

        return is;
    }

    @Converter("userpoint")
    public static Point3d getPoint(Object user, String tag, String value) throws ConverterException {
        if (!(user instanceof EntityPlayer)) {
            throw new ConverterException("Bad user!");
        }

        if (value.charAt(0) == '%') {
            EntityPlayer player = (EntityPlayer) user;

            Vec3 lookAt = player.getLook(1);
            Vec3 playerPos = Vec3.createVectorHelper(player.posX, player.posY + (player.getEyeHeight() - player.getDefaultEyeHeight()), player.posZ);
            Vec3 pos1 = playerPos.addVector(0, player.getEyeHeight(), 0);
            Vec3 pos2 = pos1.addVector(lookAt.xCoord * 100, lookAt.yCoord * 100, lookAt.zCoord * 100);
            MovingObjectPosition pos = player.worldObj.rayTraceBlocks(pos1, pos2);

            return new Point3d(pos.blockX, pos.blockY, pos.blockZ);
        } else {
            String[] tokens = value.split("\\s+");
            if (tokens.length != 3) {
                throw new ConversionException(tag, value, "User point");
            }

            try {
                return new Point3d(Integer.decode(tokens[0]), Integer.decode(tokens[1]), Integer.decode(tokens[2]));
            } catch (NumberFormatException ne) {
                throw new ConversionException(tag, value, "User point");
            }
        }
    }

}
