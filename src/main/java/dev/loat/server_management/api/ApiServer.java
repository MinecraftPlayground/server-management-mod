package dev.loat.server_management.api;

import dev.loat.server_management.api.post.Command;
import dev.loat.server_management.api.ws.console.Log;
import dev.loat.server_management.console.LogMessage;
import io.javalin.Javalin;
import io.javalin.plugin.bundled.CorsPluginConfig;
import io.javalin.websocket.WsConnectContext;
import net.minecraft.server.MinecraftServer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ApiServer {
    private final Set<WsConnectContext> clients = Collections.synchronizedSet(new HashSet<>());
    private final Javalin api;

    public ApiServer(int port, MinecraftServer minecraftServerInstance) {
        this.api = Javalin.create(config -> {
            config.showJavalinBanner = false;
            config.startupWatcherEnabled = false;
            config.jetty.defaultPort = port;
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(CorsPluginConfig.CorsRule::anyHost);
            });
        });

        this.api.ws("/console/log", ws -> Log.event(ws, this.clients));

        this.api.post("/console/send", event -> Command.event(event, minecraftServerInstance));
    }

    public void start() {
        this.api.start();
    }

    public void stop() {
        this.api.stop();
    }

    public void broadcast(LogMessage message) {
        synchronized (this.clients) {
            for (WsConnectContext client : this.clients) {
                var logLevels = client.queryParams("level");
                var logLevel = message.logLevel.toString().toLowerCase();

                if (logLevels.isEmpty()) {
                    client.send(message.toJson().toString());
                } else if (logLevels.contains(logLevel)) {
                    client.send(message.toJson().toString());
                }
            }
        }
    }
}
