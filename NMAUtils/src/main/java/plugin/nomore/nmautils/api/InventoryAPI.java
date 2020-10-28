package plugin.nomore.nmautils.api;

import net.runelite.api.Client;
import net.runelite.api.queries.InventoryWidgetItemQuery;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.nmautils.NMAUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryAPI
{

    @Inject
    private Client client;

    @Inject
    private NMAUtils utils;

    @Inject
    private StringAPI string;

    @Inject
    private MenuAPI menu;

    @Inject
    private MouseAPI mouse;

    @Inject
    private SleepAPI sleep;

    @Inject
    private PointAPI point;

    @Inject
    private MathAPI math;

    public WidgetItem getItem(String itemName)
    {
        return new InventoryWidgetItemQuery()
                .result(client)
                .stream()
                .filter(i -> string.removeWhiteSpaces(itemName)
                        .equalsIgnoreCase(string.removeWhiteSpaces(client.getItemDefinition(i.getId())
                                .getName())))
                .findFirst()
                .orElse(null);
    }

    public WidgetItem getItem(int itemId)
    {
        return new InventoryWidgetItemQuery()
                .idEquals(itemId)
                .result(client).first();
    }

    public List<WidgetItem> getItems()
    {
        Widget inventoryWidget = client.getWidget(WidgetInfo.INVENTORY);
        if (inventoryWidget == null)
        {
            return null;
        }
        return new ArrayList<>(inventoryWidget.getWidgetItems());
    }

    public List<WidgetItem> getItems(String... itemNames)
    {
        return new InventoryWidgetItemQuery()
                .result(client)
                .stream()
                .filter(i -> Arrays.stream(itemNames)
                        .anyMatch(s -> s.equalsIgnoreCase(client.getItemDefinition(i.getId())
                                .getName())))
                .collect(Collectors.toList());
    }

    public List<WidgetItem> getItems(int... itemIds)
    {
        return new InventoryWidgetItemQuery()
                .result(client)
                .stream()
                .filter(i -> Arrays.stream(itemIds)
                        .anyMatch(s -> s == i.getId()))
                .collect(Collectors.toList());
    }

    public List<WidgetItem> getItemsNotMatching(String... itemNames)
    {
        return new InventoryWidgetItemQuery()
                .result(client)
                .stream()
                .filter(i -> Arrays.stream(itemNames)
                        .noneMatch(s -> s.equalsIgnoreCase(client.getItemDefinition(i.getId())
                                .getName())))
                .collect(Collectors.toList());
    }

    public List<WidgetItem> getItemsNotMatching(int... itemIds)
    {
        return new InventoryWidgetItemQuery()
                .result(client)
                .stream()
                .filter(i -> Arrays.stream(itemIds)
                        .noneMatch(s -> s == i.getId()))
                .collect(Collectors.toList());
    }

    public boolean contains(String... itemNames)
    {
        if (client.getWidget(WidgetInfo.INVENTORY) == null)
        {
            return false;
        }
        return new InventoryWidgetItemQuery()
                .result(client)
                .stream()
                .anyMatch(i -> Arrays.stream(itemNames)
                        .anyMatch(s -> s.equalsIgnoreCase(client.getItemDefinition(i.getId())
                                .getName())));
    }

    public boolean contains(int... itemIds)
    {
        if (client.getWidget(WidgetInfo.INVENTORY) == null)
        {
            return false;
        }
        return new InventoryWidgetItemQuery()
                .result(client)
                .stream()
                .anyMatch(i -> Arrays.stream(itemIds)
                        .anyMatch(s -> s == i.getId()));
    }

    public void dropAll()
    {
        Collection<WidgetItem> items = getItems();
        if (items == null)
        {
            System.out.println("The items are null.");
            return;
        }
        utils.executor.submit(() ->
        {
            try
            {
                utils.iterating = true;
                System.out.println("Dropping " + items.size() + " items.");
                for (WidgetItem item : items)
                {
                    System.out.println(item.getId());
                    menu.setMenuEntry(menu.dropItem(item));
                    mouse.clickInstant(point.getClickPoint(item.getCanvasBounds()));
                    sleep.forXMillis(math.getRandomInt(150, 400));
                }
                utils.iterating = false;
            }
            catch (RuntimeException e)
            {
                utils.iterating = false;
                e.printStackTrace();
            }
        });
    }

    public void dropAllExcept(String... itemNames)
    {
        dropItems(getItemsNotMatching(itemNames));
    }

    public void dropAllExcept(int... itemId)
    {
        dropItems(getItemsNotMatching(itemId));
    }

    public void dropItem(String itemName)
    {
        dropItem(getItem(itemName));
    }

    public void dropItem(int itemId)
    {
        dropItem(getItem(itemId));
    }

    public void dropItem(WidgetItem item)
    {
        utils.executor.submit(() ->
        {
            try
            {
                menu.setMenuEntry(menu.dropItem(item));
                mouse.clickInstant(point.getClickPoint(item.getCanvasBounds()));
            }
            catch (RuntimeException e)
            {
                e.printStackTrace();
            }
        });
    }

    public void dropItems(String... itemName)
    {
        dropItems(getItems(itemName));
    }

    public void dropItems(int... itemId)
    {
        dropItems(getItems(itemId));
    }

    public void dropItems(List<WidgetItem> items)
    {
        if (items == null)
        {
            System.out.println("The items are null.");
            return;
        }
        utils.executor.submit(() ->
        {
            try
            {
                utils.iterating = true;
                for (WidgetItem item : items)
                {
                    menu.setMenuEntry(menu.dropItem(item));
                    mouse.clickInstant(point.getClickPoint(item.getCanvasBounds()));
                    sleep.forXMillis(math.getRandomInt(150, 400));
                }
                utils.iterating = false;
            }
            catch (RuntimeException e)
            {
                utils.iterating = false;
                e.printStackTrace();
            }
        });
    }

    public void combineItems(String item1Name, String item2Name) { combineItems(getItem(item1Name), getItem(item2Name)); }

    public void combineItems(int item1Id, int item2Id) { combineItems(getItem(item1Id), getItem(item2Id)); }

    public void combineItems(WidgetItem item1, WidgetItem item2)
    {
        if (item1 == null || item2 == null)
        {
            System.out.println("The items are null.");
            return;
        }
        List<WidgetItem> items = Arrays.asList(item1, item2);
        utils.executor.submit(() ->
        {
            try
            {
                utils.iterating = true;
                for (WidgetItem item : items)
                {
                    menu.setMenuEntry(menu.useItem(item));
                    mouse.clickInstant(point.getClickPoint(item.getCanvasBounds()));
                    sleep.forXMillis(math.getRandomInt(75, 200));
                }
                sleep.forXMillis(math.getRandomInt(400, 800));
                utils.iterating = false;
            }
            catch (RuntimeException e)
            {
                utils.iterating = false;
                e.printStackTrace();
            }
        });
    }


}
