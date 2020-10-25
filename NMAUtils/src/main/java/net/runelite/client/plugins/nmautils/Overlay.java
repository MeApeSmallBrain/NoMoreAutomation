package net.runelite.client.plugins.nmautils;

import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.client.plugins.nmautils.api.MouseAPI;
import net.runelite.client.ui.overlay.*;

import javax.inject.Inject;
import java.awt.*;

public class Overlay extends net.runelite.client.ui.overlay.Overlay {

    @Inject
    private Client client;

    @Inject
    private MouseAPI MouseAPI;

    @Inject
    private Utils utils;

    @Inject
    public Overlay() {
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.LOW);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        Point point = utils.getClickPoint();
        if (point == null)
        {
            return null;
        }
        Canvas c = client.getCanvas();
        if (c == null)
        {
            return null;
        }
        graphics.drawLine(
                c.getX(),
                point.getY(),
                c.getX() + c.getWidth(),
                point.getY());
        graphics.drawLine(
                point.getX(),
                c.getY(),
                point.getX(),
                c.getY() + c.getHeight());
        return null;
    }
}
