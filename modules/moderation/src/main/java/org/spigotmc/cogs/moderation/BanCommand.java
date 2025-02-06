package org.spigotmc.cogs.moderation;

import static org.spigotmc.cogs.api.command.Commands.userArg;

import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import org.spigotmc.cogs.api.command.Command;

public class BanCommand implements Command {
    @Override
    public SlashCommandBuilder builder() {
        return SlashCommand.with("ban", "Ban a user.")
                .addOption(SlashCommandOption.create(SlashCommandOptionType.USER, "user", "The user to ban."));
    }

    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent event) {
        final SlashCommandInteraction interaction = event.getSlashCommandInteraction();

        final User user = userArg(interaction, "user");

        interaction
                .createImmediateResponder()
                .setContent("I'm gonna ban Ebic instead of " + user.getName() + "!")
                .respond();
    }
}
