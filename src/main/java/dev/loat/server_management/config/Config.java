package dev.loat.server_management.config;

import dev.loat.server_management.config.parser.YamlSerializer;
import dev.loat.server_management.logging.Logger;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;


public final class Config {
    private static final String rootDirectory = "server_management";

    public static <ConfigFile> ConfigFile load(
        @NotNull Path path,
        Class<ConfigFile> configFileClass
    ) {
        var config = new YamlSerializer<>(
            path.toString(),
            configFileClass
        );

        try {
            File file = path.toFile();

            // Check if the config file exists, if not create it
            if(!file.exists()) {
                config.serialize(configFileClass.getDeclaredConstructor().newInstance());
            }

            return config.parse();
        } catch (Exception exception) {
            Logger.error("Error while loading the config file: {}", exception);
            try {
                return configFileClass.getDeclaredConstructor().newInstance();
            } catch (
                InstantiationException |
                IllegalAccessException |
                InvocationTargetException |
                NoSuchMethodException e
            ) {
                throw new RuntimeException(e);
            }
        }
    }

    public static @NotNull Path resolve(String configFile) {
        return resolve(Path.of(configFile));
    }
    public static @NotNull Path resolve(Path configFile) {
        Path dir = FabricLoader.getInstance()
            .getConfigDir()
            .resolve(Config.rootDirectory);

        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            Logger.error("Could not create config directory: {}", e);
        }

        return dir.resolve(configFile);
    }
}
