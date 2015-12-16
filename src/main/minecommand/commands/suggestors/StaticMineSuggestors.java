/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecommand.commands.suggestors;

import java.util.Iterator;
import java.util.List;
import modcmd.suggestors.Suggestor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.registry.GameData;

/**
 *
 * @author RlonRyan
 */
public class StaticMineSuggestors {

    @Suggestor("player")
    public static void suggestPlayer(String tag, String value, List<String> options) {
        for (EntityPlayerMP p : (List<EntityPlayerMP>) MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
            if (p.getName().toLowerCase().startsWith(value.toLowerCase())) {
                options.add(p.getName());
            }
        }
    }

    @Suggestor("gamemode")
    public static void suggestGameType(String tag, String value, List<String> options) {
        switch (value.charAt(0)) {
            case 'S':
            case 's':
            case '0':
                options.add("Survival");
                return;
            case 'C':
            case 'c':
            case '1':
                options.add("Creative");
                return;
            case 'A':
            case 'a':
            case '2':
                options.add("Adventure");
                return;
            default:
                options.add("Survival");
                options.add("Creative");
                options.add("Adventure");
        }
    }

    @Suggestor("item")
    public static void suggestItem(String tag, String value, List<String> options) {
        Iterator<Item> iter = GameData.getItemRegistry().iterator();
        while (iter.hasNext()) {
            String item = iter.next().getUnlocalizedName();
            if (item.startsWith(value)) {
                options.add(item);
            }
        }
    }

}
