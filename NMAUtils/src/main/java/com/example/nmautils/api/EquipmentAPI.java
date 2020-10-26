package com.example.nmautils.api;

import com.example.nmautils.NMAUtilsConfig;
import net.runelite.api.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

public class EquipmentAPI
{
    @Inject private Client client;
    @Inject private ClientThread clientThread;
    @Inject private OverlayManager overlayManager;
    @Inject private ConfigManager configManager;
    @Inject private NMAUtilsConfig config;
    @Inject private Overlay overlay;
    @Inject private InventoryAPI inventory;
    @Inject private MathAPI math;
    @Inject private MenuAPI menu;
    @Inject private MouseAPI mouse;
    @Inject private NPCAPI npc;
    @Inject private PlayerAPI player;
    @Inject private PointAPI point;
    @Inject private RenderAPI render;
    @Inject private SleepAPI sleep;
    @Inject private StringAPI string;
    @Inject private TimeAPI time;

    public boolean isWeilding(int itemId)
    {
        assert client.isClientThread();

        ItemContainer equipmentContainer = client.getItemContainer(InventoryID.EQUIPMENT);
        if (equipmentContainer == null)
        {
            return false;
        }

        return equipmentContainer.getItem(EquipmentInventorySlot.WEAPON.getSlotIdx()).getId() == itemId;
    }

    public boolean isWeilding(String itemName)
    {
        assert client.isClientThread();

        ItemContainer equipmentContainer = client.getItemContainer(InventoryID.EQUIPMENT);
        if (equipmentContainer == null)
        {
            return false;
        }

        return string.removeWhiteSpaces(client.getItemDefinition(equipmentContainer
                .getItem(EquipmentInventorySlot.WEAPON.getSlotIdx())
                .getId())
                .getName())
                .contains(string.removeWhiteSpaces(itemName));
    }
}
