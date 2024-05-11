package by.meww_meww.minesteeper.network;

import by.meww_meww.minesteeper.network.packets.RequestSyncTeapotMenu;
import by.meww_meww.minesteeper.network.packets.SyncTeapotMenu;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;

public class NetworkHandler {
    public static SimpleChannel channel = ChannelBuilder.named("minesteeper").simpleChannel();

    public static void register(){
        channel.messageBuilder(SyncTeapotMenu.class).encoder(SyncTeapotMenu::encode).decoder(SyncTeapotMenu::decode).consumerMainThread(SyncTeapotMenu::handle).add();
        channel.messageBuilder(RequestSyncTeapotMenu.class).encoder(RequestSyncTeapotMenu::encode).decoder(RequestSyncTeapotMenu::decode).consumerMainThread(RequestSyncTeapotMenu::handle).add();
    }
}
