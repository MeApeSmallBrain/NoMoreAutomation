/*
 * Copyright (c) 2018, James Swindle <wilingua@gmail.com>
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package plugin.nomore.notkeys;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import org.pf4j.Extension;
import plugin.nomore.nmautils.NMAUtils;
import plugin.nomore.nmautils.api.*;

import javax.inject.Inject;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.stream.Stream;

@Extension
@PluginDescriptor(
		name = "NotKeys",
		description = "Useful hotkeys for numerous things.",
		tags = {"Hotkey", "NoMore"},
		type = PluginType.UTILITY
)
@Slf4j
@PluginDependency(NMAUtils.class)
public class NotKeysPlugin extends Plugin implements KeyListener
{

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private NMAUtils utils;

	@Inject
	private DebugAPI debug;

	@Inject
	private KeyManager keyManager;

	@Inject
	private InventoryAPI inventory;

	@Inject
	private MenuAPI menu;

	@Inject
	private MouseAPI mouse;

	@Inject
	private PointAPI point;

	@Provides
	NotKeysConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(NotKeysConfig.class);
	}

	@Override
	protected void startUp()
	{
		keyManager.registerKeyListener(this);
	}

	@Override
	protected void shutDown() { keyManager.unregisterKeyListener(this); }

	@Override
	public void keyTyped(KeyEvent e)
	{
		if (e.getKeyCode() == 112)
		{
			e.consume();
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == 112)
		{
			eatFood();
			e.consume();
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == 112)
		{
			e.consume();
		}
	}

	private void eatFood()
	{
		clientThread.invoke(() ->
		{
			List<WidgetItem> items = inventory.getItems();
			for (WidgetItem item : items)
			{
				if (item == null)
				{
					continue;
				}
				for (String itemAction : client.getItemDefinition(item.getId()).getInventoryActions())
				{
					if (itemAction == null)
					{
						continue;
					}
					if (itemAction.matches("Eat"))
					{
						menu.setMenuEntry(menu.eatItem(item));
						mouse.clickInstant(point.getRandomPointFromCentre(item.getCanvasBounds(), 30));
						return;
					}
				}
			}
		});
	}

	private void drinkPrayerPotion()
	{

	}
}