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
package net.runelite.client.plugins.nmaalcher;

import com.google.inject.Provides;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.callback.ClientThread;
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
	name = "NMA Alcher",
	description = "Alch's items in the inventory.",
	tags = {"nomore", "alch", "frus"},
	type = PluginType.SKILLING
)
@Slf4j
@PluginDependency(Utils.class)
public class NMAAlcherPlugin extends Plugin
{

	@Inject private Client client;
	@Inject private ClientThread clientThread;
	@Inject private OverlayManager overlayManager;
	@Inject private ConfigManager configManager;
	@Inject private Utils utils;
	@Inject private NMAAlcherConfig config;
	@Inject private DebugAPI debug;
	@Inject private EquipmentAPI equipment;
	@Inject private InventoryAPI inventory;
	@Inject private MathAPI math;
	@Inject private MenuAPI menu;
	@Inject private MouseAPI mouse;
	@Inject private NPCAPI npc;
	@Inject private ObjectAPI object;
	@Inject private PlayerAPI player;
	@Inject private PointAPI point;
	@Inject private RenderAPI render;
	@Inject private SleepAPI sleep;
	@Inject private StringAPI string;
	@Inject private TabAPI tab;
	@Inject private TimeAPI time;

	@Provides
	NMAAlcherConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(NMAAlcherConfig.class);
	}

	@Override
	protected void startUp()
	{
		setupClick = false;
		utils.lock = false;
		utils.iterating = false;
		natureRuneQuantity = 0;
		fireRuneQuantity = 0;
		tickDelay = 4;
		utils.setClickPoint(null);
		highAlchSpellPoint = null;
		openMagic = false;
	}

	@Override
	protected void shutDown()
	{
		setupClick = false;
		utils.lock = false;
		utils.iterating = false;
		natureRuneQuantity = 0;
		fireRuneQuantity = 0;
		tickDelay = 4;
		utils.setClickPoint(null);
		highAlchSpellPoint = null;
		openMagic = false;
	}

	int tickDelay = 2;
	boolean setupClick;
	Point highAlchSpellPoint;
	int fireRuneQuantity;
	int natureRuneQuantity;
	WidgetItem alchItem;
	Widget spell;
	boolean openMagic;

	@Subscribe
	private void on(GameTick e)
	{
		if (!utils.runScript(tickDelay))
		{
			tickDelay--;
			return;
		}

		if (!setupClick)
		{
			if (!openMagic)
			{
				tab.open(TabAPI.SPELLBOOK);
				tickDelay = 2;
				openMagic = true;
				return;
			}
			setupClickPoint();
			return;
		}

		int delayTime = getSleepTime();
		if (tab.isInventoryOpen())
		{
			selectItem(delayTime);
			return;
		}

		if (tab.isSpellbookOpen())
		{
			clickSpell(delayTime);
		}
	}

	private void clickSpell(int delayTime)
	{
		menu.setMenuEntry(menu.selectSpell(spell));
		mouse.clickWithDelay(
				highAlchSpellPoint,
				delayTime
		);
	}

	private void selectItem(int delayTime)
	{
		if (!itemCheck())
		{
			utils.lock = true;
			debug.log("You have run out of the item you wish to alch, the script will stop.");
			return;
		}
		if (!runeCheck())
		{
			utils.lock = true;
			debug.log("You do not have the required runes, script will stop.");
			return;
		}
		menu.setMenuEntry(menu.useSpellOnItem(alchItem));
		mouse.clickWithDelay(
				highAlchSpellPoint,
				delayTime
		);
	}


	private void setupClickPoint()
	{
		spell = client.getWidget(WidgetInfo.SPELL_HIGH_LEVEL_ALCHEMY);
		if (spell == null)
		{
			return;
		}
		Rectangle bounds = spell.getBounds();
		if (bounds == null)
		{
			return;
		}
		highAlchSpellPoint = new Point(
				math.getRandomInt((int) bounds.getX(), (int) bounds.getX() + 10),
				math.getRandomInt((int) bounds.getY(), (int) bounds.getY() + 10)
		);
		debug.log(String.valueOf(highAlchSpellPoint));
		utils.setClickPoint(highAlchSpellPoint);
		setupClick = true;
	}

	private boolean itemCheck()
	{
		alchItem = inventory.getItem(string.removeWhiteSpaces(config.alchItemName()));
		return alchItem != null;
	}

	private boolean runeCheck()
	{
		if (equipment.isWeilding("Fire"))
		{
			fireRuneQuantity = 5;
			if (inventory.getItem("Rune pouch") != null)
			{
				checkRunePouch();
			}
			else
			{
				WidgetItem nature = inventory.getItem("Nature rune");
				if (nature == null)
				{
					return false;
				}
				natureRuneQuantity = nature.getQuantity();
			}
		}
		else
		{
			if (inventory.getItem("Rune pouch") != null)
			{
				checkRunePouch();
			}
			else
			{
				WidgetItem fire = inventory.getItem("Fire rune");
				if (fire == null)
				{
					return false;
				}
				fireRuneQuantity = fire.getQuantity();
				WidgetItem nature = inventory.getItem("Nature rune");
				if (nature == null)
				{
					return false;
				}
				natureRuneQuantity = nature.getQuantity();
			}
		}
		return fireRuneQuantity >= 5 && natureRuneQuantity >= 1;
	}

	private void checkRunePouch()
	{
		if (client.getVar(Varbits.RUNE_POUCH_RUNE1) != 0)
		{
			int v = client.getVar(Varbits.RUNE_POUCH_RUNE1);

			if (v == 4)
			{
				fireRuneQuantity = client.getVar(Varbits.RUNE_POUCH_AMOUNT1);
			}
			if (v == 10)
			{
				natureRuneQuantity = client.getVar(Varbits.RUNE_POUCH_AMOUNT1);
			}
		}
		if (client.getVar(Varbits.RUNE_POUCH_RUNE2) != 0)
		{
			int v = client.getVar(Varbits.RUNE_POUCH_RUNE2);

			if (v == 4)
			{
				fireRuneQuantity = client.getVar(Varbits.RUNE_POUCH_AMOUNT2);
			}
			if (v == 10)
			{
				natureRuneQuantity = client.getVar(Varbits.RUNE_POUCH_AMOUNT2);
			}
		}
		if (client.getVar(Varbits.RUNE_POUCH_RUNE3) != 0)
		{
			int v = client.getVar(Varbits.RUNE_POUCH_RUNE3);

			if (v == 4)
			{
				fireRuneQuantity = client.getVar(Varbits.RUNE_POUCH_AMOUNT3);
			}
			if (v == 10)
			{
				natureRuneQuantity = client.getVar(Varbits.RUNE_POUCH_AMOUNT3);
			}
		}
	}

	private int getSleepTime()
	{
		int random = math.getRandomInt(1, 20);
		if (random >= 15 && random <= 19)
		{
			return  math.getRandomInt(config.mediumBreakMinTime(), config.mediumBreakMaxTime());
		}
		if (random == 20)
		{
			return  math.getRandomInt(config.largeBreakMinTime(), config.largeBreakMaxTime());
		}
		return math.getRandomInt(config.smallBreakMinTime(), config.smallBreakMaxTime());
	}
}