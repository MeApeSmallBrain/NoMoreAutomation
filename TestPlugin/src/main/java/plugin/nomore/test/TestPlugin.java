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
package plugin.nomore.test;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import org.pf4j.Extension;
import plugin.nomore.nmautils.NMAUtils;
import plugin.nomore.nmautils.api.*;

import javax.inject.Inject;
import java.util.List;

@Extension
@PluginDescriptor(
		name = "Test",
		description = "New Plugin Description",
		tags = {"tag1", "tag2", "tag3"},
		type = PluginType.SKILLING
)
@Slf4j
@PluginDependency(NMAUtils.class)
public class TestPlugin extends Plugin
{

	@Inject
	private Client client;

	@Inject
	private NMAUtils utils;

	@Inject
	private DebugAPI debug;

	@Inject
	private ObjectAPI object;

	@Inject
	private MathAPI math;

	@Inject
	private MenuAPI menu;

	@Inject
	private MouseAPI mouse;

	@Inject
	private PointAPI point;

	private int tickDelay = 2;

	@Provides
	TestConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(TestConfig.class);
	}

	@Override
	protected void startUp()
	{
		debug.log("Plugin started.");
	}

	@Override
	protected void shutDown()
	{
		debug.log("Plugin finished.");
	}

	@Subscribe
	private void on(GameTick gameTick)
	{
		if (!utils.runScript(tickDelay))
		{
			tickDelay--;
			return;
		}

		if (client.getLocalPlayer() == null)
		{
			return;
		}

		if (object.getClosestTileItem() != null)
		{
			debug.log("The closest tile item is: " + client.getItemDefinition(object.getClosestTileItem().getId()).getName());
		}

		//tickDelay = 5;
	}

}