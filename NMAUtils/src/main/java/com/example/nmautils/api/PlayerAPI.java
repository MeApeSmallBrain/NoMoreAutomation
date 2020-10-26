package com.example.nmautils.api;

import com.example.nmautils.NMAUtilsConfig;
import com.example.nmautils.NMAUtils;
import net.runelite.api.Client;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

public class PlayerAPI
{

    @Inject private Client client;
    @Inject private ClientThread clientThread;
    @Inject private OverlayManager overlayManager;
    @Inject private ConfigManager configManager;
    @Inject private NMAUtils NMAUtils;
    @Inject private NMAUtilsConfig config;
    @Inject private Overlay overlay;
    @Inject private DebugAPI debug;
    @Inject private InventoryAPI inventory;
    @Inject private MathAPI math;
    @Inject private MenuAPI menu;
    @Inject private MouseAPI mouse;
    @Inject private NPCAPI npc;
    @Inject private PointAPI point;
    @Inject private RenderAPI render;
    @Inject private SleepAPI sleep;
    @Inject private StringAPI string;
    @Inject private TimeAPI time;

    public boolean isIdle()
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return false;
        }

        return client.getLocalPlayer().getAnimation() == -1;
    }
}
