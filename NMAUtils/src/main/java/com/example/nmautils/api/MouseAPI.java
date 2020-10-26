package com.example.nmautils.api;

import com.example.nmautils.NMAUtilsConfig;
import com.example.nmautils.NMAUtils;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.awt.event.MouseEvent;

public class MouseAPI
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
    @Inject private NPCAPI npc;
    @Inject private PlayerAPI player;
    @Inject private PointAPI point;
    @Inject private RenderAPI render;
    @Inject private SleepAPI sleep;
    @Inject private StringAPI string;
    @Inject private TimeAPI time;

    public MouseAPI() {
    }

    public void clickInstant(Point p)
    {
        if (client.isClientThread())
        {
            NMAUtils.executor.submit(() ->
            {
                try
                {
                    NMAUtils.setClickPoint(p);
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
        NMAUtils.setClickPoint(p);
        debug.log("Click at: " + p);
        sendMouseEvent(501, p);
        sleep.forXMillis(math.getRandomInt(60, 110));
        sendMouseEvent(502, p);
        sendMouseEvent(500, p);
    }

    public void clickWithDelay(Point p, int delay)
    {
        NMAUtils.executor.submit(() ->
        {
            try
            {
                NMAUtils.iterating = true;
                sleep.forXMillis(delay);
                NMAUtils.setClickPoint(p);
                debug.log("Mouse click at: " + p);
                sendMouseEvent(501, p);
                sleep.forXMillis(math.getRandomInt(60, 110));
                sendMouseEvent(502, p);
                sendMouseEvent(500, p);
                NMAUtils.iterating = false;
            }
            catch (RuntimeException e)
            {
                NMAUtils.iterating = false;
                e.printStackTrace();
            }
        });
    }

    private void sendMouseEvent(int id, Point point)
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
