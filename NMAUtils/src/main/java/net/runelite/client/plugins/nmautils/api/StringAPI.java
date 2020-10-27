package net.runelite.client.plugins.nmautils.api;

public class StringAPI
{

    public String removeCharactersFromString(String string)
    {
        return string.toLowerCase().replaceAll("\\D", "");
    }

    public String removeNumbersFromString(String string)
    {
        return string.toLowerCase().replaceAll("[0-9]", "");
    }

    public String removeWhiteSpaces(String string)
    {
        return string.toLowerCase().replaceAll("\\s+", "");
    }

}