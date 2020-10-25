package net.runelite.client.plugins.nmautils;

import com.google.inject.Provides;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import net.runelite.client.plugins.nmautils.api.*;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Extension
@PluginDescriptor(
        name = "NMAUtils",
        description = "Plugin required for numerous plugins created by NoMore.",
        type = PluginType.UTILITY
        //hidden = true
)
@Slf4j
@SuppressWarnings("unused")
@Singleton
public class Utils extends Plugin
{
    @Inject private Client client;
    @Inject private ClientThread clientThread;
    @Inject public ExecutorService executor;
    @Inject private OverlayManager overlayManager;
    @Inject private ConfigManager configManager;
    @Inject private Configuration config;
    @Inject private Overlay overlay;

    @Inject private DebugAPI debug;
    @Inject private EquipmentAPI equipment;
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
    @Inject private TabAPI tabs;
    @Inject private TimeAPI time;

    @Provides
    Configuration provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(Configuration.class);
    }

    @Override
    protected void startUp()
    {
        overlayManager.add(overlay);
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    protected void shutDown()
    {
        overlayManager.remove(overlay);
        executor.shutdown();
        lock = false;
        iterating = false;
    }

    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC)
    public Point clickPoint = new Point(100, 100);
    public boolean iterating = false;
    public boolean lock = false;
    public int iteration = 1;
    public LocalTime executorSubmitTime = null;
    public MenuEntry targetMenu = null;

    @Subscribe
    private void on(MenuOptionClicked e)
    {
        if (targetMenu != null)
        {
            e.setMenuEntry(targetMenu);
            client.invokeMenuAction("",
                    "",
                    targetMenu.getOpcode(),
                    targetMenu.getIdentifier(),
                    targetMenu.getParam0(),
                    targetMenu.getParam1());
            targetMenu = null;
        }
    }

    @Subscribe
    private void on(MenuEntryAdded e)
    {
        if (targetMenu != null)
        {
            client.insertMenuItem("",
                    "",
                    targetMenu.getOpcode(),
                    targetMenu.getIdentifier(),
                    targetMenu.getParam0(),
                    targetMenu.getParam1(),
                    targetMenu.isForceLeftClick());
        }
    }

    public boolean runScript(int tickDelay)
    {
        if (!iterating && tickDelay > 0)
        {
            debug.log(tickDelay + " ticks left until action");
            return false;
        }
        if (lock)
        {
            return false;
        }
        if (iterating)
        {
            return false;
        }
        return true;
    }

}