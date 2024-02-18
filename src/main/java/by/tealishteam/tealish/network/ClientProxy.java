package by.tealishteam.tealish.network;

import by.tealishteam.tealish.client.gui.TeapotScreen;
import by.tealishteam.tealish.network.packets.SyncTeapotMenu;
import net.minecraft.client.Minecraft;

public class ClientProxy {
    public static void handleSyncTeapot(SyncTeapotMenu packet){
        if(Minecraft.getInstance().isWindowActive() && Minecraft.getInstance().screen instanceof TeapotScreen) {
            ((TeapotScreen) Minecraft.getInstance().screen).getMenu().handlePacketData(packet);
        }
    }
}
