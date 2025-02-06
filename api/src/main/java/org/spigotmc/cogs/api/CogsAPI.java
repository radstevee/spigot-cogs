package org.spigotmc.cogs.api;

import org.javacord.api.listener.message.MessageCreateListener;

/**
 * The Cogs API.
 */
public interface CogsAPI {
    /**
     * Adds a message creation listener and returns it.
     * @param listener The listener.
     * @return The added listener.
     */
    MessageCreateListener addMessageCreateListener(MessageCreateListener listener);
}
