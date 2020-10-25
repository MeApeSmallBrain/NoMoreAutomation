package net.runelite.client.plugins.nmautils;

import net.runelite.client.config.*;

@ConfigGroup("nmutils")
public interface Configuration extends Config {

    @ConfigTitleSection(
            keyName = "playerUtils",
            name = "Player Configurations",
            description = "",
            position = 1
    )
    default Title playerUtils()
    {
        return new Title();
    }

    @ConfigItem(
            keyName = "playerIdleTime",
            name = "Animation Idle Delay",
            description = "The time it will take for the player to be classed as Idle after an animation.",
            position = 2,
            titleSection = "playerUtils"
    )
    default int playerIdleTime()
    {
        return 1000;
    }

    @ConfigItem(
            keyName = "logConsoleMessage",
            name = "Log Console Messages",
            description = "Send messages to the IntelliJ IDEA console.",
            position = 3
            //titleSection = "playerUtils"
    )
    default boolean logConsoleMessage()
    {
        return false;
    }

    @ConfigItem(
            keyName = "logGameMessage",
            name = "Log Game Messages",
            description = "Send messages to the in-game chatbox.",
            position = 3
            //titleSection = "playerUtils"
    )
    default boolean logGameMessage()
    {
        return false;
    }

}
