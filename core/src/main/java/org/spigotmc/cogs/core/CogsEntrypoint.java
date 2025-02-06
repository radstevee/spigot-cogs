package org.spigotmc.cogs.core;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.intent.Intent;
import org.spigotmc.cogs.api.Cogs;
import org.spigotmc.cogs.api.CogsAPI;
import org.spigotmc.cogs.api.config.CogsConfig;
import org.spigotmc.cogs.api.config.CogsConfigLoader;
import org.spigotmc.cogs.core.command.CommandListener;
import org.spigotmc.cogs.core.module.CogsModuleLoader;

public class CogsEntrypoint {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        final CogsConfig config = CogsConfigLoader.config();
        LOGGER.info("Connecting to Discord...");
        final DiscordApi api = new DiscordApiBuilder()
                .setToken(config.token())
                .addIntents(Intent.MESSAGE_CONTENT)
                .addIntents(Intent.GUILDS)
                .login()
                .join();

        LOGGER.info("Connected!");

        final CogsAPI impl = new CogsImpl(api);
        Cogs.impl(impl);

        api.addSlashCommandCreateListener(new CommandListener());

        final CogsModuleLoader loader = new CogsModuleLoader();
        final Set<@NonNull Path> modules;
        try {
            modules = loader.collectModuleJars();
        } catch (IOException exception) {
            throw new RuntimeException("Failed reading module jars: " + exception);
        }

        modules.forEach(loader::loadModule);

        Runtime.getRuntime().addShutdownHook(new Thread(loader::unloadAllModules));
    }
}
