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

import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;
import net.runelite.client.plugins.nmautils.Utils;

@Extension
@PluginDescriptor(
	name = "Title",
	description = "Description",
	tags = {"tag1", "tag2", "tag3"},
	type = PluginType.UTILITY
)
@Slf4j
@PluginDependency(Utils.class)
public class ThePlugin extends Plugin {

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
    TheConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(TheConfig.class);
	}

	@Override
	protected void startUp() {
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() {
		overlayManager.remove(overlay);
	}
}
