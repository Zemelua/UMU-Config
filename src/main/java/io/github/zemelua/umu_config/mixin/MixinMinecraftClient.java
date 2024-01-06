package io.github.zemelua.umu_config.mixin;

import io.github.zemelua.umu_config.client.ClientConfigManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.WindowEventHandler;
import net.minecraft.util.thread.ReentrantThreadExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient extends ReentrantThreadExecutor<Runnable> implements WindowEventHandler {
	public MixinMinecraftClient(String string) {
		super(string);
	}

	@Inject(method = "reloadResources()Ljava/util/concurrent/CompletableFuture;",
			at = @At("RETURN"))
	private void reloadClientConfigs(CallbackInfoReturnable<CompletableFuture<Void>> callback) {
		ClientConfigManager.INSTANCE.reloadAll();
	}
}
