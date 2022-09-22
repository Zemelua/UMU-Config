package io.github.zemelua.umu_config.mixin;

import io.github.zemelua.umu_config.ConfigHandler;
import io.github.zemelua.umu_config.UMUConfig;
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
		ConfigHandler.loadTo(UMUConfig.TEST_BASIC_CONFIG);
		ConfigHandler.loadTo(UMUConfig.TEST_CONFIG);
	}
}
