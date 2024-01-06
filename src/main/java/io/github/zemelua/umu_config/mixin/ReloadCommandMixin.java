package io.github.zemelua.umu_config.mixin;

import io.github.zemelua.umu_config.api.client.config.ConfigManager;
import net.minecraft.server.command.ReloadCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(ReloadCommand.class)
public abstract class ReloadCommandMixin {
	@Inject(method = "tryReloadDataPacks",
			at = @At(value = "RETURN"))
	private static void reloadConfigs(Collection<String> dataPacks, ServerCommandSource source, CallbackInfo callback) {
		ConfigManager.INSTANCE.reloadAll();

		@Nullable ServerPlayerEntity sender = source.getPlayer();
		if (sender != null) {
			source.getServer().getPlayerManager().getPlayerList().forEach(p -> {
				ConfigManager.INSTANCE.stream().forEach(c -> {
					ConfigManager.sendToClient(p, c);
				});
			});
		}
	}
}
