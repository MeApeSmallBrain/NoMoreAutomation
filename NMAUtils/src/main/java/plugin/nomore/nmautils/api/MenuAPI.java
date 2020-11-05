package plugin.nomore.nmautils.api;

import net.runelite.api.GameObject;
import net.runelite.api.MenuEntry;
import net.runelite.api.MenuOpcode;
import net.runelite.api.NPC;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.nmautils.NMAUtils;

import javax.inject.Inject;

public class MenuAPI
{

    @Inject
    private NMAUtils utils;

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

    public MenuEntry eatItem(WidgetItem item)
    {
        if (item == null)
        {
            return null;
        }
        return new MenuEntry("", "",
                item.getId(),
                MenuOpcode.ITEM_FIRST_OPTION.getId(),
                item.getIndex(),
                WidgetInfo.INVENTORY.getId(),
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
