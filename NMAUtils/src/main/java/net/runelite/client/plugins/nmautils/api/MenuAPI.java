package net.runelite.client.plugins.nmautils.api;

import net.runelite.api.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.nmautils.Configuration;
import net.runelite.client.plugins.nmautils.Overlay;
import net.runelite.client.plugins.nmautils.Utils;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

public class MenuAPI
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
    @Inject private MouseAPI mouse;
    @Inject private NPCAPI npc;
    @Inject private PlayerAPI player;
    @Inject private PointAPI point;
    @Inject private RenderAPI render;
    @Inject private SleepAPI sleep;
    @Inject private StringAPI string;
    @Inject private TimeAPI time;

    public MenuEntry interactWithNPC(NPC npc)
    {
        if (npc == null)
        {
            return null;
        }
        return new MenuEntry("", "",
                npc.getIndex(),
                MenuOpcode.NPC_FIRST_OPTION.getId(),
                0,
                0,
                false);
    }

    public MenuEntry selectSpell(Widget spell)
    {
        if (spell == null)
        {
            return null;
        }
        return new MenuEntry("Cast", spell.getName(),
                0,
                MenuOpcode.WIDGET_TYPE_2.getId(),
                -1,
                spell.getId(),
                false);
    }

    public MenuEntry useSpellOnItem(WidgetItem item)
    {
        if (item == null)
        {
            return null;
        }
        return new MenuEntry("","",
                item.getId(),
                MenuOpcode.ITEM_USE_ON_WIDGET.getId(),
                item.getIndex(),
                WidgetInfo.INVENTORY.getId(),
                false);
    }

    public MenuEntry tradeWithNPC(NPC npc)
    {
        if (npc == null)
        {
            return null;
        }
        return new MenuEntry("", "",
                npc.getIndex(),
                MenuOpcode.NPC_SECOND_OPTION.getId(),
                0,
                0,
                false);
    }

    public MenuEntry interactWithGameObject(GameObject gameObject)
    {
        if (gameObject == null)
        {
            return null;
        }
        return new MenuEntry("", "",
                gameObject.getId(),
                MenuOpcode.GAME_OBJECT_FIRST_OPTION.getId(),
                gameObject.getSceneMinLocation().getX(),
                gameObject.getSceneMinLocation().getY(),
                false);
    }

    public MenuEntry useItem(WidgetItem item)
    {
        if (item == null)
        {
            return null;
        }
        return new MenuEntry("","",
                item.getId(),
                MenuOpcode.ITEM_USE.getId(),
                item.getIndex(),
                WidgetInfo.INVENTORY.getId(),
                false);
    }

    public MenuEntry dropItem(WidgetItem item)
    {
        return new MenuEntry(
                "",
                "",
                item.getId(),
                MenuOpcode.ITEM_DROP.getId(),
                item.getIndex(),
                WidgetInfo.INVENTORY.getId(),
                false);
    }

    public void setMenuEntry(MenuEntry m)
    {
        utils.targetMenu = new MenuEntry(
                m.getOption(),
                m.getTarget(),
                m.getIdentifier(),
                m.getOpcode(),
                m.getParam0(),
                m.getParam1(),
                m.isForceLeftClick());
    }
}
