package org.spigotmc.cogs.api.config;

import com.google.gson.Gson;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class CogsConfigLoader {
    private CogsConfigLoader() {}

    private static @Nullable CogsConfig config;
    private static final @NonNull Path CONFIG_FILE = Path.of("config.json");
    private static final @NonNull Gson GSON = new Gson();

    public static @NonNull CogsConfig config() {
        if (CogsConfigLoader.config == null) {
            try {
                final String configFileContents = Files.readString(CONFIG_FILE);
                CogsConfigLoader.config = GSON.fromJson(configFileContents, CogsConfig.class);
            } catch (IOException exception) {
                throw new RuntimeException("Failed reading configuration: " + exception.getMessage());
            }
        }

        return config;
    }
}
