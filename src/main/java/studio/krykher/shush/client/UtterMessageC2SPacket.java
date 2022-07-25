package studio.krykher.shush.client;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.network.listener.ServerPlayPacketListener;

import java.time.Instant;

public record UtterMessageC2SPacket(PlayerPublicKey recipient, Instant timestamp, byte[] cipherText) implements Packet<ServerPlayPacketListener> {
    @Override
    public void write(PacketByteBuf buf) {

    }

    @Override
    public void apply(ServerPlayPacketListener listener) {

    }
}
