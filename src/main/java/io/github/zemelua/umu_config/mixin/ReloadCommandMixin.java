package io.github.zemelua.umu_config.mixin;

import io.github.zemelua.umu_config.config.ConfigFileManager;
import io.github.zemelua.umu_config.config.ConfigManager;
import net.minecraft.server.command.ReloadCommand;
import net.minecraft.server.command.ServerCommandSource;
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
		ConfigManager.streamCommon().forEach(ConfigFileManager::loadTo);
		ConfigManager.streamClient().forEach(ConfigFileManager::loadTo);
		ConfigManager.streamCommon().forEach(ConfigFileManager::saveFrom);
		ConfigManager.streamClient().forEach(ConfigFileManager::saveFrom);
	}
}
