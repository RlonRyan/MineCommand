package minecommand;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import minecommand.commands.CommandSetDelegate;
import minecommand.commands.DefaultCommands;
import modcmd.commands.CommandManager;
import net.minecraft.command.CommandHandler;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.Level;

@Mod(modid = ExampleMod.MODID, version = ExampleMod.VERSION)
public class ExampleMod {

    public static final String MODID = "MineCommand";
    public static final String VERSION = "1.0";

    @EventHandler
    public void init(FMLInitializationEvent event) {
        CommandManager.getCommandSet("modcmd").registerCommand(DefaultCommands.class);
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
