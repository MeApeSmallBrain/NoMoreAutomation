package net.runelite.client.plugins.nmautils.api;

import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.client.plugins.nmautils.NMAUtils;

import javax.inject.Inject;
import java.awt.event.MouseEvent;

public class MouseAPI
{

    @Inject
    private Client client;

    @Inject
    private NMAUtils utils;

    @Inject
    private DebugAPI debug;

    @Inject
    private SleepAPI sleep;

    @Inject
    private MathAPI math;

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
