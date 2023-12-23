package io.github.zemelua.umu_config.config.value.reference;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ResourcePackConfigValue extends AbstractResourcePackConfigValue {
	public ResourcePackConfigValue(Identifier ID, String packName) {
		super(ID, packName);
	}

	@Override
	protected ResourcePackManager getResourcePackManager() {
		return MinecraftClient.getInstance().getResourcePackManager();
	}
}
