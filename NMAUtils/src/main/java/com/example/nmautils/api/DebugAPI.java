package com.example.nmautils.api;

import com.example.nmautils.NMAUtilsConfig;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatColorType;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

public class DebugAPI
{
    @Inject private Client client;
    @Inject private ClientThread clientThread;
    @Inject private OverlayManager overlayManager;
    @Inject private ConfigManager configManager;
    @Inject private NMAUtilsConfig config;
    @Inject private Overlay overlay;
    @Inject private InventoryAPI inventory;
    @Inject private MathAPI math;
    @Inject private MenuAPI menu;
    @Inject private MouseAPI mouse;
    @Inject private NPCAPI npc;
    @Inject private PlayerAPI player;
    @Inject private PointAPI point;
    @Inject private RenderAPI render;
    @Inject private SleepAPI sleep;
    @Inject private StringAPI string;
    @Inject private TimeAPI time;
    @Inject private ChatMessageManager chatMessageManager;

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
