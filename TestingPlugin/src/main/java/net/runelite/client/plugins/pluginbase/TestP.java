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
package net.runelite.client.plugins.pluginbase;

import com.google.inject.Provides;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;

import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import net.runelite.client.plugins.nmautils.Utils;
import net.runelite.client.plugins.nmautils.api.*;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;

import java.awt.*;

@Extension
@PluginDescriptor(
	name = "Title",
	description = "Description",
	tags = {"tag1", "tag2", "tag3"},
	type = PluginType.UTILITY
)
@Slf4j
@PluginDependency(Utils.class)
public class TestP extends Plugin {

	@Inject private Client client;
	@Inject private OverlayManager overlayManager;
	@Inject private TestC config;
	@Inject private TestO overlay;
	@Inject private Utils utils;
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
    TestC provideConfig(ConfigManager configManager) {
		return configManager.getConfig(TestC.class);
	}

	@Override
	protected void startUp() {
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() {
		overlayManager.remove(overlay);
	}

	boolean toggle = true;
	int tickDelay = 1;
	@Subscribe
	private void on(GameTick e)
	{
		if (!utils.runScript(tickDelay))
		{
			tickDelay--;
			return;
		}
		if (tabs.isSpellbookOpen() && toggle)
		{
			Widget spell = client.getWidget(WidgetInfo.SPELL_HIGH_LEVEL_ALCHEMY);
			if (spell == null)
			{
				return;
			}
			Rectangle b = spell.getBounds();
			if (b == null)
			{
				return;
			}
			menu.setMenuEntry(menu.selectSpell(spell));
			mouse.clickWithDelay(point.getRandomPointWithinBounds(b), math.getRandomInt(100, 450));
			toggle = false;
			tickDelay = 2;
			return;
		}
		if (tabs.isInventoryOpen() && !toggle)
		{
			WidgetItem item = inventory.getItem("Oak logs");
			if (item == null)
			{
				return;
			}
			Rectangle b = item.getCanvasBounds();
			if (b == null)
			{
				return;
			}
			menu.setMenuEntry(menu.useSpellOnItem(item));
			mouse.clickWithDelay(point.getRandomPointWithinBounds(b), math.getRandomInt(100, 450));
			toggle = true;
			tickDelay = 2;
			return;
		}
	}
}
