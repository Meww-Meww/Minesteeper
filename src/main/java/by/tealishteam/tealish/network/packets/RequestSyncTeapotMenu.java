package by.tealishteam.tealish.network.packets;

import by.tealishteam.tealish.blocks.TeapotEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.NetworkDirection;

public class RequestSyncTeapotMenu {
    public BlockPos pos;

    public RequestSyncTeapotMenu(BlockPos pos){
        this.pos = pos;
    }

    public static void encode(RequestSyncTeapotMenu message, FriendlyByteBuf buffer){
        buffer.writeBlockPos(message.pos);
    }

    public static RequestSyncTeapotMenu decode(FriendlyByteBuf buffer){
        BlockPos pos = buffer.readBlockPos();
        return new RequestSyncTeapotMenu(pos);
    }

    public static void handle(final RequestSyncTeapotMenu message, CustomPayloadEvent.Context context) {
        if(context.getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ServerPlayer sender = context.getSender();

            if(sender != null) {
                ((TeapotEntity)sender.level().getBlockEntity(message.pos)).sendFluidInfo(sender);
            }
        }

        context.setPacketHandled(true);
    }
}
