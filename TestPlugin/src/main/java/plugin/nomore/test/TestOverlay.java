package plugin.nomore.test;

import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import plugin.nomore.nmautils.api.DebugAPI;
import plugin.nomore.nmautils.api.PlayersAPI;
import plugin.nomore.nmautils.api.RenderAPI;

import javax.inject.Inject;
import java.awt.*;

public class TestOverlay extends Overlay
{

    @Inject
    private Client client;

    @Inject
    private RenderAPI render;

    @Inject
    private PlayersAPI players;

    @Inject
    private DebugAPI debug;

    @Inject
    public TestOverlay()
    {
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.LOW);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        for (Player player : players.getPlayers())
        {
            if (player.getCombatLevel() > 10)
            {
                render.renderCentreBox(graphics, player.getConvexHull().getBounds(), Color.GREEN, 5);
            }
        }
        return null;
    }

}
