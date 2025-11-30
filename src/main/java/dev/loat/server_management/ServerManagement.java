package dev.loat.server_management;

import dev.loat.server_management.command.CommandManager;
import dev.loat.server_management.config.Config;
import dev.loat.server_management.config.files.ServerManagementConfigFile;
import dev.loat.server_management.console.LogAppender;
import dev.loat.server_management.logging.Logger;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;


public class ServerManagement implements ModInitializer {
    private static ServerManagementConfigFile config;

    /**
     * Appends the Server Management Logger to the current server instance.
     */
    private static void appendLogger() {
        var context = (LoggerContext) LogManager.getContext(false);
        var logger = context.getConfiguration().getLoggers().get("");
        var logAppender = new LogAppender();
        logAppender.start();
        logger.addAppender(
            logAppender,
            Level.valueOf(ServerManagement.config.logLevel),
            null
        );
        context.updateLoggers();
    }

    @Override
    public void onInitialize() {
        Logger.setLoggerClass(ServerManagement.class);

        ServerManagement.config = Config.load(
            Config.resolve("config.yaml"),
            ServerManagementConfigFile.class
        );
        ServerManagement.appendLogger();


        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            CommandManager.register();

            Logger.info("Server Management started!");
        });

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            Logger.info("Server Management stopped!");
        });
    }
}
