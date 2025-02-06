package org.spigotmc.cogs.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.javacord.api.DiscordApi;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import org.javacord.api.listener.message.MessageCreateListener;
import org.jetbrains.annotations.ApiStatus;
import org.spigotmc.cogs.api.CogsAPI;
import org.spigotmc.cogs.api.command.Command;

public final class CogsImpl implements CogsAPI {
    private final DiscordApi api;
    private final Map<String, SlashCommandCreateListener> commandMap = new HashMap<>();

    public CogsImpl(DiscordApi api) {
        this.api = api;
    }

    @Override
    public MessageCreateListener addMessageCreateListener(MessageCreateListener listener) {
        this.api.addMessageCreateListener(listener);
        return listener;
    }

    @Override
    public SlashCommand registerCommand(SlashCommandBuilder builder, SlashCommandCreateListener listener) {
        final SlashCommand command = builder.createGlobal(this.api).join();

        for (final String name : command.getFullCommandNames()) {
            commandMap.putIfAbsent(name, listener);
        }

        return command;
    }

    @ApiStatus.Internal
    @Override
    public boolean handleCommand(SlashCommandCreateEvent event) {
        final String name = event.getSlashCommandInteraction().getFullCommandName();
        final Optional<SlashCommandCreateListener> listener = Optional.ofNullable(this.commandMap.get(name));
        if (listener.isEmpty()) {
            return false;
        }

        listener.get().onSlashCommandCreate(event);

        return true;
    }

    @Override
    public Command registerCommand(Command command) {
        this.registerCommand(command.builder(), command);
        return command;
    }
}
