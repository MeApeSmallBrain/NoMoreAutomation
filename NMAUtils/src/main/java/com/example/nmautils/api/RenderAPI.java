package com.example.nmautils.api;

import net.runelite.api.NPC;

import java.awt.*;

public class RenderAPI
{

    public void npcCentreBox(Graphics2D graphics, NPC npc, Color color, int boxSize)
    {
        Shape shape = npc.getConvexHull();
        if (shape == null)
        {
            return;
        }
        int x = (int) shape.getBounds().getCenterX() - boxSize / 2;
        int y = (int) shape.getBounds().getCenterY() - boxSize / 2;
        graphics.setColor(color);
        graphics.fillRect(x, y, boxSize, boxSize);
    }

}
