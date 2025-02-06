package org.spigotmc.cogs.api.module;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface CogModule {
    default void enable() {
    }

    default void disable() {
    }
}
