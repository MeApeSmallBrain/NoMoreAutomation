package plugin.nomore.notkeys;

import net.runelite.api.Client;
import net.runelite.api.KeyCode;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.input.KeyListener;
import plugin.nomore.nmautils.api.*;

import javax.inject.Inject;
import java.awt.event.KeyEvent;
import java.util.List;

public class NotKeysListener implements KeyListener
{

    @Inject
    private Client client;

    @Inject
    private ClientThread thread;

    @Inject
    private DebugAPI debug;

    @Inject
    private MenuAPI menu;

    @Inject
    private InventoryAPI inventory;

    @Inject
    private MouseAPI mouse;

    @Inject
    private PointAPI point;

    int iteration = 0;
    List<WidgetItem> oakLogs;

    @Override
    public void keyTyped(KeyEvent e)
    {
        e.consume();
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        debug.log(String.valueOf(e.getKeyCode()));
        debug.log(String.valueOf(iteration));
        if (e.getKeyCode() == 49)
        {
            thread.invoke(() ->
            {
                if (iteration == 0)
                {
                    oakLogs = inventory.getItems("Oak logs");
                }
                if (oakLogs == null || oakLogs.get(iteration) == null)
                {
                    iteration = 0;
                    return;
                }


                menu.setMenuEntry(menu.dropItem(oakLogs.get(iteration)));
                mouse.clickInstant(point.getRandomPointFromCentre(oakLogs.get(iteration).getCanvasBounds(), 40));

                iteration++;
            });
        }
        e.consume();
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        e.consume();
    }
}
