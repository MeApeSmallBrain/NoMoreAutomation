package plugin.nomore.nmautils.api;

import net.runelite.api.Client;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;

import javax.inject.Inject;

public class LocationAPI
{

    @Inject
    private Client client;

    @Inject
    private DebugAPI debug;

    public boolean isWithinAreaNWtoSE(WorldPoint isWorldPoint, int x1, int y2, int x2, int y1, int plane)
    {

        if (isWorldPoint == null)
        {
            return false;
        }

        return worldPointAreaCheck(isWorldPoint.getX(),
                isWorldPoint.getY(),
                isWorldPoint.getPlane(),
                x1,
                y1,
                x2,
                y2,
                plane);
    }

    public boolean isWithinAreaNEtoSW(WorldPoint isWorldPoint, int x2, int y2, int x1, int y1, int plane)
    {

        if (isWorldPoint == null)
        {
            return false;
        }

        return worldPointAreaCheck(isWorldPoint.getX(),
                isWorldPoint.getY(),
                isWorldPoint.getPlane(),
                x1,
                y1,
                x2,
                y2,
                plane);

    }

    public boolean isWithinAreaSEtoNW(WorldPoint isWorldPoint, int x2, int y1, int x1, int y2, int plane)
    {

        if (isWorldPoint == null)
        {
            return false;
        }

        return worldPointAreaCheck(isWorldPoint.getX(),
                isWorldPoint.getY(),
                isWorldPoint.getPlane(),
                x1,
                y1,
                x2,
                y2,
                plane);

    }

    public boolean isWithinAreaSWtoNE(WorldPoint isWorldPoint, int x1, int y1, int x2, int y2, int plane)
    {

        if (isWorldPoint == null)
        {
            return false;
        }

        return worldPointAreaCheck(isWorldPoint.getX(),
                isWorldPoint.getY(),
                isWorldPoint.getPlane(),
                x1,
                y1,
                x2,
                y2,
                plane);

    }

    private boolean worldPointAreaCheck(int wpX, int wpY, int wpZ, int x1, int y1, int x2, int y2, int plane)
    {

        if (wpX >= x1 && wpX <= x2
                && wpY >= y1 && wpY <= y2
                && wpZ == plane)
        {
            debug.verbose("The isWorldPoint "
                    + wpX
                    + ", "
                    + wpY
                    + ", "
                    + wpZ
                    + " is within the area: \nSW: "
                    + x1
                    + ", "
                    + y1
                    + ". \nNE:  "
                    + x2
                    + ", "
                    + y2
                    + ", "
                    + plane
                    + ".");

            debug.log("The WorldPoint is within the WorldArea.");

            return true;
        }

        return false;

    }

}
