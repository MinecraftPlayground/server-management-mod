package dev.loat.server_management.command;

import dev.loat.server_management.command.sub_command.Help;
import dev.loat.server_management.command.sub_command.Reload;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import static net.minecraft.server.command.CommandManager.literal;


public final class CommandManager {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((
            dispatcher,
            registryAccess,
            environment
        ) -> dispatcher.register(
            literal("server-management").executes(Help::execute)
                .then(literal("help").executes(Help::execute))
                .then(literal("reload").executes(Reload::execute))
        ));
    }
}
