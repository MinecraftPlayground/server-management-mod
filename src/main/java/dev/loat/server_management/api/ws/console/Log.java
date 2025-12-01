package dev.loat.server_management.api.ws.console;

import dev.loat.server_management.logging.Logger;
import io.javalin.websocket.WsConfig;
import io.javalin.websocket.WsConnectContext;

import java.util.Set;

public final class Log {
    public static void event(
        WsConfig ws,
        Set<WsConnectContext> clients
    ) {

        ws.onConnect(openContext -> {
            clients.add(openContext);

            openContext.enableAutomaticPings();

            Logger.info("Client connected: \"{}\" with channels: {}", openContext.host(), openContext.queryParams("level").toString());
        });

        ws.onClose(closeContext -> {
            clients.removeIf((client) -> client.session == closeContext.session);

            Logger.info(
                "Client disconnected: \"{}\" with code: {}",
                closeContext.host(),
                closeContext.status()
            );
        });

        ws.onError(errorContext -> {
            if (errorContext.error() != null) {
                Logger.error(errorContext.error().getMessage());
            }
            clients.removeIf(client -> client.session == errorContext.session);
        });
    }
}
