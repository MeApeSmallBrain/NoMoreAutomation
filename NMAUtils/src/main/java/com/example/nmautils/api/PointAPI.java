package com.example.nmautils.api;

import com.example.nmautils.NMAUtilsConfig;
import com.example.nmautils.NMAUtils;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.awt.*;

public class PointAPI
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
    @Inject private PlayerAPI player;
    @Inject private RenderAPI render;
    @Inject private SleepAPI sleep;
    @Inject private StringAPI string;
    @Inject private TimeAPI time;

    public int getCentreX(WidgetItem item)
    {
        return (int) item.getCanvasBounds().getCenterX();
    }

    public int getCentreY(WidgetItem item)
    {
        return (int) item.getCanvasBounds().getCenterY();
    }

    public Point getRandomPointWithinBounds(Rectangle rect)
    {
        if (rect == null)
        {
            return null;
        }
        int x = math.getRandomInt((int) rect.getX(), (int) rect.getX() + (int) rect.getWidth());
        int y = math.getRandomInt((int) rect.getY(), (int) rect.getY() + (int) rect.getHeight());
        return new Point(x, y);
    }

    public Point getRandomPointFromCentre(Rectangle rect, int pixelSize)
    {
        if (rect == null)
        {
            return null;
        }
        int x1 = (int) rect.getCenterX() - pixelSize;
        int y1 = (int) rect.getCenterY() - pixelSize;
        int x2 = (int) rect.getCenterX() + pixelSize;
        int y2 = (int) rect.getCenterY() + pixelSize;

        return new Point(math.getRandomInt(x1, x2), math.getRandomInt(y1, y2));
    }

    public Point getClickPoint(Rectangle rect)
    {
        final int x = (int) (rect.getX() + math.getRandomInt((int) rect.getWidth() / 6 * -1, (int) rect.getWidth() / 6) + rect.getWidth() / 2);
        final int y = (int) (rect.getY() + math.getRandomInt((int) rect.getHeight() / 6 * -1, (int) rect.getHeight() / 6) + rect.getHeight() / 2);

        return new Point(x, y);
    }

}
