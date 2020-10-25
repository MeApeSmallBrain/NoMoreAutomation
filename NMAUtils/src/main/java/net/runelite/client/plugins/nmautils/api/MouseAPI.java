package net.runelite.client.plugins.nmautils.api;

import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.nmautils.Configuration;
import net.runelite.client.plugins.nmautils.Overlay;
import net.runelite.client.plugins.nmautils.Utils;
import net.runelite.client.ui.overlay.OverlayManager;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.awt.event.MouseEvent;

public class MouseAPI
{

    @Inject private Client client;
    @Inject private ClientThread clientThread;
    @Inject private OverlayManager overlayManager;
    @Inject private ConfigManager configManager;
    @Inject private Utils utils;
    @Inject private Configuration config;
    @Inject private Overlay overlay;
    @Inject private DebugAPI debug;
    @Inject private InventoryAPI inventory;
    @Inject private MathAPI math;
    @Inject private MenuAPI menu;
    @Inject private NPCAPI npc;
    @Inject private PlayerAPI player;
    @Inject private PointAPI point;
    @Inject private RenderAPI render;
    @Inject private SleepAPI sleep;
    @Inject private StringAPI string;
    @Inject private TimeAPI time;

    public void clickInstant(Point p)
    {
        if (client.isClientThread())
        {
            utils.executor.submit(() ->
            {
                try
                {
                    utils.setClickPoint(p);
                    debug.log("Click at: " + p);
                    sendMouseEvent(501, p);
                    sleep.forXMillis(math.getRandomInt(60, 110));
                    sendMouseEvent(502, p);
                    sendMouseEvent(500, p);
                }
                catch (RuntimeException e)
                {
                    e.printStackTrace();
                }
            });
            return;
        }
        utils.setClickPoint(p);
        debug.log("Click at: " + p);
        sendMouseEvent(501, p);
        sleep.forXMillis(math.getRandomInt(60, 110));
        sendMouseEvent(502, p);
        sendMouseEvent(500, p);
    }

    public void clickWithDelay(Point p, int delay)
    {
        utils.executor.submit(() ->
        {
            try
            {
                utils.iterating = true;
                sleep.forXMillis(delay);
                utils.setClickPoint(p);
                debug.log("Mouse click at: " + p);
                sendMouseEvent(501, p);
                sleep.forXMillis(math.getRandomInt(60, 110));
                sendMouseEvent(502, p);
                sendMouseEvent(500, p);
                utils.iterating = false;
            }
            catch (RuntimeException e)
            {
                utils.iterating = false;
                e.printStackTrace();
            }
        });
    }

    private void sendMouseEvent(int id, @NotNull Point point)
    {
        MouseEvent e = new MouseEvent(
                client.getCanvas(), id,
                System.currentTimeMillis(),
                0, point.getX(), point.getY(),
                1, false, 1
        );
        client.getCanvas().dispatchEvent(e);
    }

}
