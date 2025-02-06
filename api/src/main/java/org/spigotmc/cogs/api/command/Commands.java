package org.spigotmc.cogs.api.command;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;

public final class Commands {
    private Commands() {}

    /**
     * Gets a required user argument from a slash command interaction.
     *
     * @param interaction The interaction.
     * @param argumentName The argument name.
     * @return The user.
     */
    @NonNull public static User userArg(SlashCommandInteraction interaction, String argumentName) {
        return interaction.getArgumentUserValueByName(argumentName).get();
    }
}
