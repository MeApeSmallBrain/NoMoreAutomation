package plugin.nomore.nmautils.api;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.queries.NPCQuery;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NPCAPI
{

    @Inject
    private Client client;

    @Inject
    private StringAPI string;

    public NPC getClosestNPC(String npcName)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new NPCQuery()
                .nameEquals(npcName)
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

    public NPC getClosestNPC(int npcId)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new NPCQuery()
                .idEquals(npcId)
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

    public List<NPC> getNPCS()
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new NPCQuery()
                .result(client).list;
    }

    public List<NPC> getNPCSSortedByDistance()
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new NPCQuery()
                .result(client)
                .stream()
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public List<NPC> getNPCSMatching(String... npcName)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new NPCQuery()
                .result(client)
                .stream()
                .filter(i -> i != null
                        && Arrays.stream(npcName)
                        .anyMatch(s -> string.removeWhiteSpaces(s)
                                .equalsIgnoreCase(string.removeWhiteSpaces(i.getName()))))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public List<NPC> getNPCSMatching(int... npcId)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new NPCQuery()
                .result(client)
                .stream()
                .filter(i -> i != null
                        && Arrays.stream(npcId)
                        .anyMatch(s -> s == i.getId()))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }



    public List<NPC> getNPCSNotMatching(String... npcName)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new NPCQuery()
                .result(client)
                .stream()
                .filter(i -> i != null
                        && Arrays.stream(npcName)
                        .noneMatch(s -> string.removeWhiteSpaces(s)
                                .equalsIgnoreCase(string.removeWhiteSpaces(i.getName()))))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public List<NPC> getNPCSNotMatching(int... npcId)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new NPCQuery()
                .result(client)
                .stream()
                .filter(i -> i != null
                        && Arrays.stream(npcId)
                        .noneMatch(s -> s == i.getId()))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public NPC getClosestNPCWithinDistanceTo(String npcName, WorldPoint comparisonTile, int maxTileDistance)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new NPCQuery()
                .isWithinDistance(comparisonTile, maxTileDistance)
                .result(client)
                .stream()
                .filter(i -> i != null && string.removeWhiteSpaces(i.getName())
                        .equalsIgnoreCase(string.removeWhiteSpaces(npcName)))
                .findFirst()
                .orElse(null);
    }

    public NPC getClosestNPCWithinDistanceTo(int npcId, WorldPoint comparisonTile, int maxTileDistance)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new NPCQuery()
                .isWithinDistance(comparisonTile, maxTileDistance)
                .result(client)
                .stream()
                .filter(i -> i != null && i.getId() == npcId)
                .findFirst()
                .orElse(null);
    }


    public List<NPC> getNPCSWithinDistanceTo(String[] npcName, WorldPoint comparisonTile, int maxTileDistance)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new NPCQuery()
                .isWithinDistance(comparisonTile, maxTileDistance)
                .result(client)
                .stream()
                .filter(i -> i != null
                        && Arrays.stream(npcName)
                        .anyMatch(s -> string.removeWhiteSpaces(s)
                                .equalsIgnoreCase(string.removeWhiteSpaces(i.getName()))))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public List<NPC> getNPCSWithinDistanceTo(Integer[] npcId, WorldPoint comparisonTile, int maxTileDistance)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new NPCQuery()
                .isWithinDistance(comparisonTile, maxTileDistance)
                .result(client)
                .stream()
                .filter(i -> i != null
                        && Arrays.stream(npcId)
                        .anyMatch(s -> s == i.getId()))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

}
