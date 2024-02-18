package by.tealishteam.tealish.network;

import by.tealishteam.tealish.network.packets.RequestSyncTeapotMenu;
import by.tealishteam.tealish.network.packets.SyncTeapotMenu;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;

public class NetworkHandler {
    public static SimpleChannel channel = ChannelBuilder.named("tealish").simpleChannel();

    public static void register(){
        channel.messageBuilder(SyncTeapotMenu.class).encoder(SyncTeapotMenu::encode).decoder(SyncTeapotMenu::decode).consumerMainThread(SyncTeapotMenu::handle).add();
        channel.messageBuilder(RequestSyncTeapotMenu.class).encoder(RequestSyncTeapotMenu::encode).decoder(RequestSyncTeapotMenu::decode).consumerMainThread(RequestSyncTeapotMenu::handle).add();
    }
}
