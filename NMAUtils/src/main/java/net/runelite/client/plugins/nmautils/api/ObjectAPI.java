package net.runelite.client.plugins.nmautils.api;

import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.queries.GameObjectQuery;
import net.runelite.api.queries.NPCQuery;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.nmautils.Configuration;
import net.runelite.client.plugins.nmautils.Overlay;
import net.runelite.client.plugins.nmautils.Utils;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ObjectAPI
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
    @Inject private MouseAPI mouse;
    @Inject private PlayerAPI player;
    @Inject private PointAPI point;
    @Inject private RenderAPI render;
    @Inject private SleepAPI sleep;
    @Inject private StringAPI string;
    @Inject private TimeAPI time;

    public List<GameObject> getGameObjects()
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .result(client)
                .list;
    }

    public List<GameObject> getGameObjectsSortedByDistance()
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .result(client)
                .stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public GameObject getClosestGameObjectMatching(String objectName)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .filter(gameObject -> gameObject != null
                        && string.removeWhiteSpaces(client.getObjectDefinition(gameObject.getId())
                        .getName())
                        .equalsIgnoreCase(string.removeWhiteSpaces(objectName)))
                .result(client)
                .stream().min(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer().getLocalLocation())))
                .orElse(null);
    }

    public GameObject getClosestGameObjectMatching(int objectId)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .filter(gameObject -> gameObject != null
                        && gameObject.getId() == objectId)
                .result(client)
                .stream().min(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer().getLocalLocation())))
                .orElse(null);
    }

    public List<GameObject> getGameObjectsMatching(String... objectNames)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .filter(gameObject -> gameObject != null
                        && Arrays.stream(objectNames)
                        .anyMatch(objectName -> string.removeWhiteSpaces(client.getObjectDefinition(gameObject.getId())
                                .getName())
                                .equalsIgnoreCase(string.removeWhiteSpaces(objectName))))
                .result(client)
                .stream().sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public List<GameObject> getClosestGameObjectsMatching(int... objectIds)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .filter(gameObject -> gameObject != null
                        && Arrays.stream(objectIds)
                        .anyMatch(objectId -> objectId == gameObject.getId()))
                .result(client)
                .stream().sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public List<GameObject> getGameObjectsNotMatching(String... objectNames)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .filter(gameObject -> gameObject != null
                        && Arrays.stream(objectNames)
                        .noneMatch(objectName -> string.removeWhiteSpaces(client.getObjectDefinition(gameObject.getId())
                                .getName())
                                .equalsIgnoreCase(string.removeWhiteSpaces(objectName))))
                .result(client)
                .stream().sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public List<GameObject> getClosestGameObjectsNotMatching(int... objectIds)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .filter(gameObject -> gameObject != null
                        && Arrays.stream(objectIds)
                        .noneMatch(objectId -> objectId == gameObject.getId()))
                .result(client)
                .stream().sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public GameObject getClosestGameObjectWithinDistanceTo(String objectName, WorldPoint comparisonTile, int maxTileDistance)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .isWithinDistance(comparisonTile, maxTileDistance)
                .result(client)
                .stream()
                .filter(gameObject -> gameObject != null
                        && string.removeWhiteSpaces(client.getObjectDefinition(gameObject.getId()).getName())
                        .equalsIgnoreCase(string.removeWhiteSpaces(objectName)))
                .findFirst()
                .orElse(null);
    }

    public GameObject getClosestGameObjectWithinDistanceTo(int objectId, WorldPoint comparisonTile, int maxTileDistance)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .isWithinDistance(comparisonTile, maxTileDistance)
                .result(client)
                .stream()
                .filter(gameObject -> gameObject != null
                        && gameObject.getId() == objectId)
                .findFirst()
                .orElse(null);
    }

    public List<NPC> getGameObjectsWithinDistanceTo(String[] objectNames, WorldPoint comparisonTile, int maxTileDistance)
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
                .filter(gameObject -> gameObject != null
                        && Arrays.stream(objectNames)
                        .anyMatch(objectName -> string.removeWhiteSpaces(objectName)
                                .equalsIgnoreCase(string.removeWhiteSpaces(gameObject.getName()))))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public List<NPC> getGameObjectsWithinDistanceTo(Integer[] objectIds, WorldPoint comparisonTile, int maxTileDistance)
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
                .filter(gameObject -> gameObject != null
                        && Arrays.stream(objectIds)
                        .anyMatch(ObjectId -> ObjectId == gameObject.getId()))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

}
