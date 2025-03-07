package org.spigotmc.cogs.empty;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spigotmc.cogs.api.module.CogModule;
import org.spigotmc.cogs.api.module.ModuleMeta;

@ModuleMeta(id = "empty")
public final class EmptyModule implements CogModule {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void enable() {
        LOGGER.info("Hello, world!");
    }

    @Override
    public void disable() {
        LOGGER.error("Bye, cruel world :(");
    }
}
