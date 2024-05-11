package by.meww_meww.minesteeper.network.packets;

import by.meww_meww.minesteeper.network.ClientProxy;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkDirection;

public class SyncTeapotMenu {
    public FluidStack fluid;

    public SyncTeapotMenu(FluidStack fluid){
        this.fluid = fluid;
    }
    public  static void encode(SyncTeapotMenu message, FriendlyByteBuf buffer){
        buffer.writeFluidStack(message.fluid);
    }

    public static SyncTeapotMenu decode(FriendlyByteBuf buffer){
        FluidStack fluid = buffer.readFluidStack();
        return new SyncTeapotMenu(fluid);
    }

    public static void handle(final SyncTeapotMenu message, CustomPayloadEvent.Context context) {
        if ( context.getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            context.enqueueWork(() -> ClientProxy.handleSyncTeapot(message));
        }

        context.setPacketHandled(true);
    }
}
