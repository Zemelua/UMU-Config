package io.github.zemelua.umu_config.mixin;

import io.github.zemelua.umu_config.config.ConfigFileManager;
import io.github.zemelua.umu_config.api.config.ConfigManager;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.command.ReloadCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

import static io.github.zemelua.umu_config.network.NetworkHandler.*;

@Mixin(ReloadCommand.class)
public abstract class ReloadCommandMixin {
	@Inject(method = "tryReloadDataPacks",
			at = @At(value = "RETURN"))
	private static void reloadConfigs(Collection<String> dataPacks, ServerCommandSource source, CallbackInfo callback) {
		ConfigManager.streamCommon().forEach(ConfigFileManager::loadTo);
		ConfigManager.streamCommon().forEach(ConfigFileManager::saveFrom);

		@Nullable ServerPlayerEntity sender = source.getPlayer();
		if (sender != null) {
			ServerPlayNetworking.send(source.getPlayer(), RELOAD_CONFIGS_TO_CLIENT, PacketByteBufs.create());
		}
	}
}
