package org.spigotmc.cogs.api.command;

import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

public interface Command extends SlashCommandCreateListener {
    SlashCommandBuilder builder();
}
