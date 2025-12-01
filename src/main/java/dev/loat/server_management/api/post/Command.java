package dev.loat.server_management.api.post;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.loat.server_management.logging.Logger;
import io.javalin.http.Context;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public final class Command {
    public static void event(@NotNull Context event, MinecraftServer minecraftServerInstance) {
        String body = event.body();
        if (body.trim().isEmpty()) {
            event.status(400).result("Missing command in body");
            return;
        }

        var message = new JSONObject(body);

        Logger.debug("Executing command: {}", message.toString());

        if (minecraftServerInstance != null) {
            try {
                minecraftServerInstance.getCommandManager().getDispatcher().execute(
                    message.getString("command"),
                    minecraftServerInstance.getCommandSource()
                );
            } catch (CommandSyntaxException ignore) {}
        }

        event.status(200).result("Done!");
    }
}
