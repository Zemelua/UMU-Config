package io.github.zemelua.umu_config.config.value.resourcepack;

import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.util.Identifier;

public class ResourcePackConfigValue extends AbstractResourcePackConfigValue {
	public ResourcePackConfigValue(Identifier ID, Boolean defaultValue, String packName) {
		super(ID, defaultValue, packName);
	}

	@Override
	protected ResourcePackManager getResourcePackManager() {
		return MinecraftClient.getInstance().getResourcePackManager();
	}
}
