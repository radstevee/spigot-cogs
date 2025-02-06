package org.spigotmc.cogs.api;

import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import org.javacord.api.listener.message.MessageCreateListener;
import org.spigotmc.cogs.api.command.Command;

/** The Cogs API. */
public interface CogsAPI {
    /**
     * Adds a message creation listener and returns it.
     *
     * @param listener The listener.
     * @return The added listener.
     */
    MessageCreateListener addMessageCreateListener(MessageCreateListener listener);

    /**
     * Builds and registers a command using the associated builder.
     *
     * @param builder The command builder.
     * @return The added command.
     */
    SlashCommand registerCommand(SlashCommandBuilder builder, SlashCommandCreateListener listener);

    /**
     * Handles a command invocation.
     *
     * @param event The command event.
     * @return Whether the command was handled or not.
     */
    boolean handleCommand(SlashCommandCreateEvent event);

    /**
     * Registers a command.
     *
     * @param command The command.
     * @return The added command.
     */
    Command registerCommand(Command command);
}
