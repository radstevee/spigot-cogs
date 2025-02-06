package org.spigotmc.cogs.api;

import org.javacord.api.listener.message.MessageCreateListener;
import org.jetbrains.annotations.ApiStatus;

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
}
