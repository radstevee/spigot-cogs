package org.spigotmc.cogs.api;

import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import org.javacord.api.listener.message.MessageCreateListener;
import org.jetbrains.annotations.ApiStatus;
import org.spigotmc.cogs.api.command.Command;

public final class Cogs {
    private static CogsAPI IMPL;

    @ApiStatus.Internal
    public static CogsAPI impl(CogsAPI impl) {
        Cogs.IMPL = impl;
        return impl;
    }

    /**
     * Adds a message creation listener and returns it.
     *
     * @param listener The listener.
     * @return The added listener.
     */
    public static MessageCreateListener addMessageCreateListener(MessageCreateListener listener) {
        return IMPL.addMessageCreateListener(listener);
    }

    /**
     * Builds and registers a command using the associated builder.
     *
     * @param builder The command builder.
     * @return The added command.
     */
    public static SlashCommand registerCommand(SlashCommandBuilder builder, SlashCommandCreateListener listener) {
        return IMPL.registerCommand(builder, listener);
    }

    /**
     * Handles a command invocation.
     *
     * @param event The command event.
     * @return Whether the command was handled or not.
     */
    @ApiStatus.Internal
    public static boolean handleCommand(SlashCommandCreateEvent event) {
        return IMPL.handleCommand(event);
    }

    /**
     * Registers a command.
     *
     * @param command The command.
     * @return The added command.
     */
    public static Command registerCommand(Command command) {
        return IMPL.registerCommand(command);
    }
}
