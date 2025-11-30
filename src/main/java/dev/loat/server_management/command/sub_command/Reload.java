package dev.loat.server_management.command.sub_command;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public final class Reload {
    public static int execute(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(
            () -> Text.literal("RELOAD"),
            false
        );
        return 1;
    }
}
