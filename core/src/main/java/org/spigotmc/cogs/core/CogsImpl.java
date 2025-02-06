package org.spigotmc.cogs.core;

import org.javacord.api.DiscordApi;
import org.javacord.api.listener.message.MessageCreateListener;
import org.spigotmc.cogs.api.CogsAPI;

public final class CogsImpl implements CogsAPI {
    private final DiscordApi api;

    public CogsImpl(DiscordApi api) {
        this.api = api;
    }

    @Override
    public MessageCreateListener addMessageCreateListener(MessageCreateListener listener) {
        api.addMessageCreateListener(listener);
        return listener;
    }
}
