package org.spigotmc.cogs.api.module;

public interface CogModule {
    default void enable() {}

    default void disable() {}
}
