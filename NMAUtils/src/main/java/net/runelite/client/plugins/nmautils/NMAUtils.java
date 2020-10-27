package net.runelite.client.plugins.nmautils;


import com.google.inject.Provides;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.MenuEntry;
import net.runelite.api.Point;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import net.runelite.client.plugins.nmautils.api.DebugAPI;
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
)
@Slf4j
@SuppressWarnings("unused")
@Singleton
public class NMAUtils extends Plugin
{
	@Inject private Client client;
	@Inject private ClientThread clientThread;
	@Inject private OverlayManager overlayManager;
	@Inject private ConfigManager configManager;

	@Inject private NMAUtilsConfig config;
	@Inject private NMAUtilsOverlay overlay;
	@Inject public ExecutorService executor;
	@Inject private DebugAPI debug;

	@Provides
	NMAUtilsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(NMAUtilsConfig.class);
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