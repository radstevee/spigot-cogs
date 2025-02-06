package org.spigotmc.cogs.core.command;

import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import org.spigotmc.cogs.api.Cogs;

public final class CommandListener implements SlashCommandCreateListener {
    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent event) {
        Cogs.handleCommand(event);
    }
}
