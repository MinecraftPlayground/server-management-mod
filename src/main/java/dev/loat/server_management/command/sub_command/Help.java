package dev.loat.server_management.command.sub_command;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public final class Help {
    public static int execute(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(
            () -> Text.literal("HELP"),
            false
        );
        return 1;
    }
}
