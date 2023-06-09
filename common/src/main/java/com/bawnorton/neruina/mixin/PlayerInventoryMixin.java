package com.bawnorton.neruina.mixin;

import com.bawnorton.neruina.Neruina;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("unused")
@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {
    @WrapOperation(method = "updateItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;inventoryTick(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;IZ)V"))
    private void catchTickingItemStack(ItemStack instance, World world, Entity entity, int slot, boolean selected, Operation<Void> original) {
        try {
            if (Neruina.isErrored(instance)) {
                return;
            }
            original.call(instance, world, entity, slot, selected);
        } catch (Throwable e) {
            String message = Text.translatable("neruina.ticking.item_stack", instance.getItem().getName().getString(), slot).getString();
            Neruina.LOGGER.warn((world.isClient? "Client: " : "Server: ") + message, e);
            Neruina.addErrored(instance);
            if (world.isClient && entity instanceof PlayerEntity player) {
                player.sendMessage(Text.of(message), false);
            }
        }
    }
}
