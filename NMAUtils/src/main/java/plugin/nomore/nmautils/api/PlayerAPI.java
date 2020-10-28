package plugin.nomore.nmautils.api;

import net.runelite.api.Client;

import javax.inject.Inject;

public class PlayerAPI
{

    @Inject
    private Client client;

    public boolean isIdle()
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return false;
        }

        return client.getLocalPlayer().getAnimation() == -1;
    }

}
