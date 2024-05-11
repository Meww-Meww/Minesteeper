package by.meww_meww.minesteeper.network;

import by.meww_meww.minesteeper.client.gui.TeapotScreen;
import by.meww_meww.minesteeper.network.packets.SyncTeapotMenu;
import net.minecraft.client.Minecraft;

public class ClientProxy {
    public static void handleSyncTeapot(SyncTeapotMenu packet){
        if(Minecraft.getInstance().isWindowActive() && Minecraft.getInstance().screen instanceof TeapotScreen) {
            ((TeapotScreen) Minecraft.getInstance().screen).getMenu().handlePacketData(packet);
        }
    }
}
