package com.bawnorton.neruina.networking;

import com.bawnorton.neruina.Neruina;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class Networking {
    public static Identifier BAD_BLOCK_ENTITY = new Identifier(Neruina.MOD_ID, "bad_block_entity");

    public static void sendBadBlockEntityPacket(ServerPlayerEntity player, BlockPos pos) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        ServerPlayNetworking.send(player, BAD_BLOCK_ENTITY, buf);
    }
}