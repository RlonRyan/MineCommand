package minecommand;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import minecommand.commands.CommandSetDelegate;
import minecommand.commands.DefaultCommands;
import minecommand.commands.DefaultTeleportCommands;
import minecommand.commands.converters.StaticMineConverters;
import modcmd.commands.CommandManager;
import modcmd.converters.ConverterManager;
import modcmd.documents.CommandPageGenerator;
import net.minecraft.command.CommandHandler;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.Level;

@Mod(modid = MineCommandMod.MODID, version = MineCommandMod.VERSION)
public class MineCommandMod {

    public static final String MODID = "${mod_id}";
    public static final String VERSION = "${version_mod}";
    public static final boolean IN_DEV = true;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ConverterManager.addConverters(StaticMineConverters.class);
        CommandManager.getCommandSet(".").registerCommands(DefaultCommands.class);
        CommandManager.getCommandSet(".").registerCommands(DefaultTeleportCommands.class);
        CommandManager.getCommandSet(".").registerCommands(CommandPageGenerator.class);
        System.out.println("Initialization Complete");
    }

    @EventHandler
    public void serverStart(FMLServerStartedEvent event) {
        for (String e : CommandManager.getCommandSetNames()) {
            FMLLog.log(Level.INFO, "CMDSET: %1$s", e);
            ((CommandHandler) MinecraftServer.getServer().getCommandManager()).registerCommand(new CommandSetDelegate(e));
        }
    }
}
