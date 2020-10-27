package net.runelite.client.plugins.nmautils.api;

import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatColorType;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.plugins.nmautils.NMAUtilsConfig;

import javax.inject.Inject;

public class DebugAPI
{

    @Inject
    private Client client;

    @Inject
    private ClientThread clientThread;

    @Inject
    private NMAUtilsConfig config;

    @Inject
    private ChatMessageManager chatMessageManager;

    // API Injects

    @Inject
    private TimeAPI time;

    public void log(String string)
    {

        if (config.logConsoleMessage())
        {
            System.out.println("[" + time.getCurrentTime() + "]: " + string);
        }

        if (config.logGameMessage())
        {
            clientThread.invokeLater(() ->
            {
                String chatMessage = new ChatMessageBuilder()
                        .append(ChatColorType.HIGHLIGHT)
                        .append("[" + time.getCurrentTime() + "]: " + string)
                        .build();

                chatMessageManager
                        .queue(QueuedMessage.builder()
                                .type(ChatMessageType.CONSOLE)
                                .runeLiteFormattedMessage(chatMessage)
                                .build());
            });
        }

    }

}
