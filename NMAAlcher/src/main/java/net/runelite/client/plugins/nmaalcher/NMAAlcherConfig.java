/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
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

import net.runelite.client.config.*;


@ConfigGroup("nmaalcher")
public interface NMAAlcherConfig extends Config
{
	@ConfigTitleSection(
			keyName = "configOptions",
			name = "Configuration Options",
			description = "",
			position = 1
	)
	default Title configOptions()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "alchItemName",
			name = "Item Name to Alch",
			description = "Input the name of the item to alch.",
			position = 2,
			titleSection = "configOptions"
	)
	default String alchItemName() { return ""; }

	@ConfigItem(
			keyName = "smallBreakMinTime",
			name = "Min small break",
			description = "",
			position = 3,
			titleSection = "configOptions"
	)
	default int smallBreakMinTime() { return 500; }

	@ConfigItem(
			keyName = "smallBreakMaxTime",
			name = "Max small break",
			description = "",
			position = 4,
			titleSection = "configOptions"
	)
	default int smallBreakMaxTime() { return 1000; }

	@ConfigItem(
			keyName = "mediumBreakMinTime",
			name = "Min medium break",
			description = "",
			position = 5,
			titleSection = "configOptions"
	)
	default int mediumBreakMinTime() { return 2000; }

	@ConfigItem(
			keyName = "mediumBreakMaxTime",
			name = "Max medium break",
			description = "",
			position = 6,
			titleSection = "configOptions"
	)
	default int mediumBreakMaxTime() { return 5000; }

	@ConfigItem(
			keyName = "largeBreakMinTime",
			name = "Min long break",
			description = "",
			position = 7,
			titleSection = "configOptions"
	)
	default int largeBreakMinTime() { return 10000; }

	@ConfigItem(
			keyName = "largeBreakMaxTime",
			name = "Max long break",
			description = "",
			position = 8,
			titleSection = "configOptions"
	)
	default int largeBreakMaxTime() { return 200000; }

}