package net.runelite.client.plugins.nmautils.api;

import net.runelite.api.Client;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.nmautils.Configuration;
import net.runelite.client.plugins.nmautils.Overlay;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

public class SleepAPI
{

    @Inject private Client client;
    @Inject private ClientThread clientThread;
    @Inject private OverlayManager overlayManager;
    @Inject private ConfigManager configManager;
    @Inject private Configuration config;
    @Inject private Overlay overlay;
    @Inject private DebugAPI debug;
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

    public void forXTicks(int delay)
    {
        try
        {
            Thread.sleep((int) (delay * 0.6));
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void forXMillis(int delay)
    {
        try
        {
            debug.log("Sleeping for: " + delay + " milliseconds.");
            Thread.sleep(delay);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}

